package perez.isai.mydigimind.ui.dashboard

import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.json.JSONArray
import org.json.JSONObject
import perez.isai.mydigimind.R
import perez.isai.mydigimind.Recordatorio
import perez.isai.mydigimind.TimePickerFragment
import java.util.HashSet

class DashboardFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
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
        db = Firebase.firestore
        auth = FirebaseAuth.getInstance()
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
            var mon = false
            var tue = false
            var wed = false
            var thu = false
            var fri = false
            var sat = false
            var sun = false

            if( check_monday.isChecked) {
                    days.add("Monday")
                    mon = true
                }
                if(check_tuesday.isChecked) {
                    days.add("Tuesday")
                    tue = true
                }
                if(check_wednesday.isChecked) {
                    days.add("Wednesday")
                    wed = true
                }
                if(check_thursday.isChecked) {
                    days.add("Thursday")
                    thu = true
                }
                if(check_friday.isChecked) {
                    days.add("Friday")
                    fri = true
                }
                if(check_saturday.isChecked) {
                    days.add("Saturday")
                    sat = true
                }
                if(check_sunday.isChecked){
                    days.add("Sunday")
                    sun = true
                }
                if(check_monday.isChecked && check_tuesday.isChecked && check_wednesday.isChecked && check_thursday.isChecked && check_friday.isChecked &&
                        check_saturday.isChecked && check_sunday.isChecked) {
                            days.removeAll(days)
                            days.add("Everyday")
                    mon = true
                    tue = true
                    wed = true
                    thu = true
                    fri = true
                    sat = true
                    sun = true
                }

            var recordatorio = Recordatorio(mon,tue,wed,thu,fri,sat,sun,time,name.toString())
            val bundle = Bundle()
            val recorded = hashMapOf(
                "actividad" to name,
                "lu" to mon,
                "ma" to tue,
                "mi" to wed,
                "ju" to thu,
                "vi" to fri,
                "sa" to sat,
                "do" to sun,
                "tiempo" to time
            )
            val user = auth.currentUser?.email
            if (user != null) {
                db.collection("actividades").add(recordatorio)
            }
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