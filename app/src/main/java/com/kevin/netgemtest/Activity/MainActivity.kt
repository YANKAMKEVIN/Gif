package com.kevin.netgemtest.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.giphy.sdk.core.GPHCore
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.models.enums.RenditionType
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.GiphyFrescoHandler
import com.giphy.sdk.ui.themes.GPHTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import com.kevin.netgemtest.R
import com.kevin.netgemtest.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var settings = GPHSettings(GridType.waterfall, GPHTheme.Dark)
    var contentType = GPHContentType.gif


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Giphy Configuration
        Giphy.configure(this, resources.getString(R.string.api_key), true, frescoHandler = object :
            GiphyFrescoHandler {
            override fun handle(imagePipelineConfigBuilder: ImagePipelineConfig.Builder) {
            }

            override fun handle(okHttpClientBuilder: OkHttpClient.Builder) {
            }
        })
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var view = findViewById<View>(R.id.searchFragment)
        view.visibility = View.GONE

        //Display random gif at launch
        displayRandomGif()

        //Settings configuration
        settings.mediaTypeConfig = arrayOf(GPHContentType.gif)
        settings.selectedContentType = GPHContentType.gif

        //We call the fragment GiphyDialogFragment when the button is clicked
        binding.gifButton.setOnClickListener {
            binding.cardView.visibility = View.GONE
            binding.gifButton.visibility = View.GONE
            view.visibility =  View.VISIBLE

        }
    }

    /*
     * Function used to display a gif among 10 gifs randomly
     */

    private fun displayRandomGif() {
        val randomGifUrl =
            "https://api.giphy.com/v1/gifs/random?api_key=" + resources.getString(R.string.api_key)

        //Getting the data
        val requestRandomGif = JsonObjectRequest(
            Request.Method.GET, randomGifUrl, null,
            { response ->
                val data = response.getJSONObject("data")
                val gifId = data.getString("id")
                GPHCore.gifById(gifId) { result, e ->
                    binding.gifView.setMedia(result?.data, RenditionType.original)
                    e?.let {}

                }
            },
            { error ->
                error.printStackTrace()
            }
        )
        Volley.newRequestQueue(applicationContext).add(requestRandomGif)
    }

    /*
    * Listener used to listen to every gif Actions
    */
    private fun getGifSelectionListener() = object : GiphyDialogFragment.GifSelectionListener {
        override fun onGifSelected(
            media: Media,
            searchTerm: String?,
            selectedContentType: GPHContentType
        ) {
            Timber.d("onGifSelected")
            //We send the GidID to the next Activity
            val intent = Intent(this@MainActivity, GifDisplayActivity::class.java)
            intent.putExtra("GifId", media.id)
            startActivity(intent)
            contentType = selectedContentType
        }

        override fun onDismissed(selectedContentType: GPHContentType) {
            Timber.d("onDismissed")
            contentType = selectedContentType
        }

        override fun didSearchTerm(term: String) {
            Timber.d("didSearchTerm $term")
        }
    }
}