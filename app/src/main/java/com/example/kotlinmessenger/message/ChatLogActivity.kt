package com.example.kotlinmessenger.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kotlinmessenger.databinding.ActivityChatLogBinding
import com.example.kotlinmessenger.models.ChatMessage
import com.example.kotlinmessenger.models.ChatSenderItem
import com.example.kotlinmessenger.models.ChatUserItem
import com.example.kotlinmessenger.models.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupieAdapter

class ChatLogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatLogBinding

    private val adapterChatLog = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatLogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // getting the bundle data passed with intent from NewChatActivity
        val participantParcel = intent.getParcelableExtra<User>(NewChatActivity.PARTICIPANT_KEY)
        val imageUrl = participantParcel?.profileImageUrl
        Log.d(LOG_TAG, "participant details: ${participantParcel?.username}")

        // gets current user's data
        val currentUser = HomeScreen.currentUser
        Log.d(LOG_TAG,"current user:${currentUser?.username}")

        supportActionBar?.title = participantParcel?.username

        binding.recyclerViewChatLog.adapter = adapterChatLog

        // displays past messages on chat log
        if (participantParcel != null && currentUser != null) {
            listenForMessages(participantParcel, currentUser)
        }

        // sends message to the participant on send button click
        binding.buttonSendMessageChatLog.setOnClickListener {
            Log.d(LOG_TAG,"Attempt to send message...")

            if (participantParcel != null && currentUser != null) {
                performSendMessage(participantParcel, currentUser)
            }
        }
    }

    companion object {
        const val LOG_TAG = "ChatLogActivity"
    }

    // displays chat in ChatLog RV
    private fun listenForMessages(participant: User, user: User) {

        val fromId = user.uid
        val toId = participant.uid
        val refForChatLog = FirebaseDatabase.getInstance().getReference("/user_messages/$fromId/$toId")

        // event listener for every child of the data stored
        refForChatLog.addChildEventListener(object: ChildEventListener {
            // Listens for every chat added
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatData = snapshot.getValue(ChatMessage::class.java)

                // adds chat's from DB to the chatLog's RV
                if (chatData != null) {
                    // to add messages to RV w.r.t their location
                    Log.d(LOG_TAG, chatData.text)

                    // adding chats in correct representation and only in their respective logs
                    if (chatData.fromId == user.uid && chatData.toId == participant.uid) {
                        adapterChatLog.add(ChatUserItem(chatData.text, user))
                    }
                    else if (chatData.fromId == participant.uid && chatData.toId == user.uid){
                        adapterChatLog.add(ChatSenderItem(chatData.text,participant))
                    }
                }
                else {
                    Log.d(LOG_TAG,"No chats in DB")
                }
            }

            // [Start of unnecessary function for this project's scope]
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                //("Beyond this project's scope")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                //("Beyond this project's scope")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                //("Beyond this project's scope")
            }

            override fun onCancelled(error: DatabaseError) {
                //("Beyond this project's scope")
            }
            // [End of unnecessary function for this project's scope]
        })
    }

    // function to store the message sent by currentUser to FirebaseDatabase
    private fun performSendMessage(participant: User, user: User) {
        // message typed in by user
        val message = binding.editTextChatLog.text

        // get Id of current user and the chat participant
        // user because here we are sending message
        val fromId = user.uid
        // participant because ^ comment
        val toId = participant.uid

        if (message.isEmpty()) {
            Toast.makeText(baseContext,"Message Empty! Enter a text",Toast.LENGTH_SHORT).show()
            return
        }

        // get reference to the location in database where we want to save the data
        val fromReference = FirebaseDatabase.getInstance().getReference("/user_messages/$fromId/$toId").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user_messages/$toId/$fromId").push()

        val chatMessage = ChatMessage(fromReference.key.toString(), message.toString(), toId.toString(), fromId.toString(),System.currentTimeMillis()/1000)

        // send the chatMessage to database
        fromReference.setValue(chatMessage)
                .addOnSuccessListener {
                    Log.d(LOG_TAG,"Saved the chat message: ${fromReference.key}")
                    binding.editTextChatLog.text.clear()
                }
                .addOnFailureListener {
                    Log.d(LOG_TAG,"Error in saving chat: $it")
                }
        // adds message to DB with to-reference
        toReference.setValue((chatMessage))
    }
}