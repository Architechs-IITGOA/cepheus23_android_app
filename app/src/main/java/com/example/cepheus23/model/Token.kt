package com.example.cepheus23.model

object Token {
    init {
        println("singleton class created")
    }
    lateinit var token:String
    var register:Boolean = true
    var id : Int = 0
}