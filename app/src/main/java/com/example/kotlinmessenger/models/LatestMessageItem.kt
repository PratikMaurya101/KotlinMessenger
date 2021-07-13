package com.example.kotlinmessenger.models

import android.view.View
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.LatestMessageRowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

class LatestMessageItem(val message: ChatMessage): BindableItem<LatestMessageRowBinding>() {

    var chatParticipant: User? = null

    override fun bind(viewBinding: LatestMessageRowBinding, position: Int) {
        //TODO("Initialize chat")
        viewBinding.latestMessage.text = message.text

        val chatParticipantId = if (message.fromId == FirebaseAuth.getInstance().uid) {
            message.toId
        }
        else {
            message.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatParticipantId")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatParticipant = snapshot.getValue(User::class.java)
                if (chatParticipant != null) {
                    viewBinding.senderNameHomeScreen.text = chatParticipant?.username
                    Picasso.get().load(chatParticipant?.profileImageUrl).into(viewBinding.senderImageHomeScreen)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun initializeViewBinding(view: View): LatestMessageRowBinding {
        return  LatestMessageRowBinding.bind(view)
    }
}