package com.example.podcastmanager.Controller

import com.example.podcastmanager.Dto.CreatePodcastRequest
import com.example.podcastmanager.Models.Podcast
import com.example.podcastmanager.Repositories.EpisodeRepository
import com.example.podcastmanager.Services.EpisodeService
import com.example.podcastmanager.Services.PodcastService
import com.example.podcastmanager.Services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("Podcasts")
class PodcastController {
    @Autowired
    lateinit var service : PodcastService
    @Autowired
    lateinit var episodeService: EpisodeService
    @Autowired
    lateinit var episodeRepository: EpisodeRepository
    @Autowired
    lateinit var userService: UserService

    @GetMapping("allPodcasts")
    fun getAllPodcasts(): MutableList<Podcast>{
        return service.getAllPodcasts()
    }

    @GetMapping("{userId}/myPodcasts")
    fun getPodcastsMadeByUser(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,@PathVariable userId:Long): MutableList<Podcast>{

        val seeAllPodcasts = service.getAllPodcasts()
        var theNeededPodcasts = mutableListOf<Podcast>()
        for (pod in seeAllPodcasts.indices)
        {
            if(seeAllPodcasts.component1().creator.id == userId)
            {
                theNeededPodcasts.add(seeAllPodcasts.component1())
                seeAllPodcasts.removeFirst()
            }
            else
            {
                seeAllPodcasts.removeFirst()
            }
        }
        if (userService.isAuthenticated(token)) {
            return theNeededPodcasts
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }

    }

    @PostMapping("{userId}/newPodcast")
    fun makeNewPodcast(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,@RequestBody p: CreatePodcastRequest,@PathVariable userId:Long): Podcast {

        if (userService.isAuthenticated(token)) {
            return service.makeNewPodcast(p, userId)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }
    @PostMapping("deletePodcast")
    fun deletePodcast(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,@RequestBody delPodcast: CreatePodcastRequest){

        if (userService.isAuthenticated(token)) {
            return service.deletePodcast(delPodcast)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }
    @PostMapping("{userId}/{podcastId}/subscribe")
    fun subscribeToPodcast(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,@PathVariable userId: Long, @PathVariable podcastId: Long): Any {

        if (userService.isAuthenticated(token)) {
            return service.subscribeToPodcast(userId, podcastId)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }
    @PostMapping("{userId}/{podcastId}/unsubscribe")
    fun unsubscribeToPodcast(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,@PathVariable userId: Long, @PathVariable podcastId: Long): Any {

        if (userService.isAuthenticated(token)) {
            return service.unsubscribeToPodcast(userId, podcastId)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }

}