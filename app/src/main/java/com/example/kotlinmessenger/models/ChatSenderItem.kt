package com.example.kotlinmessenger.models

import android.view.View
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.ChatLogRecievedRowBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class ChatSenderItem: BindableItem<ChatLogRecievedRowBinding>() {
    override fun bind(viewBinding: ChatLogRecievedRowBinding, position: Int) {
        viewBinding.chatLogMessageReceived.text = "New phone. Who is dis?"
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/kotlinmessenger-9aa90." +
                "appspot.com/o/images%2Fa742b0ae-2d3f-41b3-96af-9c405ce3d0e0?alt=media&token=" +
                "6749621e-0d7d-4a92-bbb7-1d7398fc4a96").into(viewBinding.chatLogSenderImage)
    }

    override fun getLayout(): Int {
        return R.layout.chat_log_recieved_row
    }

    override fun initializeViewBinding(view: View): ChatLogRecievedRowBinding {
        return ChatLogRecievedRowBinding.bind(view)
    }
}