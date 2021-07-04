package com.example.kotlinmessenger.models

import android.view.View
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.ChatLogReplyRowBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class ChatUserItem: BindableItem<ChatLogReplyRowBinding>() {
    override fun bind(viewBinding: ChatLogReplyRowBinding, position: Int) {
        viewBinding.chatLogMessageSent.text = "It's a me, Mario!"
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/kotlinmessenger-9aa90." +
                "appspot.com/o/images%2Fd87bee33-fe1f-47d1-8f9e-44bcba24dd3d?alt=" +
                "media&token=541eb46a-8f43-41d5-9020-79b768c7f110").into(viewBinding.chatLogUserImage)
    }

    override fun getLayout(): Int {
        return R.layout.chat_log_reply_row
    }

    override fun initializeViewBinding(view: View): ChatLogReplyRowBinding {
        return ChatLogReplyRowBinding.bind(view)
    }
}