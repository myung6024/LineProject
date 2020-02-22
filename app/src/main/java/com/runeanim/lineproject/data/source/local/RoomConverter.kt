package com.runeanim.lineproject.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.runeanim.lineproject.data.model.AttachedImage

class RoomConverter {
    val gson = Gson()

    @TypeConverter
    fun fromAttachedImageList(attachedImages: List<AttachedImage>?): String? {
        if (attachedImages == null) {
            return null
        }
        val type = object : TypeToken<List<AttachedImage>>() {

        }.type
        return gson.toJson(attachedImages, type)
    }

    @TypeConverter
    fun toAttachedImageList(attachedImagesString: String?): List<AttachedImage>? {
        if (attachedImagesString == null) {
            return null
        }
        val type = object : TypeToken<List<AttachedImage>>() {

        }.type
        return gson.fromJson(attachedImagesString, type)
    }
}