package com.example.uploadhome.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uploadhome.CommonViewModel
import com.example.uploadhome.NavigationCenter
import com.example.uploadhome.PictureAdapter
import com.example.uploadhome.R
import com.google.android.gms.common.internal.service.Common
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_upload_photo.*




class UploadPhotoFragment : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mDataSet: ArrayList<Uri>
    private var mViewModel : CommonViewModel? = null

    companion object {
        const val REQUEST_CODE_READ = 101
        const val PICK_GALLERY_REQUEST_CODE = 102
    }

    private lateinit var mOnClickListener: View.OnClickListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
    }

    private fun initView(view: View) {
        mDatabase = FirebaseDatabase.getInstance().reference.child("house")
        mDataSet = ArrayList()
        mViewModel = activity?.let {
            ViewModelProvider(activity!!).get(CommonViewModel::class.java)
        }
        val photoAdapter = PictureAdapter(context, mDataSet)
        rv_photos.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = photoAdapter
        }

        mOnClickListener = View.OnClickListener { v ->
            when (v) {
                btn_upload -> {
                    if (ContextCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_CODE_READ
                        )
                    } else {
                        showPicker()
                    }
                }

                btn_submit -> {
                    writeNewUser()
                }
            }
        }

        btn_submit.setOnClickListener(mOnClickListener)
        btn_upload.setOnClickListener(mOnClickListener)
    }

    private fun showPicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, PICK_GALLERY_REQUEST_CODE)

    }

    private fun alertError() {
        Toast.makeText(context, "Please allow access to device data", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload_photo, container, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_READ -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showPicker()
                } else {
                    alertError()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_GALLERY_REQUEST_CODE -> {
                    val selectedImage = data?.data
                    if (selectedImage != null) {
                        updatePhoto(selectedImage)
                    }
                }
            }
        }
    }

    private fun updatePhoto(selectedImage: Uri) {
        mDataSet.add(selectedImage)
        rv_photos.adapter?.let {
            it.notifyDataSetChanged()
            it.itemCount.minus(1).let { rv_photos.smoothScrollToPosition(it) }
        }

    }

    private fun writeNewUser() {
        val database = FirebaseDatabase.getInstance()
        val mRef = database.reference.child("Homes").child(getUserId())
        mViewModel?.houseInfo?.let {
            if (mDataSet.isNullOrEmpty()) {
                Toast.makeText(context, "Must upload photos", Toast.LENGTH_SHORT).show()
                return
            } else {
                mRef.child("name").setValue(it.name)
                mRef.child("email").setValue(it.email)
                mRef.child("address").setValue(it.address)
                mRef.child("note").setValue(it.note)
                for (i in mDataSet.indices) {
                    mRef.child("images").child(i.toString()).setValue(mDataSet[i].toString())
                }
                Toast.makeText(context, "Upload success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserId(): String {
        return (0..100).random().toString()
    }


}