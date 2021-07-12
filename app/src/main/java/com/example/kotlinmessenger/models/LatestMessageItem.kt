package com.example.kotlinmessenger.models

import android.view.View
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.LatestMessageRowBinding
import com.xwray.groupie.viewbinding.BindableItem

class LatestMessageItem(val message: ChatMessage): BindableItem<LatestMessageRowBinding>() {
    override fun bind(viewBinding: LatestMessageRowBinding, position: Int) {
        //TODO("Initialize chat")
        viewBinding.latestMessage.text = message.text
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun initializeViewBinding(view: View): LatestMessageRowBinding {
        return  LatestMessageRowBinding.bind(view)
    }
}