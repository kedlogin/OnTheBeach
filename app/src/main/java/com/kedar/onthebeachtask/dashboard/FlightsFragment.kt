package com.kedar.onthebeachtask.dashboard

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.DialogCompat
import androidx.fragment.app.Fragment
import com.kedar.onthebeachtask.MyApplication
import com.kedar.onthebeachtask.R
import com.kedar.onthebeachtask.data.APIClientInterface
import com.kedar.onthebeachtask.model.Flight
import kotlinx.android.synthetic.main.dialog_flights_filter.*
import kotlinx.android.synthetic.main.fragment_flights.*
import kotlinx.android.synthetic.main.fragment_flights.view.*
import javax.inject.Inject

class FlightsFragment : Fragment(),FlightsView {

    @Inject
    lateinit var apiClient:APIClientInterface

    lateinit var presenter: FlightsPresenter
    lateinit var adapter:FlightsRecyclerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_flights,container,false)
        setHasOptionsMenu(true)
        presenter = FlightsPresenter(this,apiClient)
        view.rvFlights.adapter = FlightsRecyclerAdapter(activity!!).apply {
            adapter = this
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.loadFlights()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_filter_flights -> {
                presenter.filterMenuClicked()
                true
            }
            else -> false
        }

    override fun showFlights(flights: ArrayList<Flight>) {
        if (flights.isEmpty()){
            rvFlights.visibility = View.GONE
            tvDefault.visibility = View.VISIBLE
        }else {
            rvFlights.visibility = View.VISIBLE
            tvDefault.visibility = View.GONE
            adapter.flights = flights
        }
    }

    override fun showFilterDialog(airlines:List<String>,airlineFilter:String?,departure:List<String>,departureFilter:String?,arrival:List<String>,arrivalFilter:String?) {
        activity?.let {
            val dialog = Dialog(it)
            dialog.setContentView(R.layout.dialog_flights_filter)

            dialog.spinner.adapter = ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,airlines)
            airlineFilter?.let { dialog.spinner.setSelection(airlines.indexOf(it)) }

            dialog.spinner2.adapter = ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,departure)
            departureFilter?.let { dialog.spinner2.setSelection(departure.indexOf(it)) }

            dialog.spinner3.adapter = ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,arrival)
            arrivalFilter?.let { dialog.spinner3.setSelection(arrival.indexOf(it)) }

            dialog.button.setOnClickListener {
                val airlineFilter = if(dialog.spinner.selectedItemPosition == 0) null else dialog.spinner.selectedItem.toString()
                val departureFilter = if(dialog.spinner2.selectedItemPosition == 0) null else dialog.spinner2.selectedItem.toString()
                val arrivalFilter = if(dialog.spinner3.selectedItemPosition == 0) null else dialog.spinner3.selectedItem.toString()
                presenter.applyFilter(airlineFilter,departureFilter,arrivalFilter)
                dialog.dismiss()
            }

            dialog.button2.setOnClickListener {
                presenter.resetFilter()
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun showErrorMsg(str: Int) {
        Toast.makeText(activity,str,Toast.LENGTH_SHORT).show()
    }

    override fun setLoadingIndicator(isLoading: Boolean) {
        if (isLoading){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }
}