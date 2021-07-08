package com.example.kotlinmessenger.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.ActivityHomeScreenBinding
import com.example.kotlinmessenger.models.User
import com.example.kotlinmessenger.registerlogin.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class HomeScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        // function call to get current user
        getCurrentUser(auth)


        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextTextPersonName.text = "Current User is: ${auth.currentUser?.email}" //for check which user is signed in, remove later

    }

    companion object {
        const val LOG_TAG = "HomeScreenActivity"
        var currentUser: User? = null
    }

    // [Function to perform action when a menu action is selected]
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.log_out -> {
                auth.signOut()
                changeActivityTo("registerActivity")
            }
            R.id.new_chat -> {
                // implement method to start new chat
                changeActivityTo("newChatActivity")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // [Function to change Activity]
    private fun changeActivityTo(activity: String) {

        when(activity) {
            "registerActivity" -> {
                Log.d(LOG_TAG,"Signing out user")
                Toast.makeText(baseContext,"Signing out",Toast.LENGTH_SHORT).show()

                val intentToRunRegisterActivity = Intent(this, RegisterActivity::class.java)
                startActivity(intentToRunRegisterActivity)
            }
            "newChatActivity" -> {
                //change activity to new chat
                Log.d(LOG_TAG,"Opening new chat page")
                val intentToRunNewChatActivity = Intent(this, NewChatActivity::class.java)
                startActivity(intentToRunNewChatActivity)
            }
        }

    }

    // function the get the details of user in an object
    private fun getCurrentUser(auth: FirebaseAuth) {

        val currentUserReference = FirebaseDatabase.getInstance().getReference("/users")

        Log.d(LOG_TAG,"Fetching current user's Details")
        Log.d(LOG_TAG,"UID from auth: ${auth.currentUser?.uid}")

        // retrieves the data of current user and saves it into currentUser object
        currentUserReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    if(user != null && user.uid == auth.currentUser?.uid) {
                        currentUser = user
                        Log.d(LOG_TAG,"currentUser: ${currentUser?.username}")

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(LOG_TAG,"Error in retrieving user data")
            }
        })


    }

    // [Function to create custom menu action bar]
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // [initializing a variable for menu action bar inflation]
        menuInflater.inflate(R.menu.home_screen_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}