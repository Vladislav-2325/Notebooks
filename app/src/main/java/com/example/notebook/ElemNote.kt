package com.example.notebook

import java.io.Serializable

class ElemNote(var theme:String,var desc:String,var content:String,var date:String, var fav:Boolean = false):Serializable {

}