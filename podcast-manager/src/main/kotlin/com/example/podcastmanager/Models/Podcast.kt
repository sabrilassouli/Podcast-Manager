package com.example.podcastmanager.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class Podcast(
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    val id:Long = -1,
    var podcastName:String? = null,
    var channel:String? = null,
    var coverImage:String? = null
) {
    @OneToMany(mappedBy = "podcast")
    @JsonBackReference
    var episodes: List<Episode> = listOf()

    //@ManyToMany(mappedBy = "favoritePodcasts")
    //@JsonBackReference
    //var usersFollowing:List<User> = listOf()
    @ManyToMany(mappedBy = "favoritePodcasts")
    @JsonBackReference
    val usersFollowing: List<User> = listOf()

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @JsonManagedReference
    open lateinit var creator: User
}