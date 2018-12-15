package com.example.aroras.palan.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.aroras.palan.R


class LoginScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val btnSignIn= findViewById<Button>(R.id.btnSignIn)
        val btnSignUp= findViewById<Button>(R.id.btnSignUp)
        val txtSlogan=findViewById<TextView>(R.id.txtSlogan)

        btnSignIn.setOnClickListener{
            val signInIntent = Intent(this@LoginScreen,SignInActivity::class.java)
            startActivity(signInIntent)
        }

        btnSignUp.setOnClickListener{
            val signUpIntent = Intent(this@LoginScreen,SignUpActivity::class.java)
            startActivity(signUpIntent)

        }
    }

}
