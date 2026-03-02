package com.example.mobileschool1.models

import kotlinx.serialization.Serializable

@Serializable
object TodoList

@Serializable
data class TodoDetails(val text: String, val isCompleted: Boolean)