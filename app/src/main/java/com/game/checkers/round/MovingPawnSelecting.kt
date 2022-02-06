package com.game.checkers.round

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.game.checkers.R
import com.game.checkers.board.Board
import com.game.checkers.board.BoardRepo
import com.game.checkers.databinding.BoardBinding


class MovingPawnSelecting(b: Board, p: Int) : Fragment(){

    private val board: Board = b
    private val player: Int = p
    private val sf: Int = 0 //selected field
    private val br: BoardRepo = BoardRepo(b)
    private var _binding: BoardBinding? = null //initializing binding for board.xml
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            BoardBinding.inflate(inflater, container, false) // creating binding for board.xml
        val view = binding.root
        buildBoard()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cm: MutableList<Int> =
            br.thosePawnsCanMove(player)  // creating list of player's pawn which are able to move
        for (f in cm) {
            getFieldButton(f)?.setOnClickListener{view->
                if(player == 1){getFieldButton(f)?.setBackgroundResource(R.drawable.wpselected)}
                if(player == 2){getFieldButton(f)?.setBackgroundResource(R.drawable.rpselected)}
            }
        }
    }

    fun buildBoard(){
        for (f in br.movable) { // get every movable field
            var found: Boolean = false; //if there is a pawn on this field it will change to true
            for (i in 0..11) { //and check for every 12 pawn's his location on board
                if (f == board.team1[i].location || f == board.team2[i].location) {
                    getFieldButton(f)?.setBackgroundResource(R.drawable.wp)//set white pawn if player 1
                    found = true
                }
                if (f == board.team2[i].location) {
                    getFieldButton(f)?.setBackgroundResource(R.drawable.rp) //set white pawn for player 1
                    found = true
                }
                if (!found) {
                    getFieldButton(f)?.setBackgroundResource(R.drawable.bf)//if there are not pawns found set for this field empty field graphic
                }
            }
        }
    }

    //contains button of every field
    fun getFieldButton(i: Int): ImageButton? {
        var ib: ImageButton? = null
        if(i == 1){ ib = binding.f1}
        if(i == 3){ ib = binding.f3}
        if(i == 5){ ib = binding.f5}
        if(i == 7){ ib = binding.f7}
        if(i == 8){ib = binding.f8}
        if(i == 10){ ib = binding.f10}
        if(i == 12){ ib = binding.f12}
        if(i == 14){ ib = binding.f14}
        if(i == 17){ ib = binding.f17}
        if(i == 19){ ib = binding.f19}
        if(i == 21){ ib = binding.f21}
        if(i == 23){ ib = binding.f23}
        if(i == 24){ib = binding.f24}
        if(i == 26){ ib = binding.f26}
        if(i == 28){ ib = binding.f28}
        if(i == 30){ ib = binding.f30}
        if(i == 33){ ib = binding.f33}
        if(i == 35){ ib = binding.f35}
        if(i == 37){ ib = binding.f37}
        if(i == 39){ ib = binding.f39}
        if(i == 40){ ib = binding.f40}
        if(i == 42){ ib = binding.f42}
        if(i == 44){ ib = binding.f44}
        if(i == 46){ ib = binding.f46}
        if(i == 49){ ib = binding.f49}
        if(i == 51){ ib = binding.f51}
        if(i == 53){ ib =binding.f53}
        if(i == 55){ ib =binding.f55}
        if(i == 51){ ib = binding.f51}
        if(i == 53){ ib =binding.f53}
        if(i == 55){ ib = binding.f55}
        if(i == 56){ ib = binding.f56}
        if(i == 58){ ib = binding.f58}
        if(i == 60){ ib = binding.f60}
        if(i == 62){ ib = binding.f62}
        Log.d("returned: ", ib.toString())
        return ib
    }


}



