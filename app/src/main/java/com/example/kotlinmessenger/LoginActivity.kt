package com.example.kotlinmessenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinmessenger.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity: AppCompatActivity(){

    //[Declare auth]
    private lateinit var auth: FirebaseAuth

    //[declare variable of ViewBinding for activity_login.xml layout file]
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [initialize firebase auth]
        auth = Firebase.auth

        //[inflate the ActivityLoginBinding class]
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        //[sets the content view to the inflated ActivityLoginBinding file]
        setContentView(view)

        //[setting up to take input as soon as login button is clicked]
        binding.loginButtonLogIn.setOnClickListener {
            //[function call to login_user]
            loginUser()
        }

        //[setting up to change activity as soon as register text is clicked]
        binding.registrationLink.setOnClickListener {
            //[ function call to start_main_activity ]
            startRegisterActivity()
        }
    }

    // [ START function to Login user ]
    public fun loginUser(){

        // [initialize email and password fields and values respectively ]
        val email_entered = binding.emailaddressEdittextSignIn.text.toString()
        val password_entered = binding.passwordEdittextSignIn.text.toString()

        // [checking whether the fields i.e. email and password is empty or and prevent from crashing]
        if(email_entered.isEmpty() || password_entered.isEmpty()){
            Toast.makeText(applicationContext,"Please enter the details above",Toast.LENGTH_SHORT).show()
            return
        }

        //[START logging in user]
        auth.signInWithEmailAndPassword(email_entered,password_entered)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){

                        // [Logging the data that login was success]
                        Log.d("LoginActivity","singInWithEmailAndPassword:success")

                        // [Updating log with the values of email and password entered]
                        Log.d("LoginActivity","email:$email_entered")
                        Log.d("LoginActivity","pwd:$password_entered")

                        Toast.makeText(baseContext,"Sign-in success",Toast.LENGTH_SHORT).show()

                        // [initializing current user ]
                        val user = auth.currentUser
                    }
                    else{

                        // [Sign-in fails]
                        Log.d("LoginActivity","singInWithEmailAndPassword:failed")
                        Toast.makeText(baseContext,"Sign-in failed",Toast.LENGTH_SHORT).show()
                    }
                }
        //[STOP logging in user]


    }
    // [ STOP function to login user ]


    // [ START function to change activity to RegisterActivity ]
    public fun startRegisterActivity(){
        Log.d("LoginActivity","Try to show registration activity")
        Toast.makeText(applicationContext, "Redirecting", Toast.LENGTH_SHORT).show()

        // creating Intent to change activity to RegisterActivity
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }
    // [ STOP function start_register_activity ]
}