package com.example.aroras.palan.Activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import com.example.aroras.palan.Adapters.NavigationDrawerAdapter
import com.example.aroras.palan.Models.Category
import com.example.aroras.palan.Models.CategoryAdapter
import com.example.aroras.palan.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_main_screen.*
import android.Manifest.permission
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.READ_SMS
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat



class MainActivity : AppCompatActivity() {

    val databaseReference=FirebaseDatabase.getInstance().getReference()
    val categoryArray=ArrayList<Category>()
    lateinit var adapter: CategoryAdapter

    var navigationDrawericonList :ArrayList<String> = arrayListOf()

    var images_for_navdrawer = intArrayOf(R.drawable.icon_home,R.drawable.icon_women,R.drawable.icon_men,
            R.drawable.icon_accessories,R.drawable.icon_themestore,
            R.drawable.icon_youraccount,R.drawable.icon_aboutus)
            //,R.drawable.icon_yourorders
            //R.drawable.icon_ngo
            //R.drawable.icon_karmabucket

    object Statified{
    var drawerLayout : DrawerLayout ?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbar =findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("HOME")

        setSupportActionBar(toolbar)

//        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest .permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS), 101)
//        }

        adapter=CategoryAdapter(categoryArray)
        recycler_menu.layoutManager=LinearLayoutManager(this)
        recycler_menu.adapter=adapter


        MainActivity.Statified.drawerLayout = findViewById(R.id.drawer_layout)

        //Floating Action Button Functionality
        var fab =findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            var cartIntent=Intent(this@MainActivity,Cart::class.java)
            startActivity(cartIntent)
        }

        navigationDrawericonList.add("Home")
        navigationDrawericonList.add("Women")
        navigationDrawericonList.add("Men")
        navigationDrawericonList.add("Accessories")
        navigationDrawericonList.add("Find Product")
        //navigationDrawericonList.add("Karma Bucket")
        //navigationDrawericonList.add("NGOs")
        //navigationDrawericonList.add("Your Orders")
        navigationDrawericonList.add("Your Account")
        navigationDrawericonList.add("About Us")


        val toggle  = ActionBarDrawerToggle(this@MainActivity, Statified.drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        MainActivity.Statified.drawerLayout?.addDrawerListener(toggle)

        toggle.syncState()

        val _navigationAdapter= NavigationDrawerAdapter(navigationDrawericonList, images_for_navdrawer
                , this)

        _navigationAdapter.notifyDataSetChanged()

        val navigation_recycler_view  =findViewById<RecyclerView>(R.id.navigation_recycler_view)

        navigation_recycler_view.layoutManager= LinearLayoutManager(this) as RecyclerView.LayoutManager?
        navigation_recycler_view.itemAnimator=DefaultItemAnimator()
        navigation_recycler_view.adapter=_navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)

        databaseReference.child("Category").addValueEventListener(object : ValueEventListener {
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

    override fun onStart() {
        super.onStart()
    }
}
