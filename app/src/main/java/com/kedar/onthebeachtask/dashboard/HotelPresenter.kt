package com.kedar.onthebeachtask.dashboard

import com.kedar.onthebeachtask.util.BasePresenter

interface HotelPresenter : BasePresenter<HotelView> {
    fun loadHotel()
}