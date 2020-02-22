package com.runeanim.lineproject.addeditmemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.runeanim.lineproject.FakeRepository
import com.runeanim.lineproject.LiveDataTestUtil
import com.runeanim.lineproject.MainCoroutineRule
import com.runeanim.lineproject.R
import com.runeanim.lineproject.ui.addeditmemo.AddEditMemoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddEditMemoViewModelTest {

    private lateinit var addEditMemoViewModel: AddEditMemoViewModel

    private lateinit var memosRepository: FakeRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        memosRepository = FakeRepository()
        addEditMemoViewModel = AddEditMemoViewModel(memosRepository)
    }

    @Test
    fun `메모 저장 확인`() {
        val newTitle = "test title"
        val newBody = "test body"

        (addEditMemoViewModel).apply {
            title.value = newTitle
            body.value = newBody
        }
        addEditMemoViewModel.saveMemo()

        val memo = memosRepository.memosServiceData.values.last()

        Truth.assertThat(memo.title).isEqualTo(newTitle)
        Truth.assertThat(memo.body).isEqualTo(newBody)
    }

    @Test
    fun `저장 에러 - 제목 없음`() {
        val newTitle = ""
        val newBody = "test body"

        (addEditMemoViewModel).apply {
            title.value = newTitle
            body.value = newBody
        }
        addEditMemoViewModel.saveMemo()

        Truth.assertThat(LiveDataTestUtil.getValue(addEditMemoViewModel.snackbarText).getContentIfNotHandled())
            .isEqualTo(R.string.error_empty_title)
    }

    @Test
    fun `저장 에러 - 본문 없음`() {
        val newTitle = "test"
        val newBody = ""

        (addEditMemoViewModel).apply {
            title.value = newTitle
            body.value = newBody
        }
        addEditMemoViewModel.saveMemo()

        Truth.assertThat(LiveDataTestUtil.getValue(addEditMemoViewModel.snackbarText).getContentIfNotHandled())
            .isEqualTo(R.string.error_empty_body)
    }

    @Test
    fun `URL주소로 이미지 가져오기 - url format 에러`() {
        addEditMemoViewModel.addAttachedImageFromURL("test")
        Truth.assertThat(LiveDataTestUtil.getValue(addEditMemoViewModel.snackbarText).getContentIfNotHandled())
            .isEqualTo(R.string.error_not_matched_url_format)
        val images = addEditMemoViewModel.attachedImages.value ?: emptyList()

        Truth.assertThat(images.size).isEqualTo(0)
    }

    @Test
    fun `URL주소로 이미지 가져오기 - content type 에러`() {
        addEditMemoViewModel.addAttachedImageFromURL("https://www.naver.com")
        Truth.assertThat(LiveDataTestUtil.getValue(addEditMemoViewModel.snackbarText).getContentIfNotHandled())
            .isEqualTo(R.string.error_content_type)
        val images = addEditMemoViewModel.attachedImages.value ?: emptyList()
        Truth.assertThat(images.size).isEqualTo(0)
    }
}