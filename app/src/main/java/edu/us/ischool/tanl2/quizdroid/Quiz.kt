package layout

import java.io.Serializable

data class Quiz (val text:String, val answers:Array<String>, val answer:Int):Serializable{

}