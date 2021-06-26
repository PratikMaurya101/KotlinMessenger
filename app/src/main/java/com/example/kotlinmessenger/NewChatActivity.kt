package com.example.kotlinmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinmessenger.databinding.ActivityNewChatBinding
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

        val adapter = GroupieAdapter()

        // [adding a new Item(ParticipantItem) instance to the adapter]
        adapter.add(ParticipantItem())  //might need to remove this and ^

        // [setting the cardView to the recycler view adapter]
        binding.recyclerviewNewchat.setAdapter(adapter)

        fetchUsers()

    }

    private fun fetchUsers() {
        val userReference = FirebaseDatabase.getInstance().getReference("/users")

        // research about this later
        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { userDetails ->
                    Log.d("NewChatActivity","USER:$userDetails")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}

