package com.game.checkers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.game.checkers.board.BoardView
import com.game.checkers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val bc: BoardView = BoardView()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.board_view, bc)
        fragmentTransaction.commit()
        setContentView(R.layout.board)
    }

}