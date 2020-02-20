package com.runeanim.lineproject.ui.addeditmemo

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.AttachedImage
import com.runeanim.lineproject.model.AttachedImageType
import com.runeanim.lineproject.model.MemoEntity
import com.runeanim.lineproject.util.Event
import kotlinx.coroutines.launch

class AddEditMemoViewModel(
    private val memosDao: MemosDao
) : ViewModel() {
    private val _attachedImages = MutableLiveData<List<AttachedImage>>(emptyList())
    val attachedImages: LiveData<List<AttachedImage>> = _attachedImages

    var pos = 0

    private val attachedImageList = arrayListOf<AttachedImage>()

    private val _memoUpdatedEvent = MutableLiveData<Event<Unit>>()
    val memoUpdatedEvent: LiveData<Event<Unit>> = _memoUpdatedEvent

    private val _showImageChooserEvent = MutableLiveData<Event<Unit>>()
    val showImageChooserEvent: LiveData<Event<Unit>> = _showImageChooserEvent

    fun showImageChooser() {
        _showImageChooserEvent.value = Event(Unit)
    }

    fun saveMemo(title: String, body: String) {
        viewModelScope.launch {
            memosDao.insertMemo(
                MemoEntity(
                    title,
                    body,
                    attachedImageList,
                    System.currentTimeMillis()
                )
            )
        }
        _memoUpdatedEvent.value = Event(Unit)
    }

    fun addAttachedImages(attachedImages: List<Uri>) {
        attachedImageList.addAll(attachedImages.map {
            AttachedImage(
                pos++,
                AttachedImageType.URI,
                it.path!!
            )
        })
        _attachedImages.value = attachedImageList.toMutableList()
    }

    fun removeAttachedImage(attachedImage: AttachedImage) {
        attachedImageList.remove(attachedImage)
        _attachedImages.value = attachedImageList.toMutableList()
    }
}
