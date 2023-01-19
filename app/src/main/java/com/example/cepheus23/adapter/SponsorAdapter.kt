package com.example.cepheus23.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cepheus23.SponsorActivity
import com.example.cepheus23.databinding.SponsorItemBinding
import com.example.cepheus23.model.SponsorData

class SponsorAdapter(private val listSponsors: ArrayList<SponsorData>) : RecyclerView.Adapter<SponsorAdapter.Viewholder>(){
    class Viewholder(val binding : SponsorItemBinding) : RecyclerView.ViewHolder(binding.root){
        val sponsorDescription = binding.tvSponsorDescription
        val sponsorImage = binding.ivSponsorImage
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorAdapter.Viewholder {
        var binding = SponsorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val currentSponsor = listSponsors[position]
//        Glide.with(context).load(listSponsors[position].url).into(holder.sponsorImage)
        holder.sponsorDescription.text = currentSponsor.category.toString()
//        var value = currentSponsor.iswebsite
//        Log.i("sponsors", value.toString())


//        if(value == "No"){
//            val phoneNumber = currentSponsor.link
//            val callintent2 = Intent(Intent.ACTION_VIEW)
//            callintent2.data = Uri.parse("tel:$phoneNumber")
//            Log.i("spcheck","4")
//            startActivity(callintent2)
//        }
    }

    override fun getItemCount(): Int {
        return listSponsors.size
    }
}