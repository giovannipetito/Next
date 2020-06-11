package it.giovanni.next

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        view_transactions_button.setOnClickListener(this)
        send_money_button.setOnClickListener(this)
        trivia_button.setOnClickListener(this)
        rubrica_button.setOnClickListener(this)
        egg_timer_button.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.view_transactions_button -> navController.navigate(R.id.action_mainFragment_to_viewTransactionFragment)
            R.id.send_money_button -> navController.navigate(R.id.action_mainFragment_to_chooseRecipientFragment)
            R.id.trivia_button -> navController.navigate(R.id.action_mainFragment_to_titleScreenFragment)
            R.id.rubrica_button -> navController.navigate(R.id.action_mainFragment_to_showListFragment)
            R.id.egg_timer_button -> navController.navigate(R.id.action_mainFragment_to_eggTimerFragment)
        }
    }
}