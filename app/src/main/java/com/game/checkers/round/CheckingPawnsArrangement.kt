package com.game.checkers.round

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.game.checkers.R
import com.game.checkers.board.Board
import com.game.checkers.board.BoardRepo
import com.game.checkers.databinding.BoardBinding

class CheckingPawnsArrangement(b: Board, p: Int) : Fragment() {
    private val br: BoardRepo = BoardRepo(b)
    private val player = p
    private var _binding: BoardBinding? = null //initializing binding for board.xml
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BoardBinding.inflate(inflater, container, false) // creating binding for board.xml
        val view = binding.root
        return view
    }

    //this method checks if there is win, if not it will check if any of
    //player's pawn are able to move
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var r: Char? = null
        if(br.isWin){
            Log.d("won: " , player.toString())
        }
        else if(br.isEnemyToCapture(player)){
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.board_view, PawnSelecting(br.board, player,'c'))
            fragmentTransaction.commit()
        }
        else{
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.board_view, PawnSelecting(br.board, player,'m'))
            fragmentTransaction.commit()
        }
    }

}