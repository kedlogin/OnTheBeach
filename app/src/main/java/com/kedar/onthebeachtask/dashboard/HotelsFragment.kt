package com.kedar.onthebeachtask.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.kedar.onthebeachtask.MyApplication
import com.kedar.onthebeachtask.R
import com.kedar.onthebeachtask.data.APIClientInterface
import com.kedar.onthebeachtask.model.Hotel
import kotlinx.android.synthetic.main.fragment_hotels.*
import kotlinx.android.synthetic.main.row_hotel_facility.view.*
import kotlinx.android.synthetic.main.row_hotel_image_slider.view.*
import javax.inject.Inject

class HotelsFragment :Fragment(), HotelView{

    @Inject
    lateinit var apiClient: APIClientInterface

    lateinit var presenter: HotelFragmentPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as MyApplication).appComponent.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hotels,container,false)
        presenter = HotelFragmentPresenter(this,apiClient)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.loadHotel()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showHotel(hotel: Hotel?) {
        if (hotel == null){
            tvDefault.visibility = View.VISIBLE
        }else{
           setHotelInfo(hotel)
        }
    }

    private fun setHotelInfo(hotel:Hotel){
        tvHotelName.visibility = View.VISIBLE
        tvHotelAddress.visibility = View.VISIBLE
        imageView.visibility = View.VISIBLE
        viewPager2.visibility = View.VISIBLE
        tabIndicator.visibility = View.VISIBLE
        ratingBar.visibility = View.VISIBLE
        tvHotelDesc.visibility = View.VISIBLE
        textView8.visibility = View.VISIBLE
        llFacilities.visibility = View.VISIBLE

        with(hotel) {
            tvHotelName.text = name
            tvHotelAddress.text = hotel_location
            ratingBar.rating = rating.toFloat()
            tvHotelDesc.text = description
            for (facility in facilities){
                val row = layoutInflater.inflate(R.layout.row_hotel_facility,llFacilities,false)
                row.rowTvFacility.text = facility
                llFacilities.addView(row)
            }
            viewPager2.adapter = ImageSliderAdapter(images)
            tabIndicator.setupWithViewPager(viewPager2,true)
        }
    }

    inner class ImageSliderAdapter(val images:ArrayList<String>):PagerAdapter(){

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val row = layoutInflater.inflate(R.layout.row_hotel_image_slider,container,false)
            Glide.with(activity!!).load(images[position]).into(row.imgHotelSlider)
            container.addView(row)
            return row
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return  view == `object`
        }

        override fun getCount() = images.size

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as ImageView)
        }
    }

    override fun showErrorMsg(str: Int) {
        Toast.makeText(activity,str,Toast.LENGTH_SHORT).show()
    }

    override fun setLoadingIndicator(isLoading: Boolean) {
        if (isLoading){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }
}