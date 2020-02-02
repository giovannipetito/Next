package it.giovanni.next.trivia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import it.giovanni.next.R

class GameOverFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)

        view.findViewById<View>(R.id.play_btn4).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_game_over_to_match)
        }
        return view
    }
}