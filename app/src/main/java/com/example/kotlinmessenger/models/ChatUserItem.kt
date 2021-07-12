package com.example.kotlinmessenger.models

import android.view.View
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.ChatLogReplyRowBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class ChatUserItem(val chatText: String, val user: User): BindableItem<ChatLogReplyRowBinding>() {
    override fun bind(viewBinding: ChatLogReplyRowBinding, position: Int) {
        viewBinding.chatLogMessageSent.text = chatText
        Picasso.get().load(user.profileImageUrl).into(viewBinding.chatLogUserImage)
    }

    override fun getLayout(): Int {
        return R.layout.chat_log_reply_row
    }

    override fun initializeViewBinding(view: View): ChatLogReplyRowBinding {
        return ChatLogReplyRowBinding.bind(view)
    }
}