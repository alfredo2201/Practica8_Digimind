package perez.isai.mydigimind.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.recordatorios.*
import kotlinx.android.synthetic.main.recordatorios.view.*
import kotlinx.coroutines.newFixedThreadPoolContext
import perez.isai.mydigimind.Carrito
import perez.isai.mydigimind.R
import perez.isai.mydigimind.Recordatorio
import perez.isai.mydigimind.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var homeViewModel:HomeViewModel
    var carrito = Carrito()
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
            adapter = RecordatorioAdapter(context,carrito.recordatorios)
            gridView.adapter = adapter
        }

    }
    class RecordatorioAdapter: BaseAdapter {
        var recordatorio = ArrayList<Recordatorio>()
        var context: Context? = null

        constructor(context: Context?, recordatorio: ArrayList<Recordatorio>){
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
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var vista = inflator.inflate(R.layout.recordatorios, null)
            vista.txtDiasRecordatorio.text = rec.dias.toString()
            vista.txtNombreRecordatorio.text = rec.nombre.toString()
            vista.txtTiempoRecordatorio.text = rec.tiempo.toString()
            return vista
        }

    }
}