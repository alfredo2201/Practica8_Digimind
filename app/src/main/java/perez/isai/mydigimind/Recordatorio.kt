package perez.isai.mydigimind

import android.text.Editable
import java.io.Serializable

data class Recordatorio(var lu:Boolean,var ma:Boolean,var mi:Boolean,var ju:Boolean,var vi:Boolean,var sa:Boolean,var dom :Boolean, var tiempo:String, var nombre: String):Serializable