package com.example.podcastmanager.Services

import com.example.podcastmanager.Dto.CreatePodcastRequest
import com.example.podcastmanager.Models.Podcast
import com.example.podcastmanager.Repositories.EpisodeRepository
import com.example.podcastmanager.Repositories.PodcastRepository
import com.example.podcastmanager.Repositories.UserRepository
import com.example.podcastmanager.designPattern.EntityFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PodcastService(

    private val podcastFactory: EntityFactory<Podcast>
) {
    @Autowired
    lateinit var podcastRepository: PodcastRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var episodeRepository: EpisodeRepository


    fun getAllPodcasts(): MutableList<Podcast>{
        return podcastRepository.findAll()
    }

    fun makeNewPodcast(p: CreatePodcastRequest, userId: Long): Podcast {
        val creator = userRepository.findById(userId).orElse(null)

        val newPodcast = podcastFactory.createInstance().apply {
            podcastName = p.podcastName
            channel = p.channel
            coverImage = p.coverImage
            this.creator = creator
        }

        return podcastRepository.save(newPodcast)
    }

    fun deletePodcast(delPodcast:CreatePodcastRequest){
        val allTheEpisodes = episodeRepository.findAll()
        println("here are all the episodes $allTheEpisodes")
        println("here is the podcast id ${delPodcast.id}")
        for (ep in allTheEpisodes.indices)
        {
            if(allTheEpisodes.component1().podcast.id == delPodcast.id)
            {

                val episodeId = allTheEpisodes.component1().id
                println(allTheEpisodes.component1().podcast.id)
                println("deleting the liked episodes for id $episodeId")
                episodeRepository.unlikeAllEpisode(episodeId)
                episodeRepository.deleteById(episodeId)
                allTheEpisodes.removeFirst()
            }
            else
            {
                allTheEpisodes.removeFirst()
            }
        }
        podcastRepository.forceRemovesubscriptions(delPodcast.id)
        return podcastRepository.deleteById(delPodcast.id)
    }

    fun subscribeToPodcast(userId: Long, podcastId: Long): Any {

        val count = podcastRepository.countByUserIdAndPodcastId(userId, podcastId)
        if (count > 0) {
            // Podcast already exists in favorites
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Podcast already exists in favorites")
        } else {
            // Add the podcast to favorites
            podcastRepository.subscribeToPodcast(userId, podcastId)
            return ResponseEntity.ok("Podcast added to favorites")
        }
        //return podcastRepository.addPodcastToFavorites(userId, podcastId)
    }

    fun unsubscribeToPodcast(userId: Long, podcastId: Long): Any{
        val count = podcastRepository.countByUserIdAndPodcastId(userId, podcastId)
        if (count > 0){
            podcastRepository.unsubscribeToPodcast(userId, podcastId)
            return ResponseEntity.ok("podcast was unfollowed")
        } else{
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("follow did not exist")
        }
    }


}