package com.buffup.sdk.api.response

import com.google.gson.annotations.SerializedName

/**
 * Pojo response from backend
* */

 class BuffResponse(
    @SerializedName("result") val result: ResultDto
)

 class ResultDto(
    @SerializedName("answers") val answers: List<AnswerDto>?,
    @SerializedName("author") val author: AuthorDto,
    val client_id: Int,
    val created_at: String?,
    val id: Int,
    @SerializedName("question") val question: QuestionDto,
    val stream_id: Int,
    @SerializedName("time_to_show") val time_to_show: Int
)

 class AnswerDto(
    val buff_id: Int,
    val id: Int,
    @SerializedName("image") val image: Map<String?, ImageDto>,
    @SerializedName("title") val title: String?
)

 class AuthorDto(
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("last_name") val lastName: String?
)

 class QuestionDto(
    val category: Int,
    val id: Int,
    @SerializedName("title") val title: String?
)

 class ImageDto(
    val id: String?,
    val key: String?,
    val url: String?
)

