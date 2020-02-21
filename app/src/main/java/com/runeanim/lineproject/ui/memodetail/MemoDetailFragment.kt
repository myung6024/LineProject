package com.runeanim.lineproject.ui.memodetail

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.BaseFragment
import com.runeanim.lineproject.databinding.MemoDetailFragmentBinding
import com.runeanim.lineproject.ui.addeditmemo.AttachedImageAdapter
import com.runeanim.lineproject.util.EventObserver
import kotlinx.android.synthetic.main.add_memo_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoDetailFragment :
    BaseFragment<MemoDetailFragmentBinding, MemoDetailViewModel>(R.layout.memo_detail_fragment) {

    private val args: MemoDetailFragmentArgs by navArgs()

    override val viewModel: MemoDetailViewModel by viewModel()

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
        setupListAdapter()

        viewModel.start(args.memoId)
    }

    private fun setupListAdapter() {
        adapter = AttachedImageAdapter(viewModel)
        recycler_view.adapter = adapter
    }

    private fun setupNavigation() {
        viewModel.editMemoEvent.observe(this, EventObserver {
            val action = MemoDetailFragmentDirections
                .actionMemoDetailFragmentToAddEditMemoFragment(args.memoId)
            findNavController().navigate(action)
        })

        viewModel.showImageDetailEvent.observe(this, EventObserver {
            val action = MemoDetailFragmentDirections
                .actionMemoDetailFragmentDestToImageDetailFragmentDest(it)
            findNavController().navigate(action)
        })

        viewModel.closeEvent.observe(this, EventObserver {
            val action = MemoDetailFragmentDirections
                .actionMemoDetailFragmentToMemosFragment()
            findNavController().navigate(action)
        })
    }
}
