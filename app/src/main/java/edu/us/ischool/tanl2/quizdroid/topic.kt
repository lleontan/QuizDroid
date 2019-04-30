package layout

data class topic (val name:String,val description:String,
                  var questions:List<question>){
}