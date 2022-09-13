package com.fara.giphy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fara.giphy.domain.models.GifDomain

@Entity(tableName = "gifs")
data class GifEntity(
    @PrimaryKey
    val id: String,
    val offset: Int,
    val previewUrl: String,
    val originUrl: String,
    val query: String
)

fun GifDomain.toEntity(query: String): GifEntity {
    return GifEntity(
        id = id,
        offset = offset,
        previewUrl = previewUrl,
        originUrl = originUrl,
        query = query
    )
}
