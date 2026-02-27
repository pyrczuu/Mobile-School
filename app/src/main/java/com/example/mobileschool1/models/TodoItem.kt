package com.example.mobileschool1.models

import kotlinx.serialization.Serializable

@Serializable
data class TodoItem(
    val id: Long,
    var name: String,
    var isCompleted: Boolean
)
