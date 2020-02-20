package com.runeanim.lineproject.ui.memodetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.runeanim.lineproject.R

class MemoDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MemoDetailFragment()
    }

    private lateinit var viewModel: MemoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.memo_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MemoDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
