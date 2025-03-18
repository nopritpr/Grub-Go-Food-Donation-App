package com.example.grubgo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.grubgo.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            firebaseAuth = FirebaseAuth.getInstance()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)
            setupNavigationListeners()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate: ${e.message}", e)
            Toast.makeText(this, "Error initializing app: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupNavigationListeners() {
        try {
            binding.donateLayout.setOnClickListener {
                navigateTo(DonateActivity::class.java)
            }

            binding.receiveLayout.setOnClickListener {
                navigateTo(ReceiveActivity::class.java)
            }

            binding.foodMapLayout.setOnClickListener {
                openGoogleMaps("Food donors near me")
            }

            binding.donationsLayout.setOnClickListener {
                navigateTo(CustomerCareActivity::class.java)
            }

            binding.historyLayout.setOnClickListener {
                navigateTo(HistoryActivity::class.java)
            }

            binding.aboutUsLayout.setOnClickListener {
                navigateTo(AboutUsActivity::class.java)
            }

            binding.profileLayout.setOnClickListener {
                navigateTo(ProfileActivity::class.java)
            }

            binding.signOutLayout.setOnClickListener {
                confirmSignOut()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error setting up listeners: ${e.message}", e)
            Toast.makeText(this, "Error setting up navigation: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGoogleMaps(query: String) {
        try {
            val uri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
     3       }
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error opening Google Maps: ${e.message}", e)
            Toast.makeText(this, "Error opening Google Maps: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun navigateTo(activityClass: Class<*>) {
        try {
            val intent = Intent(this, activityClass)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("MainActivity", "Navigation error: ${e.message}", e)
            Toast.makeText(this, "Error navigating to ${activityClass.simpleName}: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmSignOut() {
        try {
            // Confirmation dialog for sign out
            AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes") { _, _ ->
                    signOut()
                }
                .setNegativeButton("No", null)
                .show()
        } catch (e: Exception) {
            Log.e("MainActivity", "Sign out confirmation error: ${e.message}", e)
            Toast.makeText(this, "Error during sign out: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signOut() {
        try {
            // Firebase sign out
            firebaseAuth.signOut()

            // Google sign out
            googleSignInClient.signOut().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "You have been signed out.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Sign out failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error during sign out: ${e.message}", e)
            Toast.makeText(this, "Error during sign out: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
