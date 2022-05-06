package perez.isai.mydigimind.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.recordatorios.view.*
import perez.isai.mydigimind.Carrito
import perez.isai.mydigimind.R
import perez.isai.mydigimind.Recordatorio

class HomeFragment : Fragment() {
    private lateinit var homeViewModel:HomeViewModel
    var carrito = Carrito()
    companion object{
        var task = ArrayList<Recordatorio>()
        var first = true
    }

    private var adapter: RecordatorioAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home,container,false)

        homeViewModel.text.observe(viewLifecycleOwner, Observer{

        })
        if(first){
            fillTask()
            first = false
        }
        adapter = RecordatorioAdapter(context,carrito.recordatorios)
        root.gridView.adapter = adapter
        for (t in task){
            carrito.agregar(t)
        }
        return root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var key = "recordatorio"
        var bundle = Bundle()
        setFragmentResultListener("key") { recordatorio, bundle ->
            val result: Recordatorio = bundle.getSerializable("recordatorio") as Recordatorio
            carrito.agregar(result)
        }

    }
    fun fillTask(){
        //task.add(Recordatorio(arrayListOf("Tuesday"),"17:30","Practice 1"))
       // task.add(Recordatorio(arrayListOf("Monday","Tuesday"),"17:30","Practice 2"))
       // task.add(Recordatorio(arrayListOf("Wednesday"),"17:30","Practice 3"))
       // task.add(Recordatorio(arrayListOf("Wednesday"),"17:30","Practice 4"))
      //  task.add(Recordatorio(arrayListOf("Friday"),"17:30","Practice 5"))
       // task.add(Recordatorio(arrayListOf("Wednesday"),"17:30","Practice 6"))


    }

    class RecordatorioAdapter: BaseAdapter {
        var recordatorio = ArrayList<Recordatorio>()
        var context: Context? = null

        constructor(context: Context?, recordatorio: ArrayList<Recordatorio>) {
            this.context = context
            this.recordatorio = recordatorio
        }

        override fun getCount(): Int {
            return recordatorio.size
        }

        override fun getItem(p0: Int): Any {
            return recordatorio[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var rec = recordatorio[p0]
            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflator.inflate(R.layout.recordatorios, null)
            var days: ArrayList<String> = arrayListOf()
            if (rec.lu) {
                days.add("Monday")
            }
            if (rec.ma) {
                days.add("Tuesday")

            }
            if (rec.mi) {
                days.add("Wednesday")
            }
            if (rec.ju) {
                days.add("Thursday")
            }
            if (rec.vi) {
                days.add("Friday")

            }
            if (rec.sa) {
                days.add("Saturday")
            }
            if (rec.dom) {
                days.add("Sunday")
            }
            if (rec.lu && rec.ma && rec.mi && rec.ju && rec.vi &&
                rec.sa && rec.dom
            ) {
                days.removeAll(days)
                days.add("Everyday")
            }
            vista.txtDiasRecordatorio.text = days.toString()
            vista.txtNombreRecordatorio.text = rec.nombre.toString()
            vista.txtTiempoRecordatorio.text = rec.tiempo.toString()
            return vista

        }

    }
}