package it.giovanni.next.trivia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import it.giovanni.next.R

class UserProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        val name = arguments?.getString("userName") ?: "Ali Connors"
        view.findViewById<TextView>(R.id.profile_user_name).text = name
        return view
    }
}