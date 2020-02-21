package com.runeanim.lineproject.ui.addeditmemo

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runeanim.lineproject.base.AttachedImageClickListener
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.AttachedImage
import com.runeanim.lineproject.model.AttachedImageType
import com.runeanim.lineproject.model.Memo
import com.runeanim.lineproject.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.util.regex.Pattern


class AddEditMemoViewModel(
    private val memosDao: MemosDao
) : ViewModel(), AttachedImageClickListener {

    val title = MutableLiveData<String>()

    val body = MutableLiveData<String>()

    private val _attachedImages = MutableLiveData<List<AttachedImage>>(emptyList())
    val attachedImages: LiveData<List<AttachedImage>> = _attachedImages

    private val attachedImageList = arrayListOf<AttachedImage>()

    private val _memoUpdatedEvent = MutableLiveData<Event<Unit>>()
    val memoUpdatedEvent: LiveData<Event<Unit>> = _memoUpdatedEvent

    private val _showImageChooserEvent = MutableLiveData<Event<Unit>>()
    val showImageChooserEvent: LiveData<Event<Unit>> = _showImageChooserEvent

    fun showImageChooser() {
        _showImageChooserEvent.value = Event(Unit)
    }

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

        viewModelScope.launch(Dispatchers.IO) {
            val result = memosDao.getMemoById(memoId)
            launch(Dispatchers.Main) {
                result?.let {
                    onMemoLoaded(it)
                }
            }
        }
    }

    private fun onMemoLoaded(memo: Memo) {
        title.value = memo.title
        body.value = memo.body
        attachedImageList.addAll(memo.attachedImages)
        imageId = attachedImageList.last().id + 1
        _attachedImages.value = attachedImageList.toMutableList()
    }


    fun saveMemo() {
        val currentTitle = title.value
        val currentBody = body.value

        if (currentTitle == null || currentBody == null) {
            return
        }

        val currentMemoId = memoId
        if (isNewMemo || currentMemoId == -1) {
            viewModelScope.launch {
                memosDao.insertMemo(
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
                memosDao.insertMemo(
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
        _memoUpdatedEvent.value = Event(Unit)
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
                val isImage = contentType.startsWith("image/")

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
                    }
                }
            }
        }
    }

    override fun onAttachedImageClick(attachedImage: AttachedImage) {
        removeAttachedImage(attachedImage)
    }

    private fun removeAttachedImage(attachedImage: AttachedImage) {
        attachedImageList.remove(attachedImage)
        _attachedImages.value = attachedImageList.toMutableList()
    }
}
