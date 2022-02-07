package com.game.checkers.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.game.checkers.R
import com.game.checkers.board.Board
import com.game.checkers.round.CheckingPawnsArrangement

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val board: Board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.board)
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.board_view,
            CheckingPawnsArrangement(board, 1)
        )
        fragmentTransaction.commit()
    }

}