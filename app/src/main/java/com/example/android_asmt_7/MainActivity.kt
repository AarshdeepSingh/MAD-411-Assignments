// Aarshdeep Singh
// 0838091

package com.example.android_asmt_7

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: is used")

        // Initialize UI elements
        val expenseNameInput = findViewById<EditText>(R.id.edit_name)
        val expenseAmountInput = findViewById<EditText>(R.id.edit_amount)
        val addExpenseButton = findViewById<Button>(R.id.add_expense)
        val expensesRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val show_Button = findViewById<Button>(R.id.show_Button)

        // Set up RecyclerView with adapter
        val expenseList = mutableListOf<Expense>()
        val expenseAdapter = ExpenseAdapter(expenseList)
        expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        expensesRecyclerView.adapter = expenseAdapter

        // Add expense on button click
        addExpenseButton.setOnClickListener {
            val expenseName = expenseNameInput.text.toString().trim()
            val expenseAmount = expenseAmountInput.text.toString().trim()
            val expenseDate = findViewById<EditText>(R.id.edit_date).text.toString().trim()

            if (expenseName.isNotEmpty() && expenseAmount.isNotEmpty()) {
                expenseList.add(Expense(expenseName, expenseAmount, expenseDate))
                expenseAdapter.notifyDataSetChanged()
                expenseNameInput.text.clear()
                expenseAmountInput.text.clear()
                findViewById<EditText>(R.id.edit_date).text.clear()
            }
        }
        show_Button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.canada.ca/en/financial-consumer-agency/services/covid-19-managing-financial-health.html"))
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: is used")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: is used")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: is used")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: is used")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: is used")
    }
}



// Data model for expense
data class Expense(val name: String, val amount: String, val date: String)

class ExpenseAdapter(private val expenseList: MutableList<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    // ViewHolder for each expense item
    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expenseNameTextView: TextView = view.findViewById(R.id.expense_name)
        val expenseAmountTextView: TextView = view.findViewById(R.id.expense_amount)
        val expenseDateTextView: TextView = view.findViewById(R.id.expense_date)
        val deleteExpenseButton: Button = view.findViewById(R.id.delete_button)
        val showDetailsButton: Button = view.findViewById(R.id.show_details_button)
    }

    // Inflate item layout for RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(itemView)
    }

    // Bind expense data to the views
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.expenseNameTextView.text = expense.name
        holder.expenseAmountTextView.text = "$${expense.amount}"
        holder.expenseDateTextView.text = expense.date

        holder.showDetailsButton.setOnClickListener {
            Log.d("ExpenseAdapter", "Show Details clicked for: ${expense.name}")
            val context = holder.itemView.context
            val intent = Intent(context, ExpenseDetailsActivity::class.java)

            // Pass data to ExpenseDetailsActivity
            intent.putExtra("expense_name", expense.name)
            intent.putExtra("expense_amount", expense.amount)
            intent.putExtra("expense_date", expense.date)

            context.startActivity(intent)
        }

        holder.deleteExpenseButton.setOnClickListener {
            expenseList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = expenseList.size

}
