package com.example.podcastmanager.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne

@Entity
class Episode(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,
    var episodeTitle:String? = null,
    var thumbnail:String? = null,
    var video:String? = null,
    var viewCount: Long = 0
) {
        //connection between podcasts and episodes
        @ManyToOne
        @JoinColumn(name = "podcast_id")
        @JsonManagedReference
        open lateinit var podcast: Podcast

        @ManyToMany(mappedBy = "likedEpisodes")
        @JsonBackReference
        val UsersLiking: List<User> = listOf()
}