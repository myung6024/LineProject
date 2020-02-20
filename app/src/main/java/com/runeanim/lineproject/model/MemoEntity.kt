package com.runeanim.lineproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos")
data class MemoEntity(
    val title : String,
    val body : String,
    val attachedImages: List<AttachedImage> = emptyList(),
    @ColumnInfo(name = "itemOrder") var order: Long,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)