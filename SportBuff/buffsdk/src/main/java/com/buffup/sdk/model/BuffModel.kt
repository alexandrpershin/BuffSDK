package com.buffup.sdk.model

import com.buffup.sdk.api.response.BuffResponse


 data class BuffModel(
    val answers: List<Answer>,
    val author: Author,
    val question: Question,
    val secondsToShow: Int
)

 data class Answer(
    val title: String
)

 data class Author(
    val firstName: String,
    val image: String,
    val lastName: String
) {
    val fullName = "$firstName $lastName"
}

 data class Question(
    val title: String
)

 /**
  * Convert response object from DTO (Data transfer object) to model object
  * */

 fun BuffResponse.toModel(): BuffModel {
    return BuffModel(
        secondsToShow = result.time_to_show,
        question = Question(result.question.title ?: ""),
        author = Author(
            firstName = result.author.firstName ?: "",
            lastName = result.author.lastName ?: "",
            image = result.author.image ?: ""
        ),
        answers = result.answers?.map { Answer(it.title ?: "") } ?: arrayListOf()
    )
}