package com.example.mobiletik.model.data

data class TemplateUser(
    val uid : String,
    val userName : String,
    val userNIS : String,
    val userEmail : String,
    val kuisSatu : Long = 0,
    val kuisDua : Long = 0,
    val kuisTiga : Long = 0,
    val kuisEmpat : Long = 0,
    val kuisLima : Long = 0
)
