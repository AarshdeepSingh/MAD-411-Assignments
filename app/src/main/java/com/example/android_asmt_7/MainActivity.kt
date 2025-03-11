package com.example.android_asmt_7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var expenseNameInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var expenseList: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var statusMessage: TextView // TextView for showing status messages
    private val expenses = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        expenseNameInput = findViewById(R.id.edit_name)
        amountInput = findViewById(R.id.edit_amount)
        addExpenseButton = findViewById(R.id.add_expense)
        expenseList = findViewById(R.id.recycler_view)
        statusMessage = findViewById(R.id.status_message) // TextView for status messages

        // Set up RecyclerView with adapter
        expenseAdapter = ExpenseAdapter(expenses)
        expenseList.layoutManager = LinearLayoutManager(this)
        expenseList.adapter = expenseAdapter

        // Set click listener for "Add Expense" button
        addExpenseButton.setOnClickListener {
            val name = expenseNameInput.text.toString().trim()
            val amountText = amountInput.text.toString().trim()

            // Validate inputs
            if (name.isNotEmpty() && amountText.isNotEmpty()) {
                val amount = amountText.toDoubleOrNull()
                if (amount != null) {
                    // Create a new expense and add it to the list
                    val date = getCurrentDate()
                    val expense = Expense(name, amount, date)
                    expenses.add(expense)
                    expenseAdapter.notifyItemInserted(expenses.size - 1)

                    // Clear input fields
                    expenseNameInput.text?.clear()
                    amountInput.text?.clear()

                    // Show success message in TextView
                    statusMessage.text = "Expense added successfully!"
                } else {
                    // Show error message in TextView
                    statusMessage.text = "Please enter a valid amount."
                }
            } else {
                // Show error message in TextView
                statusMessage.text = "Please fill in all fields."
            }
        }
    }

    // Function to get the current date as a string
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    // Expense data class
    data class Expense(
        val name: String,
        val amount: Double,
        val date: String
    )

    // RecyclerView Adapter for displaying the expense list
    class ExpenseAdapter(private val expenses: MutableList<Expense>) :
        RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

        // ViewHolder class to hold references to the views for each data item
        inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val expenseNameTextView: TextView = itemView.findViewById(R.id.expense_name)
            val amountTextView: TextView = itemView.findViewById(R.id.expense_amount)
            val deleteButton: Button = itemView.findViewById(R.id.delete_button)
        }

        // Inflates the item layout and creates the ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.expense_item, parent, false)
            return ExpenseViewHolder(itemView)
        }

        // Binds the data to the views
        override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
            val currentExpense = expenses[position]
            holder.expenseNameTextView.text = currentExpense.name
            holder.amountTextView.text = currentExpense.amount.toString()

            holder.deleteButton.setOnClickListener {
                expenses.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        // Returns the total number of items in the data set
        override fun getItemCount() = expenses.size
    }
}
