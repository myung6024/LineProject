package com.runeanim.lineproject.ui.main

import android.content.Intent
import android.os.Bundle
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.BaseFragment
import com.runeanim.lineproject.databinding.MainFragmentBinding
import com.runeanim.lineproject.ui.addeditmemo.AddEditMemoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : BaseFragment<MainFragmentBinding, MainViewModel>(R.layout.main_fragment) {

    override val viewModel: MainViewModel by viewModel()

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
        setupListAdapter()
    }

    private fun setupListAdapter() {
        listAdapter = MemoListAdapter(viewModel)
        viewDataBinding.rvMemo.adapter = listAdapter
    }

    private fun setupFab() {
        viewDataBinding.fab.setOnClickListener {
            startActivity(Intent(context, AddEditMemoActivity::class.java))
        }
    }
}
