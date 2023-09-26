package com.cslab.blovi.configure.mapper

import com.cslab.blovi.dto.UserDTO
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper{
    fun findUserByEmail(email:String): UserDTO
    fun findUserByPassword(password : String) : UserDTO
}
