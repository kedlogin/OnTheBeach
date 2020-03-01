package com.kedar.onthebeachtask.dashboard

import com.kedar.onthebeachtask.R
import com.kedar.onthebeachtask.data.APIClientCallback
import com.kedar.onthebeachtask.data.APIClientInterface
import com.kedar.onthebeachtask.model.Hotel

class HotelFragmentPresenter(override var view: HotelView?, private val apiClient: APIClientInterface) :HotelPresenter {
    override fun loadHotel() {
        view?.setLoadingIndicator(true)
        apiClient.getHotel(object :APIClientCallback<Hotel>{
            override fun onSuccess(t: Hotel) {
                view?.apply {
                    setLoadingIndicator(false)
                    showHotel(t)
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
}