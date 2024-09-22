package com.example.mycoroutine

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycoroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.simulateDelay.setOnClickListener {
            for (i in 1..1_00_00_00_000) {}
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
        }

        binding.checkUI.setOnClickListener {
            Toast.makeText(this, "UI is fine", Toast.LENGTH_SHORT).show()
        }

        binding.filePage.setOnClickListener {
            val intent = Intent(this@MainActivity, FileActivity::class.java)
            startActivity(intent)
        }

        binding.imagePage.setOnClickListener {
            val intent = Intent(this@MainActivity, ImageActivity::class.java)
            startActivity(intent)
        }

        binding.videoPage.setOnClickListener {
            val intent = Intent(this@MainActivity, VideoActivity::class.java)
            startActivity(intent)
        }
    }
}