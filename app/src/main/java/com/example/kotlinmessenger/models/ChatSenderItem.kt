package com.example.kotlinmessenger.models

import android.view.View
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.ChatLogReceivedRowBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class ChatSenderItem(val chatText: String?, val sender: User): BindableItem<ChatLogReceivedRowBinding>() {
    override fun bind(viewBinding: ChatLogReceivedRowBinding, position: Int) {
        viewBinding.chatLogMessageReceived.text = chatText
        Picasso.get().load(sender.profileImageUrl).into(viewBinding.chatLogSenderImage)
    }

    override fun getLayout(): Int {
        return R.layout.chat_log_received_row
    }

    override fun initializeViewBinding(view: View): ChatLogReceivedRowBinding {
        return ChatLogReceivedRowBinding.bind(view)
    }
}