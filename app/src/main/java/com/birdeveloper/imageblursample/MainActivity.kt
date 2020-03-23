package com.birdeveloper.imageblursample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.birdeveloper.imageblur.ImageBlur

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var imageView2: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView2 = findViewById(R.id.imageView2)
        ImageBlur.withContext(this)
                .blur(25f)
                .asyncTask(true)
                .load(R.drawable.profile)
                .into(imageView2)
    }
}
