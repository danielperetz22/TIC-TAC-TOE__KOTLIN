package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Game state variables
    private var currentPlayer = "X"
    private var gameActive = true

    // List to hold game cells for easier iteration
    private lateinit var cells: List<TextView>

    // Other UI components
    private lateinit var resultMessage: TextView
    private lateinit var restartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize all views
        initializeViews()

        // Set click listeners for game cells
        setupCellListeners()

        // Setup restart button listener
        restartButton.setOnClickListener {
            resetGame()
        }
    }

    private fun initializeViews() {
        // Find all cell TextViews
        cells = listOf(
            findViewById(R.id.one),
            findViewById(R.id.two),
            findViewById(R.id.three),
            findViewById(R.id.four),
            findViewById(R.id.five),
            findViewById(R.id.six),
            findViewById(R.id.seven),
            findViewById(R.id.eight),
            findViewById(R.id.nine)
        )

        // Find other UI components
        resultMessage = findViewById(R.id.game_result_message)
        restartButton = findViewById(R.id.btn_restartGame)
    }

    private fun setupCellListeners() {
        // Set click listener for each cell
        for (cell in cells) {
            cell.setOnClickListener { handleCellClick(cell) }
        }
    }

    private fun handleCellClick(cell: TextView) {
        // Check if cell is empty and game is active
        if (cell.text.isEmpty() && gameActive) {
            cell.text = currentPlayer

            if (currentPlayer == "X") {
                cell.setTextColor(getColor(R.color.colorX))
            } else {
                cell.setTextColor(getColor(R.color.colorO))
            }

            // Check for win condition
            if (checkWin()) {
                showGameResult("$currentPlayer Wins!")
                gameActive = false
            } else if (isBoardFull()) {
                showGameResult("It's a Draw!")
                gameActive = false
            } else {
                // Switch players
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    private fun checkWin(): Boolean {
        // Winning combinations
        val winCombos = listOf(
            listOf(0, 1, 2), // Row 1
            listOf(3, 4, 5), // Row 2
            listOf(6, 7, 8), // Row 3
            listOf(0, 3, 6), // Column 1
            listOf(1, 4, 7), // Column 2
            listOf(2, 5, 8), // Column 3
            listOf(0, 4, 8), // Diagonal 1
            listOf(2, 4, 6)  // Diagonal 2
        )

        // Check if any winning combination is met
        for (combo in winCombos) {
            if (cells[combo[0]].text == currentPlayer &&
                cells[combo[1]].text == currentPlayer &&
                cells[combo[2]].text == currentPlayer
            ) {
                return true
            }
        }
        return false
    }

    private fun isBoardFull(): Boolean {
        // Check if all cells are filled
        return cells.all { it.text.isNotEmpty() }
    }

    private fun showGameResult(result: String) {
        resultMessage.text = result
        resultMessage.visibility = View.VISIBLE
        restartButton.visibility = View.VISIBLE

    }

    private fun resetGame() {
        // Clear all cells
        for (cell in cells) {
            cell.text = ""
        }

        // Reset game state
        currentPlayer = "X"
        gameActive = true

        // Hide result message and restart button
        resultMessage.visibility = View.GONE
        restartButton.visibility = View.GONE
    }
}
