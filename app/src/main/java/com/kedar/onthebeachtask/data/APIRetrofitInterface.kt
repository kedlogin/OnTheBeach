package com.kedar.onthebeachtask.data

import com.kedar.onthebeachtask.model.FlightList
import com.kedar.onthebeachtask.model.Hotel
import retrofit2.Call
import retrofit2.http.GET

interface APIRetrofitInterface {
    @GET("/raw/bFnZQEx0")
    fun getFlights():Call<FlightList>

    @GET("/raw/f0Tm6bfy")
    fun getHotel():Call<Hotel>
}