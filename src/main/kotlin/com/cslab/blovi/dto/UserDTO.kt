package com.cslab.blovi.dto

import java.time.LocalDate

data class UserDTO(
    val id :Int,
    val email : String,
    val password : String,
    val nickname : String,
    val blog_name : String,
    val created_at : LocalDate,
    val state : Int,
    val profile_img : String,
    val intro : String,
    val follow_cnt : Int,
    val follower_cnt : Int,
    val visited_cnt :Int,
    val role : String
){
    fun getPassword(): String {
        return password
    }
}
