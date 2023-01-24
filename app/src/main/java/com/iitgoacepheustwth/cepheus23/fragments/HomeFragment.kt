package com.iitgoacepheustwth.cepheus23.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.iitgoacepheustwth.cepheus23.R
import com.iitgoacepheustwth.cepheus23.databinding.FragmentHomeBinding
import com.iitgoacepheustwth.cepheus23.ImageAdapter
import java.lang.Math.abs


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler

    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: ImageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        init()

        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })

//        binding.comcard.setOnClickListener {
////            val intent = Intent(this,CompetitionsInEventFragment::class.java)
//        }

        binding.card1.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_eventsFragment)
        }
        binding.card2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_eventsFragment)
        }
        binding.card3.setOnClickListener {
            Toast.makeText(context, "Stay tuned! Rulebook comming soon.", Toast.LENGTH_LONG).show()
        }

        binding.instahome.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://instagram.com/cepheus_iitgoa?igshid=YmMyMTA2M2Y=")
            startActivity(intent)
        }

        binding.linkedinhome.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.linkedin.com/company/cepheus-iit-goa/")
            startActivity(intent)
        }

        binding.ythome.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://youtube.com/@cepheusiitgoa")
            startActivity(intent)
        }

        binding.webhome.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://iitgoa.ac.in/cepheus/")
            startActivity(intent)
        }



        return binding.root
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }
    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.75f + r*0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init(){
        viewPager2 = binding.viewpager2
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.scrollimage1)
        imageList.add(R.drawable.scrollimage2)
        imageList.add(R.drawable.scrollimage3)
        imageList.add(R.drawable.scrollimage4)

        adapter = ImageAdapter(imageList,viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER


    }
}