package com.kedar.onthebeachtask.dashboard

import com.kedar.onthebeachtask.model.Hotel
import com.kedar.onthebeachtask.util.BaseView

interface HotelView : BaseView {
    fun showHotel(hotel:Hotel?)
}