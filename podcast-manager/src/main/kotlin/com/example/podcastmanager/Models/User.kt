package com.example.podcastmanager.Models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    val id: Long = -1,
    var username:String? = null,
    var email:String? = null,
    var password:String? = null
) {

    var accessToken = ""
    var expirationDate = System.currentTimeMillis()

    //@ManyToMany
    //@JsonBackReference
    //@JoinTable(name = "favorite_Podcasts",
    //    joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
    //    inverseJoinColumns = [JoinColumn(name = "podcast_id", referencedColumnName = "id")])
    //lateinit var favoritePodcasts: List<Podcast>
    @ManyToMany
    @JsonBackReference
    @JoinTable(
        name = "favorite_podcasts",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "podcast_id")])
    lateinit var favoritePodcasts: List<Podcast>
    @ManyToMany
    @JsonBackReference
    @JoinTable(
        name = "liked_episodes",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "Episode_id")])
    lateinit var likedEpisodes: List<Episode>

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    var createdPodcasts: List<Podcast> = listOf()
}