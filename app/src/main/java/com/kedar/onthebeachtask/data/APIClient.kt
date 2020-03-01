package com.kedar.onthebeachtask.data

import com.kedar.onthebeachtask.model.FlightList
import com.kedar.onthebeachtask.model.Hotel
import com.kedar.onthebeachtask.util.parseDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIClient @Inject constructor(private val apiRetrofitInterface: APIRetrofitInterface):APIClientInterface {

    override fun getFlights(callback: APIClientCallback<FlightList>) {
        apiRetrofitInterface.getFlights().enqueue(object :Callback<FlightList>{
            override fun onFailure(call: Call<FlightList>?, t: Throwable?) {
                callback.onFailure()
            }

            override fun onResponse(call: Call<FlightList>?, response: Response<FlightList>?) {
                if (response == null || !response.isSuccessful){
                    callback.onFailure()
                } else {
                    val flightList = response.body()!!
                    flightList.flights.forEach {
                        it.mDepartureDate = parseDate(it.departure_date)
                        it.mArrivalDate = parseDate(it.arrival_date)
                    }
                    callback.onSuccess(flightList)
                }
            }
        })
    }

    override fun getHotel(callback: APIClientCallback<Hotel>) {
        apiRetrofitInterface.getHotel().enqueue(object :Callback<Hotel>{
            override fun onFailure(call: Call<Hotel>?, t: Throwable?) {
                callback.onFailure()
            }

            override fun onResponse(call: Call<Hotel>?, response: Response<Hotel>?) {
                if (response == null || !response.isSuccessful){
                    callback.onFailure()
                }else{
                    callback.onSuccess(response.body()!!)
                }
            }
        })
    }
}