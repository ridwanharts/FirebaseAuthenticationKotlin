package com.labs.jangkriek.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            val email = et_login_email.text.toString().trim()
            val password = et_login_password.text.toString().trim()

            if(email.isEmpty()){
                et_login_email.error = "Email Required"
                et_login_password.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_login_email.error = "Valid Email Required"
                et_login_email.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length<6){
                et_login_password.error = "must be 6 character"
                et_login_password.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)

        }

        tv_register.setOnClickListener{startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))}
    }

    private fun loginUser(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
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
