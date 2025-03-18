package com.example.grubgo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aboutus)

        // Social Media Buttons
        val btnFacebook = findViewById<ImageButton>(R.id.btnFacebook)
        val btnInstagram = findViewById<ImageButton>(R.id.btnInstagram)
        val btnTwitter = findViewById<ImageButton>(R.id.btnTwitter)

        // Feedback Button and EditText
        val btnSubmitFeedback = findViewById<Button>(R.id.btnSubmitFeedback)
        val etFeedback = findViewById<EditText>(R.id.etFeedback)

        // Open Social Media Links
        btnFacebook.setOnClickListener { v: View? ->
            val facebookIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com")
            )
            startActivity(facebookIntent)
        }

        btnInstagram.setOnClickListener { v: View? ->
            val instagramIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com")
            )
            startActivity(instagramIntent)
        }

        btnTwitter.setOnClickListener { v: View? ->
            val twitterIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.twitter.com")
            )
            startActivity(twitterIntent)
        }

        // Handle Feedback Submission
        btnSubmitFeedback.setOnClickListener { v: View? ->
            val feedback = etFeedback.text.toString().trim { it <= ' ' }
            if (feedback.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter your feedback.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Show custom thank you message
                Toast.makeText(
                    this,
                    "Thank you for the feedback. Your feedback is valuable for us to keep growing and assisting our motto.",
                    Toast.LENGTH_LONG
                ).show()
                etFeedback.setText("") // Clear the feedback field
            }
        }
    }
}
