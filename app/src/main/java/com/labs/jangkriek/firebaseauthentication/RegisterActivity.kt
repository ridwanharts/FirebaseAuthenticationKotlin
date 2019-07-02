package com.labs.jangkriek.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        btn_signup.setOnClickListener {
            val email = et_email_signup.text.toString().trim()
            val password = et_password_signup.text.toString().trim()

            if(email.isEmpty()){
                et_email_signup.error = "Email Required"
                et_email_signup.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_email_signup.error = "Valid Email Required"
                et_email_signup.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length<6){
                et_password_signup.error = "must be 6 character"
                et_password_signup.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)

        }

        tv_loginhere.setOnClickListener{startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))}
    }

    private fun registerUser(email: String, password: String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    login()
                }else{
                    task.exception?.message?.let{
                        toast(it)
                    }


                }
            }

    }

    override fun onStart() {
        super.onStart()

        mAuth.currentUser?.let {
            login()
        }
    }
}
