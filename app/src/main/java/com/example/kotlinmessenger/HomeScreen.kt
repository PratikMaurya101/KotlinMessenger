package com.example.kotlinmessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.kotlinmessenger.databinding.ActivityHomeScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val currentUser = "Current User is: ${auth.currentUser?.email}"

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextTextPersonName.text = currentUser //for check which user is signed in, remove later

    }

    // [Function to perform action when a menu action is selected]
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
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

    private fun changeActivityTo(activity: String) {

        when(activity) {
            "registerActivity" -> {
                Log.d("HomeScreenActivity","Signing out user")
                Toast.makeText(baseContext,"Signing out",Toast.LENGTH_SHORT).show()

                val intentToRunRegisterActivity = Intent(this,RegisterActivity::class.java)
                startActivity(intentToRunRegisterActivity)
            }
            "newChatActivity" -> {
                //change activity to new chat
                Log.d("HomeScreenActivity","Opening new chat page")
                val intentToRunNewChatActivity = Intent(this,NewChatActivity::class.java)
                startActivity(intentToRunNewChatActivity)
            }
        }

    }

    // [Function to create menu action bar]
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // [initializing a variable for menu action bar inflation]
        menuInflater.inflate(R.menu.home_screen_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}