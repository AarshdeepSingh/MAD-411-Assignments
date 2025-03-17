package com.example.android_asmt_7

import android.annotation.SuppressLint
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

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Initialize UI elements
        val expenseNameInput = findViewById<EditText>(R.id.edit_name)
        val expenseAmountInput = findViewById<EditText>(R.id.edit_amount)
        val addExpenseButton = findViewById<Button>(R.id.add_expense)
        val expensesRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        // Set up RecyclerView with adapter
        val expenseList = mutableListOf<Expense>()
        val expenseAdapter = ExpenseAdapter(expenseList)
        expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        expensesRecyclerView.adapter = expenseAdapter

        // Add expense on button click
        addExpenseButton.setOnClickListener {
            val expenseName = expenseNameInput.text.toString().trim()
            val expenseAmount = expenseAmountInput.text.toString().trim()

            if (expenseName.isNotEmpty() && expenseAmount.isNotEmpty()) {
                expenseList.add(Expense(expenseName, expenseAmount))
                expenseAdapter.notifyDataSetChanged()
                expenseNameInput.text.clear()
                expenseAmountInput.text.clear()
            }
        }
    }
}

// Data model for expense
data class Expense(val name: String, val amount: String)

class ExpenseAdapter(private val expenseList: MutableList<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    // ViewHolder for each expense item
    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expenseNameTextView: TextView = view.findViewById(R.id.expense_name)
        val expenseAmountTextView: TextView = view.findViewById(R.id.expense_amount)
        val deleteExpenseButton: Button = view.findViewById(R.id.delete_button)
    }

    // Inflate item layout for RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(itemView)
    }

    // Bind expense data to the views
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.expenseNameTextView.text = expense.name
        holder.expenseAmountTextView.text = "$${expense.amount}"
        holder.deleteExpenseButton.setOnClickListener {
            expenseList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = expenseList.size
}
