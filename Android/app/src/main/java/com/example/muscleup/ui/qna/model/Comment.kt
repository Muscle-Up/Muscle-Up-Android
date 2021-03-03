package com.example.muscleup.ui.qna.model

data class Comment(val commentId : Int,
                   val content : String,
                   val writer : String,
                   val createdAt : String,
                   val subComments : List<SubComment>)