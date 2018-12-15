package com.example.aroras.palan.Activity

import android.content.ClipData
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.aroras.palan.Database.Database
import com.example.aroras.palan.Models.AccessoriesItems
import com.example.aroras.palan.Models.MenItems
import com.example.aroras.palan.Models.Order
import com.example.aroras.palan.Models.WomenItems
import com.example.aroras.palan.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_women_details.*

import kotlinx.android.synthetic.main.items.*
import kotlinx.android.synthetic.main.items.view.*

class ItemAccessoriesDetails : AppCompatActivity() {

    var itemId: String = "";
    val databaseReference = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_women_details)

        collapsing.setExpandedTitleTextAppearance(R.style.ExpandeAppbar)
        collapsing.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar)

        var extras: Bundle
        var newString: String

        if (savedInstanceState == null) { //fetching extra data passed with intents ina bundle type variable
            extras = intent.extras
            if (extras == null) {
                newString = ""
                Toast.makeText(this, newString, Toast.LENGTH_SHORT).show()
            } else {
                //fetching the string passed using extras
                newString = extras.getString("ItemId")
                //Toast.makeText(this,newString+"**",Toast.LENGTH_SHORT).show()
            }
            databaseReference.child("AccessoriesItems").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (i in p0.children) {

                        if (i.key == newString) {

                            val currentItem = i.getValue(AccessoriesItems::class.java)
                            Log.d(itemId, currentItem!!.Name + " " + currentItem.Image)


                            Picasso.with(baseContext).load(currentItem?.Image).fit()
                                    .into(itm_image)

                            collapsing.title = currentItem?.Name

                            itm_price.text = currentItem?.Price
                            itm_name.text = currentItem?.Name
                            itm_description.text = currentItem?.Description

                            btnCart.setOnClickListener {
                                Database(baseContext).addToCart(Order(
                                        itemId,
                                        currentItem?.Name,
                                        number_button.number,
                                        currentItem?.Price,
                                        currentItem?.Discount
                                ))
                                Toast.makeText(this@ItemAccessoriesDetails,"Added to Cart",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            })
        }
    }

}
