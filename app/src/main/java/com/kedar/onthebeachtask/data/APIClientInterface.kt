package com.kedar.onthebeachtask.data

import com.kedar.onthebeachtask.model.FlightList
import com.kedar.onthebeachtask.model.Hotel

interface APIClientInterface {
    fun getFlights(callback: APIClientCallback<FlightList>)

    fun getHotel(callback:APIClientCallback<Hotel>)
}

interface APIClientCallback<T>{
    fun onSuccess(t:T)
    fun onFailure()
}