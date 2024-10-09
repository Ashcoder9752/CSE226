package com.example.mydrawables

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // access the view containing animation and start animation
        val animationImageView = findViewById<ImageView>(R.id.animationImageView)
        (animationImageView.drawable as AnimationDrawable).start()

        // access the view containing custom drawable and set it
        val customDrawableImageView = findViewById<ImageView>(R.id.customDrawableImageView)
        customDrawableImageView.setImageDrawable(CustomCircleDrawable())
    }
}