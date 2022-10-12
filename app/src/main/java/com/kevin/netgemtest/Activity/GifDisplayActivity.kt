package com.kevin.netgemtest.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kevin.netgemtest.databinding.ActivityGifDisplayBinding

class GifDisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGifDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGifDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.extras!!.get("imageUrl").toString()
        //we receive the gifId from the previous activity
        //val gifId = intent.extras!!.get("GifId").toString()
        Glide.with(applicationContext).load(imageUrl)
            .into(binding.monimage)

        //We set display the corresponding gif
        /*GPHCore.gifById("Pzrzs9azvyaSYZWz07") { result, e ->
            binding.gifView.setMedia(result?.data, RenditionType.original)
            e?.let {

            }
        }*/
        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}