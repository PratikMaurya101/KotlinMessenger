package com.example.kotlinmessenger.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinmessenger.databinding.ActivityNewChatBinding
import com.example.kotlinmessenger.models.ParticipantItem
import com.example.kotlinmessenger.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupieAdapter

class NewChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // changing the action bar title
        supportActionBar?.title = "Chat with..."

        fetchUsers()

    }

    // make for sending KEY in intent and easily manageable(not sure)
    companion object {
        const val PARTICIPANT_KEY = "PARTICIPANT_KEY"
    }

    private fun fetchUsers() {
        val userReference = FirebaseDatabase.getInstance().getReference("/users")

        // TODO research about this later
        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val adapterNewChat = GroupieAdapter() //initialize adapter

                snapshot.children.forEach { userDetails ->
                    Log.d("NewChatActivity","USER:$userDetails")
                    val participant = userDetails.getValue(User::class.java)
                    if(participant != null) {
                        adapterNewChat.add(ParticipantItem(participant)) //add to the RV
                    }

                }
                // [setting the cardView to the recycler view adapter]
                binding.recyclerviewNewchat.adapter = adapterNewChat

                // [handles click event of RecyclerView]
                adapterNewChat.setOnItemClickListener { item, view ->

                    // casting item as participantItem  in order to use the userName under
                    // the particular participant item
                    /*
                     * What we doing here is first we are creating and object participantItem
                     * Which takes the data from 'item' in this itemClickListener ^
                     * Also we make the User.kt class Parcelable. Then we send the user object
                     * taken from the particular 'Item' from the ParticipantItem with the intent
                     * in form of parcel
                     */
                    val participantItem = item as ParticipantItem
                    Log.d("NewChatActivity","Open ChatLogActivity")
                    val intentToOpenChat = Intent(view.context, ChatLogActivity::class.java)
                    // sending User object as bundle data with intent under key PARTICIPANT_KEY
                    intentToOpenChat.putExtra(PARTICIPANT_KEY,participantItem.user)
                    startActivity(intentToOpenChat)
                    // finishes the activity NewChatActivity from back stack
                    finish()
                    Log.d("NewChatActivity","NewChatActivity successfully removed from " +
                            "back stack")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("NewChatActivity","Error in loading users:$error")
            }

        })
    }
}

