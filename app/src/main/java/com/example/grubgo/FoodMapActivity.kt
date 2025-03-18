package com.example.grubgo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.foodmap)

        val focusLocationButton = findViewById<FloatingActionButton>(R.id.focusLocation)

        focusLocationButton.setOnClickListener {
            openGoogleMapsWithSearch("Food Donors Near Me")
        }
    }

    private fun openGoogleMapsWithSearch(query: String) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // Fallback: Show a message if Google Maps is not installed
            val fallbackIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(fallbackIntent)
        }
    }
}
