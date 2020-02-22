package com.runeanim.lineproject.ui.addeditmemo

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.BaseFragment
import com.runeanim.lineproject.databinding.AddMemoFragmentBinding
import com.runeanim.lineproject.util.EventObserver
import com.runeanim.lineproject.util.setupSnackbar
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.add_memo_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager


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

        setupNavigation()
        setupEventObserve()
        setupListAdapter()
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

        viewModel.start(args.memoId)
    }

    private fun setupEventObserve() {
        viewModel.showImageChooserEvent.observe(this, EventObserver {
            createImageTypeSelectDialog().show()
        })
    }

    private fun setupNavigation() {
        viewModel.closeEvent.observe(this, EventObserver {
            activity?.currentFocus?.hideKeyboard()
            navigateToMemos()
        })
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
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

    private fun createImageTypeSelectDialog(): AlertDialog =
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
        val urlEditText = EditText(context).apply {
            hint = "https://"
        }
        AlertDialog.Builder(context!!, R.style.MyAlertDialogStyle)
            .setMessage(R.string.add_url_image_dialog_message)
            .setView(urlEditText)
            .setPositiveButton(R.string.ok) { _, _ ->
                val url = urlEditText.text.toString()
                viewModel.addAttachedImageFromURL(url)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                urlEditText.text.clear()
            }.create().show()
    }

    private fun navigateToMemos() {
        val action = AddEditMemoFragmentDirections
            .actionAddEditMemoFragmentToMemosFragment()
        findNavController().navigate(action)
    }
}
