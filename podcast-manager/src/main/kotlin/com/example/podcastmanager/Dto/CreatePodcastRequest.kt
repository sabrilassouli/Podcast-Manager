package com.example.podcastmanager.Dto

class CreatePodcastRequest(
    val id:Long = -1,
    val podcastName:String?,
    val channel:String?,
    val coverImage:String?

) {
}