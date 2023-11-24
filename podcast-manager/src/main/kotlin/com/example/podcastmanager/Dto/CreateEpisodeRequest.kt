package com.example.podcastmanager.Dto

class CreateEpisodeRequest(

    val id: Long = -1,
    var episodeTitle:String?,
    var thumbnail:String?,
    var video:String?,
    var viewCount: Long = 0,
    var podcastId: Long
) {
}