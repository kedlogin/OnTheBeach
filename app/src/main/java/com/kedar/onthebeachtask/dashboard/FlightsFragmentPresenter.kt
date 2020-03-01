package com.kedar.onthebeachtask.dashboard

import com.kedar.onthebeachtask.util.BasePresenter

interface FlightsFragmentPresenter<T>:BasePresenter<T>{
    fun loadFlights()

    fun filterMenuClicked()

    fun applyFilter(airlineFilter:String?,departureFilter:String?,arrivalFilter:String?)

    fun resetFilter()
}