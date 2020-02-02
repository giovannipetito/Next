package it.giovanni.next.trivia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import it.giovanni.next.R

class ResultsWinnerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_results_winner, container, false)

        view.findViewById<Button>(R.id.play_btn2).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_results_winner_to_match)
        }

        view.findViewById<Button>(R.id.leaderboard_btn2).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_results_winner_to_leaderboard)
        }
        return view
    }
}