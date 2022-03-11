package perez.isai.mydigimind

import android.text.Editable
import java.io.Serializable

data class Recordatorio(var dias:String, var tiempo:String, var nombre: Editable):Serializable