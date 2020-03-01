package com.kedar.onthebeachtask.dashboard

import com.example.android.architecture.blueprints.todoapp.capture
import com.kedar.onthebeachtask.R
import com.kedar.onthebeachtask.data.APIClientCallback
import com.kedar.onthebeachtask.data.APIClientInterface
import com.kedar.onthebeachtask.model.Hotel
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.*
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.verify

class HotelFragmentPresenterTest {

    @Mock
    private lateinit var view: HotelView

    @Mock
    private lateinit var apiClient: APIClientInterface

    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<APIClientCallback<Hotel>>

    private lateinit var presenter: HotelFragmentPresenter

    private lateinit var hotel:Hotel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = HotelFragmentPresenter(view,apiClient)
        hotel = Hotel().apply {
            name = "Test Hotel Name"
            hotel_location = "Test location"
            description = "Test description"
            images = arrayListOf("https://test1.png","https://test2.png")
            rating = 2
            facilities = arrayListOf("facility 1","facility 2")
        }
    }

    @Test
    fun loadHotel_available_loadsHotel(){
        presenter.loadHotel()

        verify(apiClient).getHotel(capture(callbackCaptor))
        callbackCaptor.value.onSuccess(hotel)

        val inOrder = Mockito.inOrder(view)
        inOrder.verify(view).setLoadingIndicator(true)
        inOrder.verify(view).setLoadingIndicator(false)
        verify(view).showHotel(eq(hotel))
    }

    @Test
    fun loadHotel_error_showsErrorMsg(){
        presenter.loadHotel()

        verify(apiClient).getHotel(capture(callbackCaptor))
        callbackCaptor.value.onFailure()

        val inOrder = Mockito.inOrder(view)
        inOrder.verify(view).setLoadingIndicator(true)
        inOrder.verify(view).setLoadingIndicator(false)
        verify(view).showErrorMsg(eq(R.string.something_went_wrong))
    }
}