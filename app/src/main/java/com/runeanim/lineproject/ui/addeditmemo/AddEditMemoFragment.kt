package com.runeanim.lineproject.ui.addeditmemo

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.BaseFragment
import com.runeanim.lineproject.databinding.AddMemoFragmentBinding
import com.runeanim.lineproject.util.EventObserver
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.add_memo_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.EditText
import java.util.regex.Pattern


class AddEditMemoFragment :
    BaseFragment<AddMemoFragmentBinding, AddEditMemoViewModel>(R.layout.add_memo_fragment) {

    override val viewModel: AddEditMemoViewModel by viewModel()

    private val args: AddEditMemoFragmentArgs by navArgs()

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

        viewModel.start(args.memoId)
    }

    private fun setupEventObserve() {
        viewModel.showImageChooserEvent.observe(this, EventObserver {
            createFilterSelectDialog().show()
        })
        viewModel.memoUpdatedEvent.observe(this, EventObserver {
            navigateToMemos()
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
        adapter = AttachedImageAdapter(viewModel, true)
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
                )
            }
        }
    }

    private fun createFilterSelectDialog(): AlertDialog =
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setItems(
                    R.array.attached_image_type_array
                ) { _, which ->
                    when (which) {
                        0 -> showImageChooser()
                        else -> showUrlDialog()
                    }
                }
            return builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    private fun showUrlDialog() {
        val et = EditText(context)
        et.hint = "http://"
        val dialog = AlertDialog.Builder(context!!, R.style.MyAlertDialogStyle)
            .setMessage("이미지의 url을 입력하세요")
            .setView(et)
            .setPositiveButton("확인") { _, _ ->
            }.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener {
                val url = et.text.toString()
                val reg = "https?://[-\\w.]+(:\\d+)?(/([\\w/_.]*)?)?"
                if (Pattern.matches(reg, url)) {
                    viewModel.addAttachedImageFromURL(url)
                    dialog.dismiss()
                }
            }
    }

    private fun navigateToMemos() {
        val action = AddEditMemoFragmentDirections
            .actionAddEditMemoFragmentToMemosFragment()
        findNavController().navigate(action)
    }
}
