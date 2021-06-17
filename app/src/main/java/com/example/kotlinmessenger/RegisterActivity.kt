 package com.example.kotlinmessenger

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.kotlinmessenger.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


 class RegisterActivity : AppCompatActivity() {

     //[START declare_auth]
     private lateinit var auth: FirebaseAuth
     //[STOP declare_auth]

     // [Declare variable of ViewBinding for activity_main.xml layout file]
     private lateinit var binding: ActivityMainBinding

     public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //[initialise_auth]
        auth = Firebase.auth

        //[inflate the ActivityMainBinding class file]
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        //[select the content view to the inflated ActivityMainBinding file]
        setContentView(view)

        //[setting up to take inputs and do user creation as soon a registration button is clicked]
        binding.registerButtonRegistration.setOnClickListener {

            //[START account creation process]
            createAccount()
            //[END account creation process]

        }

        // [setting up to change activity to LoginActivity]
         binding.signInLink.setOnClickListener {
             //[ function call to start_login_activity ]
             startLoginActivity()

         }
         // [stop of changing activity to LoginActivity]

         // [setting up to create user profile image]
         binding.selectImageButtonRegistration.setOnClickListener {

             selectImage()

         }
     }


     // [START function to create account]
     public fun createAccount() {

         // [initialize email, password and user-name fields]
         val email_value = binding.emailaddressEdittextRegistration.getText().toString()
         val userName_value = binding.usernameEdittextRegistration.getText().toString()
         val password_value = binding.passwordEdittextRegistration.getText().toString()

         // [checking whether the fields i.e. email and password is empty or and prevent from crashing]
         if(email_value.isEmpty() || password_value.isEmpty()){
             Toast.makeText(applicationContext,"Please enter the details above",Toast.LENGTH_SHORT).show()
             return
         }

         //[ specify conditions for writing password ]
         if(password_value.length < 6){
             Toast.makeText(applicationContext,"Please enter a password with minimum character length 6",Toast.LENGTH_SHORT).show()
             return
         }

         // [START creating new account]
         auth.createUserWithEmailAndPassword(email_value, password_value)
                 .addOnCompleteListener(this) { task ->
                     if (task.isSuccessful) {

                         // [Sign in success]
                         Log.d("RegisterActivity", "createUserWithEmail:success")

                         // [logging the data entered in email, password and username field in RegisterActivity]
                         Log.d("RegisterActivity","Username: $userName_value")
                         Log.d("RegisterActivity", "Email is: $email_value")
                         Log.d("RegisterActivity", "Password is: $password_value")

                         // [initialize current user]
                         val user = auth.currentUser

                         Toast.makeText(baseContext,"Account successfully created",Toast.LENGTH_SHORT).show()

                     } else {
                         // [If sign in fails, display a message to the user.]
                         Log.d("RegisterActivity", "createUserWithEmail:failure", task.exception)
                         Toast.makeText(baseContext, "Failed to create an account",Toast.LENGTH_SHORT).show()

                     }
                 }
         // [STOP creating new account]


     }
     // [STOP function to create account]

     //[START function to Start LoginActivity]
     public fun startLoginActivity(){

         //[update log to reflect request ot change activity]
         Log.d("RegisterActivity", "Try to show login activity")
         Toast.makeText(applicationContext, "Redirecting", Toast.LENGTH_SHORT).show()

         // [making Intent to change from RegisterActivity to LoginActivity]
         val intent_to_run_loginActivity = Intent(this, LoginActivity::class.java)
         startActivity(intent_to_run_loginActivity)


     }
     // [STOP function to Start LoginActivity]

     public fun selectImage(){
         Log.d("RegisterActivity","Try to show image selector")

         val intent_to_run_imageActivity = Intent(Intent.ACTION_PICK)
         intent_to_run_imageActivity.type = "image/*"
         startActivityForResult(intent_to_run_imageActivity,0)
     }

     // [START function to display result after getting result from intent_to_run_imageActivity]
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null ){

             // [check what the input image was]
             Log.d("RegisterActivity","An image was selected")

             // [getting uri from the "data" passed in the onActivityResult function]
             val selected_image_uri = data.data

             // [getting bitmap image data from uri]
             val bitmap_image_selected = MediaStore.Images.Media.getBitmap(this.contentResolver, selected_image_uri)

             // [projecting the image selected on the button]
             val bitmap_image_selected_drawable = BitmapDrawable(bitmap_image_selected)
             binding.selectImageButtonRegistration.setBackgroundDrawable(bitmap_image_selected_drawable)

         }
     }
     // [STOP function onActivityResult]

}