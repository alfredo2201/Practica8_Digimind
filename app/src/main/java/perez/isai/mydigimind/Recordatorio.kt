package perez.isai.mydigimind

import android.text.Editable
import java.io.Serializable

data class Recordatorio(var dias:ArrayList<String>, var tiempo:String, var nombre: String):Serializable