package com.kln.android.androidfirebase.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kln.android.androidfirebase.R
import com.kln.android.androidfirebase.model.Client

class ClientListAdapter(private val mList: List<Client>, private val activity: Activity?) : RecyclerView.Adapter<ClientListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.client_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val client = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.name.text = client.first + " " + client.last
        holder.age.text = client.age.toString()
        holder.lat.text = "Lat: "+ client.lat
        holder.long.text = "Lgt: "+ client.lng

        //deleting a client
        holder.closeBtn.setOnClickListener {

            val db: FirebaseFirestore = FirebaseFirestore.getInstance()

            db.collection("clients").document("${client.first} ${client.last}")
                .delete()
                .addOnSuccessListener { Log.d(TAG, "Client successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting Client", e) }

            holder.card.removeAllViews()
        }
//
//        holder.card.setOnClickListener {
//            val bundle = bundleOf(
//                "name" to itemsViewModel.name,
//                "lat" to itemsViewModel.lat,
//                "lng" to itemsViewModel.lng
//            )
//            it.findNavController().navigate(R.id.action_FirstFragment_to_mapsFragment, bundle)
//        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val name: TextView = itemView.findViewById(R.id.nameTextView)
        val age: TextView = itemView.findViewById(R.id.ageTextView)
        val lat: TextView = itemView.findViewById(R.id.latTextView)
        val long: TextView = itemView.findViewById(R.id.lngTextView)
        val card: CardView = itemView.findViewById(R.id.clientCard)
        val closeBtn: ImageButton = itemView.findViewById(R.id.closeButton)
    }
}