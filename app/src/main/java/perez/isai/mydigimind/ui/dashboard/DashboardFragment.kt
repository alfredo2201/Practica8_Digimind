package perez.isai.mydigimind.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_dashboard.*
import perez.isai.mydigimind.R
import perez.isai.mydigimind.Recordatorio
import perez.isai.mydigimind.TimePickerFragment

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard,container,false)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnTime.setOnClickListener {
            showTimePickerDialog()
        }
        btnSave.setOnClickListener {
            var name = editTextInputRemember.text
            var days = ArrayList<String>()
            var time = txtHour.text.toString()

            if( check_monday.isChecked) {
                    days.add("Monday")
                }
                if(check_tuesday.isChecked) {
                    days.add("Monday")
                }
                if(check_wednesday.isChecked) {
                    days.add("Monday")
                }
                if(check_thursday.isChecked) {
                    days.add("Monday")
                }
                if(check_friday.isChecked) {
                    days.add("Monday")
                }
                if(check_saturday.isChecked) {
                    days.add("Monday")
                }
                if(check_sunday.isChecked){
                    days.add("Monday")
                }
                if(check_monday.isChecked && check_tuesday.isChecked && check_wednesday.isChecked && check_thursday.isChecked && check_friday.isChecked &&
                        check_saturday.isChecked && check_sunday.isChecked) {
                            days.removeAll(days)
                            days.add("Everyday")
                }

            var recordatorio = Recordatorio(days,time,name.toString())
            val bundle = Bundle()
            bundle.putSerializable("recordatorio",recordatorio)
            clean()
            setFragmentResult("key",bundle)

        }
    }
    private fun showTimePickerDialog(){
        val timePicker = TimePickerFragment{onTimeSelected(it)}
        timePicker.show(parentFragmentManager,"time")
    }
    private fun onTimeSelected(time:String){
        txtHour.text = time
    }
    private fun clean(){
        editTextInputRemember.setText("")
        check_monday.isChecked =false
        check_tuesday.isChecked =false
        check_wednesday.isChecked =false
        check_thursday.isChecked =false
        check_friday.isChecked =false
        check_saturday.isChecked =false
        check_sunday.isChecked =false
        txtHour.text = ""


    }
}