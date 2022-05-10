package com.kln.android.androidfirebase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kln.android.androidfirebase.adapter.ClientListAdapter
import com.kln.android.androidfirebase.databinding.FragmentClientListBinding
import com.kln.android.androidfirebase.model.Client

class ClientListFragment : Fragment() {

    private var _binding: FragmentClientListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClientListBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        // getting the recyclerview by its id
        val recyclerview = binding.recyclerview

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(activity)

        // ArrayList of class Clients
        val data = ArrayList<Client>()

        db.collection("clients")
            .get()
            .addOnSuccessListener { clients ->
                for (client in clients) {
                    data.add(
                        Client(
                            client.getString("first").toString(),
                            client.getString("last").toString(),
                            client.getLong("age").toString().toInt(),
                            client.getDouble("lat").toString().toFloat(),
                            client.getDouble("lng").toString().toFloat()
                        )
                    )
                }
                // This will pass the ArrayList to our Adapter
                val adapter = ClientListAdapter(data, requireActivity().parent)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting clients.", exception)
            }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_ClientListFragment_to_FirestoreFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}