package com.runeanim.lineproject.ui.imagedetail

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.BaseFragment
import com.runeanim.lineproject.databinding.ImageDetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageDetailFragment :
    BaseFragment<ImageDetailFragmentBinding, ImageDetailViewModel>(R.layout.image_detail_fragment) {

    private val args: ImageDetailFragmentArgs by navArgs()

    override val viewModel: ImageDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            viewmodel = viewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.start(args.imagePath)
    }
}
