package com.example.aroras.palan.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.aroras.palan.Models.UserInfo
import com.example.aroras.palan.R
import com.google.firebase.database.*
import com.rengwuxian.materialedittext.MaterialEditText

class SignUpActivity : AppCompatActivity() {

    var edtPhone : MaterialEditText?=null;
    var edtName : MaterialEditText?=null;
    var edtPassword : MaterialEditText?=null;
    var btnSignUp : Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtPhone=findViewById<MaterialEditText>(R.id.edtphone)
        edtName=findViewById<MaterialEditText>(R.id.edtname)
        edtPassword=findViewById<MaterialEditText>(R.id.edtpassword)
        btnSignUp= findViewById<Button>(R.id.btnSignUp)

        var database : FirebaseDatabase
        var table_user : DatabaseReference
        database = FirebaseDatabase.getInstance()
        table_user =database.reference.child("user")

        btnSignUp?.setOnClickListener {

            var mDialog = ProgressDialog(this)
            mDialog.setMessage("Please Wait ... !")
            mDialog.show()

            table_user.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.child(edtPhone?.text.toString()).exists()){
                        mDialog.dismiss()
                        Toast.makeText(this@SignUpActivity, "Phone Number already registered !", Toast.LENGTH_SHORT).show()
                        //val startAct = Intent(this@SignUpActivity,MainActivity::class.java)
                        //startActivity(startAct)
                    }
                    else{
                        mDialog.dismiss()
                        //var user = UserInfo(edtName?.text.toString(),edtPassword?.text.toString())
                        var user =UserInfo(edtName?.text.toString(),edtPassword?.text.toString(),edtPhone?.text.toString())
                        table_user.child(edtPhone?.text.toString()).setValue(user)
                        Toast.makeText(this@SignUpActivity, "Sign Up Successful !", Toast.LENGTH_SHORT).show()
                        finish()
                        //val startAct = Intent(this@SignUpActivity,MainActivity::class.java)
                        //startActivity(startAct)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })

        }
    }
}
