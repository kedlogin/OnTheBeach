package com.kedar.onthebeachtask.dashboard

import com.kedar.onthebeachtask.model.Flight
import com.kedar.onthebeachtask.util.BaseView

interface FlightsView:BaseView{
    fun showFlights(flights:ArrayList<Flight>)
    fun showFilterDialog(airlines:List<String>,airlineFilter:String?,departure:List<String>,departureFilter:String?,arrival:List<String>,arrivalFilter:String?)
}