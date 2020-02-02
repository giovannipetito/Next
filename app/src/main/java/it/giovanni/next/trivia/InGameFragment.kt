package it.giovanni.next.trivia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import it.giovanni.next.R

class InGameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_in_game, container, false)

        val gameOverListener: (View) -> Unit = {
            Navigation.findNavController(view).navigate(R.id.action_in_game_to_gameOver)
        }

        view.findViewById<View>(R.id.checkBox).setOnClickListener(gameOverListener)
        view.findViewById<View>(R.id.checkBox2).setOnClickListener(gameOverListener)
        view.findViewById<View>(R.id.checkBox4).setOnClickListener(gameOverListener)

        view.findViewById<View>(R.id.checkBox3).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_in_game_to_resultsWinner)
        }

        return view
    }
}