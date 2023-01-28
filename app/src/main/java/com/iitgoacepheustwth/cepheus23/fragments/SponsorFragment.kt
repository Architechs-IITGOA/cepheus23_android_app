package com.iitgoacepheustwth.cepheus23.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitgoacepheustwth.cepheus23.adapter.SponsorAdapter
import com.iitgoacepheustwth.cepheus23.databinding.FragmentSponsorBinding
import com.iitgoacepheustwth.cepheus23.model.SponsorData
import com.google.firebase.database.*


class SponsorFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var listSponsors : ArrayList<SponsorData>
    private lateinit var databaseReference :DatabaseReference

    lateinit var eventRecycler: RecyclerView
    lateinit var eventsRecyclerLayoutManager: RecyclerView.LayoutManager
    lateinit var eventsRecyclerAdapter: SponsorAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sponsorBinding = FragmentSponsorBinding.inflate(inflater, container, false)
        Log.i("sponsors", "sponsor activity initialised")


        eventRecycler=sponsorBinding.recycler
        eventsRecyclerLayoutManager = LinearLayoutManager(activity)
        eventRecycler.layoutManager = eventsRecyclerLayoutManager
        Log.i("sponsors", "firebase call initialised")
        databaseReference = FirebaseDatabase.getInstance().getReference("sponsor")

        Log.i("sponsors", "firebase call complete")
        databaseReference.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.i("sponsors", "firebase initialsed")
//                listSponsors.clear()
                if (snapshot.exists()){
                    for(datasnapshot in snapshot.children){
                        val list = datasnapshot.getValue(SponsorData::class.java)
                        listSponsors.add(list!!)
                    }

                    Log.i("sponsors", "found the adpter")
//                    recyclerView.adapter = SponsorAdapter(this@SponsorFragment, listSponsors)
                    Log.i("sponsors", "adapter initialised")


                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("sponsors", error.toString())

                Log.i("sponsors", "some error occurred")
            }
        })
//        eventsRecyclerAdapter = EventAdapter(setData.SetEvents())
//        eventRecycler.adapter = eventsRecyclerAdapter

        return sponsorBinding.root
    }


}