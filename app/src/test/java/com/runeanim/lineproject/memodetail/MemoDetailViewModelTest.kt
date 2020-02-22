package com.runeanim.lineproject.memodetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.runeanim.lineproject.FakeRepository
import com.runeanim.lineproject.LiveDataTestUtil
import com.runeanim.lineproject.MainCoroutineRule
import com.runeanim.lineproject.R
import com.runeanim.lineproject.data.model.Memo
import com.runeanim.lineproject.ui.memodetail.MemoDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MemoDetailViewModelTest {

    private lateinit var memoDetailViewModel: MemoDetailViewModel

    private lateinit var memosRepository: FakeRepository

    private val memo = Memo("Title1", "Description1", emptyList(), 0, 0)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        memosRepository = FakeRepository()
        memosRepository.memosServiceData[memo.id] = memo
        memoDetailViewModel = MemoDetailViewModel(memosRepository)
    }

    @Test
    fun `초기화`() {
        memoDetailViewModel.start(memo.id)

        Truth.assertThat(LiveDataTestUtil.getValue(memoDetailViewModel.memo))
            .isEqualTo(memo)
    }

    @Test
    fun `메모 삭제`() {
        Truth.assertThat(memosRepository.memosServiceData.containsKey(memo.id)).isTrue()
        memoDetailViewModel.start(memo.id)
        memoDetailViewModel.removeMemo()

        Truth.assertThat(memosRepository.memosServiceData.containsKey(memo.id)).isFalse()
    }

    @Test
    fun `메모 수정`() {
        Truth.assertThat(memosRepository.memosServiceData.containsKey(memo.id)).isTrue()
        memoDetailViewModel.start(memo.id)
        memoDetailViewModel.editMemo()

        Truth.assertThat(LiveDataTestUtil.getValue(memoDetailViewModel.editMemoEvent))
            .isNotNull()
    }
}