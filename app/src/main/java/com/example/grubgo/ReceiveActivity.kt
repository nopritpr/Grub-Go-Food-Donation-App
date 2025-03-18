package com.example.grubgo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ReceiveActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var spinner: Spinner
    private lateinit var imgFood1: ImageView
    private lateinit var imgFood2: ImageView
    private lateinit var imgFood3: ImageView
    private lateinit var tvExpiry: TextView
    private lateinit var tvWhenMade: TextView
    private lateinit var tvQuantity: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvDonorInfo: TextView
    private lateinit var btnSubmitRequest: Button

    private var selectedDonation: DBHelper.Donation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receive_layout)

        dbHelper = DBHelper.getInstance(this)

        // Initialize views
        val etReceiverName = findViewById<EditText>(R.id.etReceiverName)
        val etReceiverContact = findViewById<EditText>(R.id.etReceiverContact)
        spinner = findViewById(R.id.spAvailableDonations)
        imgFood1 = findViewById(R.id.imgFood1)
        imgFood2 = findViewById(R.id.imgFood2)
        imgFood3 = findViewById(R.id.imgFood3)
        tvExpiry = findViewById(R.id.tvExpiry)
        tvWhenMade = findViewById(R.id.tvWhenMade)
        tvQuantity = findViewById(R.id.tvQuantity)
        tvAddress = findViewById(R.id.tvAddress)
        tvDonorInfo = findViewById(R.id.tvDonorInfo)
        btnSubmitRequest = findViewById(R.id.btnSubmitRequest)

        // Load available donations into spinner
        loadAvailableDonations()

        // Spinner selection listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val donations = spinner.tag as? List<DBHelper.Donation>
                selectedDonation = donations?.getOrNull(position)
                Log.d("SpinnerSelection", "Selected donation: $selectedDonation")
                updateUI(selectedDonation)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do not clear UI here; donor details will persist until a new selection
            }
        }

        // Submit request button
        btnSubmitRequest.setOnClickListener {
            val receiverName = etReceiverName.text.toString().trim()
            val receiverContact = etReceiverContact.text.toString().trim()

            if (receiverName.isBlank() || receiverContact.isBlank() || selectedDonation == null) {
                Toast.makeText(this, "Please fill all fields and select a donation!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isContactNumberValid(receiverContact)) {
                Toast.makeText(this, "Please enter a valid 10-digit contact number!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            selectedDonation?.let { donation ->
                Log.d("SubmitRequest", "Updating donation: ${donation.id}")
                dbHelper.updateDonationStatus(donation.id, "Completed")

                // Show donor info and make number clickable
                showDonorInfo(donation)
                Toast.makeText(this, "Request submitted successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadAvailableDonations() {
        val donations = dbHelper.getAvailableDonations()
        Log.d("LoadDonations", "Available donations: $donations")

        if (donations.isEmpty()) {
            val placeholder = listOf("No Donations Available")
            spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, placeholder).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spinner.tag = emptyList<DBHelper.Donation>()
        } else {
            val foodItems = donations.map { it.foodItem }
            spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, foodItems).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spinner.tag = donations
        }
    }

    private fun updateUI(donation: DBHelper.Donation?) {
        if (donation == null) return

        loadImage(donation.imageUris.getOrNull(0), imgFood1)
        loadImage(donation.imageUris.getOrNull(1), imgFood2)
        loadImage(donation.imageUris.getOrNull(2), imgFood3)

        tvExpiry.text = "Expiry: ${donation.expiry} day"
        tvWhenMade.text = "When Made: ${donation.whenMade}"
        tvQuantity.text = "Quantity: ${donation.quantity} ${donation.unit}" // Display quantity with unit
        tvAddress.text = "Address: ${donation.address}"
    }


    private fun showDonorInfo(donation: DBHelper.Donation) {
        tvDonorInfo.text = "Donor: ${donation.donorName}\nContact: ${donation.contact}"
        tvDonorInfo.visibility = View.VISIBLE

        // Make the donor's contact number clickable
        tvDonorInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${donation.contact}")
            }
            startActivity(intent)
        }
    }

    private fun isContactNumberValid(contact: String): Boolean {
        return contact.length == 10 && contact.all { it.isDigit() }
    }

    private fun loadImage(uri: String?, imageView: ImageView) {
        if (!uri.isNullOrBlank()) {
            Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground) // Placeholder image
                .into(imageView)
        } else {
            imageView.setImageResource(android.R.color.transparent)
        }
    }
}
