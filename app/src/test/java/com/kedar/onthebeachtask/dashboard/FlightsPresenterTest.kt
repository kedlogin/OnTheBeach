package com.kedar.onthebeachtask.dashboard

import com.example.android.architecture.blueprints.todoapp.argumentCaptor
import com.example.android.architecture.blueprints.todoapp.capture
import com.kedar.onthebeachtask.R
import com.kedar.onthebeachtask.data.APIClientCallback
import com.kedar.onthebeachtask.data.APIClientInterface
import com.kedar.onthebeachtask.model.Flight
import com.kedar.onthebeachtask.model.FlightList
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

class FlightsPresenterTest {

    @Mock
    private lateinit var view: FlightsView

    @Mock
    private lateinit var apiClient: APIClientInterface

    @Captor private lateinit var callbackCaptor: ArgumentCaptor<APIClientCallback<FlightList>>

    private lateinit var presenter: FlightsPresenter

    private lateinit var flightList: FlightList

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = FlightsPresenter(view,apiClient)

        val testFlights = ArrayList<Flight>()

        testFlights.add(Flight().apply {
            airline = "Test Airline 1"
            departure_date = "2020-10-20T10:00:00Z"
            arrival_date = "2020-10-20T11:00:00Z"
            price = 12300
            departure_airport = "London Gatwick"
            arrival_airport = "Barcelona"
        })

        testFlights.add(Flight().apply {
            airline = "Test Airline 2"
            departure_date = "2020-10-20T10:00:00Z"
            arrival_date = "2020-10-20T11:00:00Z"
            price = 12300
            departure_airport = "London Gatwick"
            arrival_airport = "Barcelona"
        })

        testFlights.add(Flight().apply {
            airline = "Test Airline 1"
            departure_date = "2020-10-20T10:00:00Z"
            arrival_date = "2020-10-20T12:00:00Z"
            price = 12300
            departure_airport = "Heathrow"
            arrival_airport = "Barcelona"
        })
        flightList = FlightList().apply {
            flights = testFlights
        }
    }

    @Test
    fun loadFlights_available_loadsIntoView(){
        presenter.loadFlights()

        verify(apiClient).getFlights(capture(callbackCaptor))
        callbackCaptor.value.onSuccess(flightList)

        val inOrder = inOrder(view)
        inOrder.verify(view).setLoadingIndicator(true)
        inOrder.verify(view).setLoadingIndicator(false)

        val listCaptor = argumentCaptor<ArrayList<Flight>>()
        verify(view).showFlights(capture(listCaptor))
        assertTrue(listCaptor.value.size == 3)
    }

    @Test
    fun loadFlights_noFlights_EmptyList(){
        presenter.loadFlights()

        verify(apiClient).getFlights(capture(callbackCaptor))
        callbackCaptor.value.onSuccess(FlightList().apply { flights = arrayListOf() })

        val inOrder = inOrder(view)
        inOrder.verify(view).setLoadingIndicator(true)
        inOrder.verify(view).setLoadingIndicator(false)

        val listCaptor = argumentCaptor<ArrayList<Flight>>()
        verify(view).showFlights(capture(listCaptor))
        assertTrue(listCaptor.value.size == 0)
    }

    @Test
    fun loadFlights_errorFetching_showsErrorMsg(){
        presenter.loadFlights()

        verify(apiClient).getFlights(capture(callbackCaptor))
        callbackCaptor.value.onFailure()

        val inOrder = inOrder(view)
        inOrder.verify(view).setLoadingIndicator(true)
        inOrder.verify(view).setLoadingIndicator(false)

        verify(view).showErrorMsg(eq(R.string.something_went_wrong))
    }

    @Test
    fun filterMenuClicked_showsFilterDialog(){
        presenter.flights = flightList.flights
        presenter.filterMenuClicked()

        val airlineCaptor = argumentCaptor<List<String>>()
        val departureCaptor = argumentCaptor<List<String>>()
        val arrivalCaptor = argumentCaptor<List<String>>()
        verify(view).showFilterDialog(capture(airlineCaptor), nullable(String::class.java),
            capture(departureCaptor), nullable(String::class.java), capture(arrivalCaptor), nullable(String::class.java))

        assertTrue(airlineCaptor.value.size == 3)
        assertTrue(departureCaptor.value.size == 3)
        assertTrue(arrivalCaptor.value.size == 2)
    }

    @Test
    fun applyFilterClicked_zeroMatches_showsEmptyList(){
        presenter.flights = flightList.flights
        presenter.applyFilter("Test Airline 3",null,null)

        val listCaptor = argumentCaptor<ArrayList<Flight>>()
        verify(view).showFlights(capture(listCaptor))
        assertTrue(listCaptor.value.size == 0)
    }

    @Test
    fun applyFilterClicked_matchesAirlines_shows2Matches(){
        presenter.flights = flightList.flights
        presenter.applyFilter("Test Airline 1",null,null)

        val listCaptor = argumentCaptor<ArrayList<Flight>>()
        verify(view).showFlights(capture(listCaptor))
        assertTrue(listCaptor.value.size == 2)
    }

    @Test
    fun applyFilterClicked_matchesAirlinesAndArrival_shows3Matches(){
        presenter.flights = flightList.flights
        presenter.applyFilter("Test Airline 1",null,"Barcelona")

        val listCaptor = argumentCaptor<ArrayList<Flight>>()
        verify(view).showFlights(capture(listCaptor))
        assertTrue(listCaptor.value.size == 3)
    }

    @Test
    fun resetFilterClicked_loadAllFlightsDetails(){
        presenter.flights = flightList.flights
        presenter.airlineFilter = flightList.flights[0].airline

        presenter.resetFilter()
        val listCaptor = argumentCaptor<ArrayList<Flight>>()
        verify(view).showFlights(capture(listCaptor))
        assertTrue(listCaptor.value.size == 3)

        assertTrue(presenter.airlineFilter == null)
        assertTrue(presenter.departureFilter == null)
        assertTrue(presenter.arrivalFilter == null)
    }
}