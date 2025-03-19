package com.example.android_asmt_7

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ExpenseDetailsActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        val expenseName = intent.getStringExtra("expense_name")
        val expenseAmount = intent.getStringExtra("expense_amount")
        val expenseDate = intent.getStringExtra("expense_date")


        val nameTextView = findViewById<TextView>(R.id.expense_name_detail)
        val amountTextView = findViewById<TextView>(R.id.expense_amount_detail)
        val dateTextView = findViewById<TextView>(R.id.expense_date_detail)

        nameTextView.text = "Name: $expenseName"
        amountTextView.text = "Amount: $$expenseAmount"
        dateTextView.text = "Date: $expenseDate"
    }
}