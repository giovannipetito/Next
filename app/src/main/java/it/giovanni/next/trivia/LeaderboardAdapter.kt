package it.giovanni.next.trivia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import it.giovanni.next.R

class LeaderboardAdapter(private val myDataset: Array<String>) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    private val listOfAvatars = listOf(R.drawable.avatar_1_raster, R.drawable.avatar_2_raster, R.drawable.avatar_3_raster)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_view_item, parent, false)
        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]
        holder.item.findViewById<ImageView>(R.id.user_avatar_image).setImageResource(listOfAvatars[position])

        holder.item.setOnClickListener {
            val bundle = bundleOf("userName" to myDataset[position])

            Navigation.findNavController(holder.item).navigate(
                R.id.action_leaderboard_to_userProfile,
                bundle)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)
}