package it.giovanni.next.trivia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import it.giovanni.next.R

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        view.findViewById<Button>(R.id.signup_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_register_to_match)
        }
        return view
    }
}