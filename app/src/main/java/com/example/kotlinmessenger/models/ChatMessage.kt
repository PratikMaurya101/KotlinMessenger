package com.example.kotlinmessenger.models

// fromId is CurrentUser and toID is the ChatParticipant
data class ChatMessage(val id: String = "",
                       val text: String = "",
                       val toId: String = "",
                       val fromId: String = "",
                       val timeStamp: Long = 0)
