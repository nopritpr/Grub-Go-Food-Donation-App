package com.example.grubgo

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DonateActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var imgPicture1: ImageView
    private lateinit var imgPicture2: ImageView
    private lateinit var imgPicture3: ImageView
    private val imageUris = mutableListOf<Uri?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_layout)

        dbHelper = DBHelper.getInstance(this)

        val etDonorName = findViewById<EditText>(R.id.etDonorName)
        val etDonorContact = findViewById<EditText>(R.id.etDonorContact)
        val etFoodItem = findViewById<EditText>(R.id.etFoodItem)
        val etQuantity = findViewById<EditText>(R.id.etQuantity)
        val spQuantityUnit = findViewById<Spinner>(R.id.spQuantityUnit)
        val etExpiry = findViewById<EditText>(R.id.etExpiringIn)
        val etWhenMade = findViewById<EditText>(R.id.etWhenMade)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val btnSubmitDonation = findViewById<Button>(R.id.btnSubmitDonation)
        imgPicture1 = findViewById(R.id.imgPicture1)
        imgPicture2 = findViewById(R.id.imgPicture2)
        imgPicture3 = findViewById(R.id.imgPicture3)

        val imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    try {
                        // Take persistable permission
                        contentResolver.takePersistableUriPermission(
                            it,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        addImage(it)
                        Log.d("DonateActivity", "Image URI persisted: $it")
                    } catch (e: SecurityException) {
                        Log.e("DonateActivity", "Failed to persist URI permission: ${e.message}")
                        Toast.makeText(this, "Unable to access the selected image.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        imgPicture1.setOnClickListener { imagePickerLauncher.launch("image/*") }
        imgPicture2.setOnClickListener { imagePickerLauncher.launch("image/*") }
        imgPicture3.setOnClickListener { imagePickerLauncher.launch("image/*") }

        etWhenMade.setOnClickListener {
            showDatePickerDialog { date -> etWhenMade.setText(date) }
        }

        btnSubmitDonation.setOnClickListener {
            val donorName = etDonorName.text.toString().trim()
            val contact = etDonorContact.text.toString().trim()
            val foodItem = etFoodItem.text.toString().trim()
            val quantity = etQuantity.text.toString().trim()
            val unit = spQuantityUnit.selectedItem.toString().trim()
            val expiry = etExpiry.text.toString().trim()
            val whenMade = etWhenMade.text.toString().trim()
            val address = etAddress.text.toString().trim()

            if (validateInputs(donorName, contact, foodItem, quantity, expiry, address)) {
                val imageUriStrings = imageUris.filterNotNull().map { it.toString() }

                if (imageUriStrings.isEmpty()) {
                    Toast.makeText(this, "Please upload at least one image!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val rowId = dbHelper.insertDonation(
                    donorName, contact, foodItem, quantity, unit,
                    expiry, whenMade, address, imageUriStrings
                )

                if (rowId != -1L) {
                    Toast.makeText(this, "Donation added successfully!", Toast.LENGTH_LONG).show()
                    resetFields(etDonorName, etDonorContact, etFoodItem, etQuantity, etExpiry, etWhenMade, etAddress)
                } else {
                    Toast.makeText(this, "Failed to add donation.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInputs(vararg fields: String): Boolean {
        return if (fields.any { it.isBlank() }) {
            Toast.makeText(this, "All fields are mandatory!", Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    private fun addImage(uri: Uri) {
        when {
            imageUris.size < 1 -> {
                imgPicture1.setImageURI(uri)
                imageUris.add(uri)
            }
            imageUris.size < 2 -> {
                imgPicture2.setImageURI(uri)
                imageUris.add(uri)
            }
            imageUris.size < 3 -> {
                imgPicture3.setImageURI(uri)
                imageUris.add(uri)
            }
            else -> Toast.makeText(this, "You can add up to 3 pictures only", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetFields(
        etDonorName: EditText, etDonorContact: EditText, etFoodItem: EditText,
        etQuantity: EditText, etExpiry: EditText, etWhenMade: EditText, etAddress: EditText
    ) {
        etDonorName.text.clear()
        etDonorContact.text.clear()
        etFoodItem.text.clear()
        etQuantity.text.clear()
        etExpiry.text.clear()
        etWhenMade.text.clear()
        etAddress.text.clear()

        imgPicture1.setImageResource(android.R.color.transparent)
        imgPicture2.setImageResource(android.R.color.transparent)
        imgPicture3.setImageResource(android.R.color.transparent)
        imageUris.clear()
    }

    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
