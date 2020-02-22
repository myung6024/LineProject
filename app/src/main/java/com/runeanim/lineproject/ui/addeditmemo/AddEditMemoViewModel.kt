package com.runeanim.lineproject.ui.addeditmemo

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runeanim.lineproject.R
import com.runeanim.lineproject.base.AttachedImageClickListener
import com.runeanim.lineproject.data.Result
import com.runeanim.lineproject.data.model.AttachedImage
import com.runeanim.lineproject.data.model.AttachedImageType
import com.runeanim.lineproject.data.model.Memo
import com.runeanim.lineproject.data.source.MemosRepository
import com.runeanim.lineproject.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.util.regex.Pattern


class AddEditMemoViewModel(
    private val memosRepository: MemosRepository
) : ViewModel(), AttachedImageClickListener {

    val title = MutableLiveData<String>()

    val body = MutableLiveData<String>()

    private val _attachedImages = MutableLiveData<List<AttachedImage>>(emptyList())
    val attachedImages: LiveData<List<AttachedImage>> = _attachedImages

    private val attachedImageList = arrayListOf<AttachedImage>()

    private val _showImageChooserEvent = MutableLiveData<Event<Unit>>()
    val showImageChooserEvent: LiveData<Event<Unit>> = _showImageChooserEvent

    private val _closeEvent = MutableLiveData<Event<Unit>>()
    val closeEvent: LiveData<Event<Unit>> = _closeEvent

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private var memoId: Int = -1

    private var imageId = 0

    private var isNewMemo: Boolean = false

    fun start(memoId: Int) {
        this.memoId = memoId

        if (memoId == -1) {
            isNewMemo = true
            return
        }

        isNewMemo = false

        viewModelScope.launch {
            memosRepository.getMemo(memoId).let { result ->
                if (result is Result.Success) {
                    onMemoLoaded(result.data)
                }
            }
        }
    }

    fun close() {
        _closeEvent.value = Event(Unit)
    }

    private fun onMemoLoaded(memo: Memo) {
        title.value = memo.title
        body.value = memo.body
        attachedImageList.addAll(memo.attachedImages)
        if (attachedImageList.isNotEmpty()) imageId = attachedImageList.last().id + 1
        _attachedImages.value = attachedImageList.toMutableList()
    }


    fun saveMemo() {
        val currentTitle = title.value
        val currentBody = body.value

        if (currentTitle.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.error_empty_title)
            return
        }

        if (currentBody.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.error_empty_body)
            return
        }

        val currentMemoId = memoId
        if (isNewMemo || currentMemoId == -1) {
            viewModelScope.launch {
                memosRepository.saveMemo(
                    Memo(
                        currentTitle,
                        currentBody,
                        attachedImageList,
                        System.currentTimeMillis()
                    )
                )
            }
        } else {
            viewModelScope.launch {
                memosRepository.saveMemo(
                    Memo(
                        currentTitle,
                        currentBody,
                        attachedImageList,
                        System.currentTimeMillis(),
                        currentMemoId
                    )
                )
            }
        }
        _closeEvent.value = Event(Unit)
    }

    fun addAttachedImages(attachedImages: List<Uri>) {
        attachedImageList.addAll(attachedImages.map {
            AttachedImage(
                imageId++,
                AttachedImageType.URI,
                it.path!!
            )
        })
        _attachedImages.value = attachedImageList.toMutableList()
    }

    fun addAttachedImageFromURL(url: String) {
        val reg = "https?://[-\\w.]+(:\\d+)?(/([\\w/_.]*)?)?"
        if (Pattern.matches(reg, url)) {
            viewModelScope.launch(Dispatchers.IO) {
                val connection =
                    URL(url).openConnection()
                val contentType = connection.getHeaderField("Content-Type")
                val isImage = contentType?.startsWith("image/") ?: false

                launch(Dispatchers.Main) {
                    if (isImage) {
                        attachedImageList.add(
                            AttachedImage(
                                imageId++,
                                AttachedImageType.URL,
                                url
                            )
                        )
                        _attachedImages.value = attachedImageList.toMutableList()
                    } else {
                        _snackbarText.value = Event(R.string.error_content_type)
                    }
                }
            }
        } else {
            _snackbarText.value = Event(R.string.error_not_matched_url_format)
        }
    }

    override fun onAttachedImageClick(attachedImage: AttachedImage) {
        removeAttachedImage(attachedImage)
    }

    private fun removeAttachedImage(attachedImage: AttachedImage) {
        attachedImageList.remove(attachedImage)
        _attachedImages.value = attachedImageList.toMutableList()
    }

    fun showImageChooser() {
        _showImageChooserEvent.value = Event(Unit)
    }
}
