package com.runeanim.lineproject.ui.addeditmemo

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.BaseFragment
import com.runeanim.lineproject.databinding.AddMemoFragmentBinding
import com.runeanim.lineproject.util.EventObserver
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.add_memo_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddEditMemoFragment :
    BaseFragment<AddMemoFragmentBinding, AddEditMemoViewModel>(R.layout.add_memo_fragment) {

    companion object {
        fun newInstance() = AddEditMemoFragment()
    }

    override val viewModel: AddEditMemoViewModel by viewModel()

    lateinit var adapter: AttachedImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            viewmodel = viewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setupImageChooser()
        setupEventObserve()
        setupListAdapter()
    }

    private fun setupEventObserve() {
        viewModel.showImageChooserEvent.observe(this, EventObserver {
            showImageChooser()
        })
        viewModel.memoUpdatedEvent.observe(this, EventObserver {
            activity?.finish()
        })
    }

    private fun showImageChooser() {
        activity?.run {
            if (grantExternalStoragePermission(this)) {
                TedImagePicker.with(this)
                    .startMultiImage { uriList ->
                        viewModel.addAttachedImages(uriList)
                    }
            }
        }
    }

    private fun setupListAdapter() {
        adapter = AttachedImageAdapter(viewModel)
        recycler_view.adapter = adapter
    }

    private fun grantExternalStoragePermission(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            return if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(ContentValues.TAG, "Permission is granted")
                true
            } else {
                Log.v(ContentValues.TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )

                false
            }
        } else {
            Toast.makeText(activity, "External Storage Permission is Grant", Toast.LENGTH_SHORT)
                .show()
            Log.d(ContentValues.TAG, "External Storage Permission is Grant ")
            return true
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (Build.VERSION.SDK_INT >= 23) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v(
                    ContentValues.TAG,
                    "Permission: " + permissions[0] + "was " + grantResults[0]
                );
                //resume tasks needing this permission
            }
        }
    }
}
