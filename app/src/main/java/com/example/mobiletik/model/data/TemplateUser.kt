package com.example.mobiletik.model.data

data class TemplateUser(
    val uid : String,
    val userName : String,
    val userNIS : String,
    val userEmail : String,
    val userKelas : String,
    val kuisSatu : Long = 0,
    val kuisDua : Long = 0,
    val kuisTiga : Long = 0,
    val kuisEmpat : Long = 0,
    val kuisLima : Long = 0,
    val kuisSatuAttempt : Long = 0,
    val kuisDuaAttempt : Long = 0,
    val kuisTigaAttempt : Long = 0,
    val kuisEmpatAttempt : Long = 0,
    val kuisLimaAttempt : Long = 0
)
