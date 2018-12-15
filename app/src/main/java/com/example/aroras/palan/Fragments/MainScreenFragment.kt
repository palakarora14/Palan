package com.example.aroras.palan.Fragments


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.aroras.palan.Models.Category
import com.example.aroras.palan.Models.CategoryAdapter
import com.example.aroras.palan.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_main_screen.*


class MainScreenFragment : AppCompatActivity(){
    val databaseReference=FirebaseDatabase.getInstance().getReference()
    val categoryArray=ArrayList<Category>()
    lateinit var adapter:CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main_screen)

        adapter=CategoryAdapter(categoryArray)
        recycler_menu.layoutManager=LinearLayoutManager(this)
        recycler_menu.adapter=adapter


        databaseReference.child("Category").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children){
                    val categoryChildern=i.getValue(Category::class.java)
                    Log.d("Category",categoryChildern!!.Name+" " + categoryChildern.Image)
                    categoryArray.add(categoryChildern)
                    adapter.notifyDataSetChanged()
                }

            }

        })
    }

    }



