package com.example.random_pets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var petImageURL = ""
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.petButton)
        imageView = findViewById(R.id.petImage)
        getNextImage(button, imageView)
    }

    private fun getDogImageURL() {
        val client = AsyncHttpClient()
        client["https://dog.ceo/api/breeds/image/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                petImageURL = json.jsonObject.getString("message")
                Log.d("petImageURL", "pet image URL set")
                Log.d("Dog", "response successful$json")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }

    private fun getCatImageURL() {
        val client = AsyncHttpClient()
        client["https://api.thecatapi.com/v1/images/search", object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                var resultsJSON = json.jsonArray.getJSONObject(0)
                petImageURL = resultsJSON.getString("url")
                Log.d("petImageURL", "pet image URL set")
                Log.d("Cat", "response successful$json")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Cat Error", errorResponse)
            }
        }]
    }


    private fun getNextImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            var random = Random.nextInt()
            if (random%2 == 0){
                getDogImageURL()
            } else {
                getCatImageURL()
            }



            Glide.with(this)
                .load(petImageURL)
                .fitCenter()
                .into(imageView)
        }

    }
}