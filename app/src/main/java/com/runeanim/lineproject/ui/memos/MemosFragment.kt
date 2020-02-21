package com.runeanim.lineproject.ui.memos

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.BaseFragment
import com.runeanim.lineproject.databinding.MemosFragmentBinding
import com.runeanim.lineproject.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel


class MemosFragment : BaseFragment<MemosFragmentBinding, MemosViewModel>(R.layout.memos_fragment) {

    override val viewModel: MemosViewModel by viewModel()

    private lateinit var listAdapter: MemoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            viewmodel = viewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        setupNavigation()
        setupListAdapter()
    }

    private fun setupListAdapter() {
        listAdapter = MemoListAdapter(viewModel)
        viewDataBinding.rvMemo.adapter = listAdapter
    }

    private fun setupFab() {
        viewDataBinding.fab.setOnClickListener {
            navigateToAddNewMemo()
        }
    }

    private fun setupNavigation() {
        viewModel.openMemoEvent.observe(this, EventObserver {
            openMemoDetails(it)
        })
    }

    private fun navigateToAddNewMemo() {
        val action = MemosFragmentDirections
            .actionMemosFragmentToAddEditMemoFragment()
        findNavController().navigate(action)
    }

    private fun openMemoDetails(memoId: Int) {
        val action = MemosFragmentDirections.actionMemosFragmentToMemoDetailFragment(memoId)
        findNavController().navigate(action)
    }

}
