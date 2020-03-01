package com.kedar.onthebeachtask.dashboard

import com.kedar.onthebeachtask.R
import com.kedar.onthebeachtask.data.APIClientCallback
import com.kedar.onthebeachtask.data.APIClientInterface
import com.kedar.onthebeachtask.model.Flight
import com.kedar.onthebeachtask.model.FlightList

class FlightsPresenter(override var view: FlightsView?, private val apiClient:APIClientInterface) : FlightsFragmentPresenter<FlightsView>{

    var flights:ArrayList<Flight>? = null
    var airlineFilter:String?= null
    var departureFilter:String?= null
    var arrivalFilter:String?= null

    override fun loadFlights() {
        view?.setLoadingIndicator(true)
        apiClient.getFlights(object:APIClientCallback<FlightList>{
            override fun onSuccess(t: FlightList) {
                flights = t.flights
                view?.apply {
                    setLoadingIndicator(false)
                    showFlights(t.flights)
                }
            }

            override fun onFailure() {
                view?.apply {
                    setLoadingIndicator(false)
                    showErrorMsg(R.string.something_went_wrong)
                }
            }
        })
    }

    override fun filterMenuClicked() {
        if (flights == null){
            view?.showErrorMsg(R.string.something_went_wrong)
        }else{
            val airlines = HashSet<String>()
            val departure = HashSet<String>()
            val arrival = HashSet<String>()
            flights?.forEach {
                airlines.add(it.airline)
                departure.add(it.departure_airport)
                arrival.add(it.arrival_airport)
            }
            val airlinesList = airlines.toMutableList()
            airlinesList.add(0,"Select Airlines")
            val departureList = departure.toMutableList()
            departureList.add(0,"Select Departure")
            val arrivalList = arrival.toMutableList()
            arrivalList.add(0,"Select Arrival")
            view?.showFilterDialog(airlinesList,airlineFilter,departureList,departureFilter,arrivalList,arrivalFilter)
        }
    }

    override fun applyFilter(airlineFilter: String?, departureFilter: String?, arrivalFilter: String?) {
        this.airlineFilter = airlineFilter
        this.departureFilter = departureFilter
        this.arrivalFilter = arrivalFilter
        applyFilter()
    }

    override fun resetFilter() {
        airlineFilter = null
        departureFilter = null
        arrivalFilter = null
        applyFilter()
    }

    private fun applyFilter(){
        if (airlineFilter == null && departureFilter == null && arrivalFilter== null){
            view?.showFlights(flights ?: arrayListOf())
        }else{
            val finalList = ArrayList<Flight>()
            flights?.forEach {
                if (it.airline == airlineFilter || it.departure_airport == departureFilter || it.arrival_airport == arrivalFilter){
                    finalList.add(it)
                }
            }
            view?.showFlights(finalList)
        }
    }

}