package com.example.podcastmanager.Dto

class CreateUserRequest(

    val id: Long = -1,
    val username:String?,
    val email:String?,
    val password:String?,
    val favoriteEpisodes:String?,
){
}