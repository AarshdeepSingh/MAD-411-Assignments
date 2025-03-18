package com.example.android_asmt_7

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailsActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        val expenseName = intent.getStringExtra("EXTRA_NAME")
        val expenseAmount = intent.getStringExtra("EXTRA_AMOUNT")
        val expenseDate = intent.getStringExtra("EXTRA_DATE")

        val nameTextView = findViewById<TextView>(R.id.expense_name_detail)
        val amountTextView = findViewById<TextView>(R.id.expense_amount_detail)
        val dateTextView = findViewById<TextView>(R.id.expense_date_detail)

        nameTextView.text = "Name: $expenseName"
        amountTextView.text = "Amount: $$expenseAmount"
        dateTextView.text = "Date: $expenseDate"
    }
}
