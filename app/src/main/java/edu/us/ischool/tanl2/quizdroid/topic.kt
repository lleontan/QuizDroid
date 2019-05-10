package layout

data class topic (val title:String, val desc:String, val longDescription:String="", var questions:List<Quiz>){
}