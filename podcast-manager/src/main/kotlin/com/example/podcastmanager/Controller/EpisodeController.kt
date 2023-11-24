package com.example.podcastmanager.Controller

import com.example.podcastmanager.Dto.CreateEpisodeRequest
import com.example.podcastmanager.Models.Episode
import com.example.podcastmanager.Models.User
import com.example.podcastmanager.Services.EpisodeService
import com.example.podcastmanager.Services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("Episodes")
class EpisodeController {
    @Autowired
    lateinit var service :EpisodeService
    @Autowired
    lateinit var userService: UserService

    @GetMapping("allEpisodes")
    fun getAllEpisodes(): MutableList<Episode>{
        return service.getAllEpisodes()
    }
    @GetMapping("{podcastId}")
    fun getEpisodesOfPodcast(@PathVariable podcastId: Long): MutableList<Episode> {
        val seeAllEpisodes = service.getAllEpisodes()
        var theNeededEpisodes = mutableListOf<Episode>()
        for (ep in seeAllEpisodes.indices)
        {
            if(seeAllEpisodes.component1().podcast.id == podcastId)
            {
                theNeededEpisodes.add(seeAllEpisodes.component1())
                seeAllEpisodes.removeFirst()
            }
            else
            {
                seeAllEpisodes.removeFirst()
            }
        }
        return theNeededEpisodes
    }
    @GetMapping("podcast/{episodeId}")
    fun getEpisodeById(@PathVariable episodeId: Long): Episode? {
        return service.getEpisodeById(episodeId)
    }

    @PostMapping("newEpisode")
    fun makeNewEpisode(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String, @RequestBody e:CreateEpisodeRequest): Episode{

        if (userService.isAuthenticated(token)) {
            return service.makeNewEpisode(e)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }

    @PostMapping("deleteEpisode")
    fun deleteEpisode(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String ,@RequestBody delEpisode: CreateEpisodeRequest){

        if (userService.isAuthenticated(token)) {
            return service.deleteEpisode(delEpisode)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }

    @PostMapping("like/{userId}/{episodeId}")
    fun likeEpisode(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String, @PathVariable userId: Long, @PathVariable episodeId: Long): Any {
        if (userService.isAuthenticated(token)) {
            return service.likedEpisode(userId, episodeId)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }

    }
    @PostMapping("unlike/{userId}/{episodeId}")
    fun unlikeEpisode(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String, @PathVariable userId: Long, @PathVariable episodeId: Long): Any {
        if (userService.isAuthenticated(token)) {
            return service.unlikeEpisode(userId, episodeId)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }
    @GetMapping("likes/{episodeId}")
    fun getLikeCount(@PathVariable episodeId: Long): Long {
        return service.getLikeCount(episodeId)
    }

    @PutMapping("{episodeId}/view")
    fun addView(@PathVariable episodeId: Long) {

        println("controller $episodeId")
        return  service.addView(episodeId)
    }
    @GetMapping("Trending")
    fun getTrending(): List<Episode> {
        return service.getTrending()
    }


}