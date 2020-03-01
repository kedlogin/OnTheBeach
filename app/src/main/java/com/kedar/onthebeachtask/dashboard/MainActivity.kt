package com.kedar.onthebeachtask.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kedar.onthebeachtask.MyApplication
import com.kedar.onthebeachtask.R
import com.kedar.onthebeachtask.data.APIClientCallback
import com.kedar.onthebeachtask.data.APIClientInterface
import com.kedar.onthebeachtask.model.FlightList
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
    }

    private fun initViews(){
        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    class MyPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm){
        override fun getItem(position: Int): Fragment {
            return if (position ==0) FlightsFragment()
            else HotelsFragment()
        }

        override fun getCount() = 2

        override fun getPageTitle(position: Int) = if(position == 0) "Flights" else "Hotels"
    }
}
