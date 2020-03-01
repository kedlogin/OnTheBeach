package com.kedar.onthebeachtask.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kedar.onthebeachtask.R
import com.kedar.onthebeachtask.model.Flight
import kotlinx.android.synthetic.main.row_flight.view.*
import java.text.SimpleDateFormat


class FlightsRecyclerAdapter(context:Context) :RecyclerView.Adapter<FlightsRecyclerAdapter.FlightsViewHolder>(){
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val simpleDateFormat = SimpleDateFormat("HH:mm")
    val simpleDateFormat1 = SimpleDateFormat("yyyy-MM-dd")
    var flights = ArrayList<Flight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsViewHolder {
        return FlightsViewHolder(layoutInflater.inflate(R.layout.row_flight,parent,false))
    }

    override fun getItemCount(): Int = flights.size

    override fun onBindViewHolder(holder: FlightsViewHolder, position: Int) {
        val flight = flights[position]
        with(holder.itemView) {
            tvRowFlightAirline.text = flight.airline
            tvRowStartTime.text = simpleDateFormat.format(flight.mDepartureDate)
            tvRowEndTime.text = simpleDateFormat.format(flight.mArrivalDate)
            tvRowDepaartureAirport.text = flight.departure_airport
            tvRowArrivalAirport.text = flight.arrival_airport
            tvRowDepartureDate.text = simpleDateFormat1.format(flight.mDepartureDate)
            tvRowArrivalDate.text = simpleDateFormat1.format(flight.mArrivalDate)
            tvRowFlightPrice.text = "£${flight.price}"

            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60

            val diff = flight.mArrivalDate!!.time - flight.mDepartureDate!!.time
            val hoursDiff = diff/hoursInMilli
            val minDiff = (diff/minutesInMilli)%60

            tvRowDuration.text = "$hoursDiff hr $minDiff mins"
        }

    }

    class FlightsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}