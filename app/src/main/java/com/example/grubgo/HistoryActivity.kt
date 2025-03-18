package com.example.grubgo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grubgo.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var dbHelper: DBHelper
    private var adapter: DonationHistoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper.getInstance(this)

        loadDonationHistory()

        binding.btnClearHistory.setOnClickListener {
            clearDonationHistory()
        }
    }

    private fun loadDonationHistory() {
        val donations = dbHelper.getAllDonations()
        if (donations.isEmpty()) {
            Toast.makeText(this, "No donations found!", Toast.LENGTH_SHORT).show()
            binding.rvOrderHistory.adapter = null
        } else {
            adapter = DonationHistoryAdapter(donations.toMutableList())
            binding.rvOrderHistory.layoutManager = LinearLayoutManager(this)
            binding.rvOrderHistory.adapter = adapter
        }
    }

    private fun clearDonationHistory() {
        dbHelper.clearAllDonations()
        Toast.makeText(this, "History cleared successfully!", Toast.LENGTH_SHORT).show()

        adapter?.clearDonations()
        binding.rvOrderHistory.adapter = null
    }
}
