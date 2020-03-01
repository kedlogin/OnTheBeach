package com.kedar.onthebeachtask.model

import java.util.*

class Flight {
    lateinit var airline: String
    lateinit var departure_date: String
    var mDepartureDate: Date? = null
    lateinit var arrival_date: String
    var mArrivalDate: Date? = null
    var price: Int = 0
    lateinit var departure_airport: String
    lateinit var arrival_airport: String
}