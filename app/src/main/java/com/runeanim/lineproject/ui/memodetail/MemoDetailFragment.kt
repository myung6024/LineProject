package com.runeanim.lineproject.ui.memodetail

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.BaseFragment
import com.runeanim.lineproject.databinding.MemoDetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoDetailFragment :
    BaseFragment<MemoDetailFragmentBinding, MemoDetailViewModel>(R.layout.memo_detail_fragment) {

    private val args: MemoDetailFragmentArgs by navArgs()

    override val viewModel: MemoDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            viewmodel = viewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.start(args.memoId)
    }
}
