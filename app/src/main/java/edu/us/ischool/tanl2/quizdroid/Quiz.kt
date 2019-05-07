package layout

import java.io.Serializable

data class Quiz (val question:String, val answers:Array<String>, val answerIndex:Int):Serializable{

}