package com.example.kotlinmessenger.models

import android.view.View
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.ChatLogRecievedRowBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class ChatSenderItem(private val senderImageUrl: String?): BindableItem<ChatLogRecievedRowBinding>() {
    override fun bind(viewBinding: ChatLogRecievedRowBinding, position: Int) {
        viewBinding.chatLogMessageReceived.text = "New phone. Who is dis?"
        Picasso.get().load(senderImageUrl).into(viewBinding.chatLogSenderImage)
    }

    override fun getLayout(): Int {
        return R.layout.chat_log_recieved_row
    }

    override fun initializeViewBinding(view: View): ChatLogRecievedRowBinding {
        return ChatLogRecievedRowBinding.bind(view)
    }
}