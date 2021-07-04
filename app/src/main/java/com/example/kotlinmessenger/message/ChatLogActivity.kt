package com.example.kotlinmessenger.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinmessenger.databinding.ActivityChatLogBinding
import com.example.kotlinmessenger.models.ChatSenderItem
import com.example.kotlinmessenger.models.ChatUserItem
import com.xwray.groupie.GroupieAdapter

class ChatLogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Chat log"

        val adapterChatLog = GroupieAdapter()

        // for testing
        for( i in 1..10) {
            adapterChatLog.add(ChatSenderItem())
            adapterChatLog.add(ChatUserItem())
        }


        binding.recyclerViewChatLog.adapter = adapterChatLog
    }
}