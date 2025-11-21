package com.example.mini_project_grah

import kotlinx.parcelize.Parcelize

data class bankMahasiswa(var nama:String, var nrp:String, var jurusan:String, var imageId:Int, var aboutMe:String, var myCourse:String, var myExperience:String, var isFriend: Boolean = false)