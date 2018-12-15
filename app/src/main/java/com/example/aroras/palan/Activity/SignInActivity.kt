package com.example.aroras.palan.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.aroras.palan.Models.UserInfo
import com.example.aroras.palan.R
import com.example.aroras.palan.Models.common
import com.google.firebase.database.*
import com.rengwuxian.materialedittext.MaterialEditText

class SignInActivity : AppCompatActivity() {

    var edtPhone :MaterialEditText ?=null;
    var edtPassword :MaterialEditText ?=null;
    var btnSignIn : Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        edtPhone=findViewById(R.id.edtphone)
        edtPassword=findViewById<MaterialEditText>(R.id.edtpassword)
        btnSignIn=findViewById<Button>(R.id.btnSignIn)

        var database = FirebaseDatabase.getInstance()
        var table_user :DatabaseReference
        table_user =database.reference.child("user")

        btnSignIn?.setOnClickListener {
            var mDialog = ProgressDialog(this)
            mDialog.setMessage("Please Wait ... !")
            mDialog.show()

            Log.d("Error","Entered before")

            table_user.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@SignInActivity,"Cancelled",Toast.LENGTH_SHORT).show()
                }
                override fun onDataChange(p0: DataSnapshot) {

                    //check if user Exist in Database
                    Log.d("User",p0.child(edtPhone!!.text.toString()).exists().toString())
                    if (p0.child(edtPhone!!.text.toString()).exists()) {
                        //to get info.
                        mDialog.dismiss()
                        var user: UserInfo = p0?.child(edtPhone?.text.toString())!!.getValue(UserInfo::class.java)!!

                        //set Phone
                        user.setPhone(edtPhone?.text.toString())

                        if (user.getPassword().equals(edtPassword?.text.toString()))
                        {
                            Toast.makeText(this@SignInActivity, "Sign in Successful !", Toast.LENGTH_SHORT).show()
                            val startAct = Intent(this@SignInActivity,MainActivity::class.java)

                            common.currentUser= user

                            startActivity(startAct)
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@SignInActivity, "Wrong Password !", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        mDialog.dismiss()
                        Toast.makeText(this@SignInActivity, "User doesn't exist in Database , Please Sign Up first !",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}
