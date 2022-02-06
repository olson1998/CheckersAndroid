package com.game.checkers.board



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.game.checkers.R
import com.game.checkers.databinding.BoardBinding


class BoardView: Fragment()  {

    lateinit var bv: View //board view
    private var _binding: BoardBinding? = null //initializing binding for board.xml
    private val binding get() = _binding!!
    private val board: Board = Board()

    private val moveable = intArrayOf(
        1, 3, 5, 7,
        8, 10, 12, 14,
        17, 19, 21, 23,
        24, 26, 28, 30,
        33, 35, 37, 39,
        40, 42, 44, 46,
        49, 51, 53, 55,
        56, 58, 60, 62
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BoardBinding.inflate(inflater, container, false) // creating binding for board.xml
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for(f in moveable){
            var found: Boolean = false;
            for(i in 0..11 ){
                if(f == board.team1[i].location){
                    getFieldButton(f)?.setBackgroundResource(R.drawable.rp)
                    found = true
                }
                if(f == board.team2[i].location){
                    getFieldButton(f)?.setBackgroundResource(R.drawable.wp)
                    found = true
                }
            }
            if(!found){
                getFieldButton(f)?.setBackgroundResource(R.drawable.bf)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //service functions

    private fun getFieldButton(i: Int): ImageButton? {
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