package com.runeanim.lineproject.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.runeanim.lineproject.model.AttachedImage

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