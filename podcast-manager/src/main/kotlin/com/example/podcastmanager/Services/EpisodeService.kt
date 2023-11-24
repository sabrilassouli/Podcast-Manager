package com.example.podcastmanager.Services

import com.example.podcastmanager.Dto.CreateEpisodeRequest
import com.example.podcastmanager.Models.Episode
import com.example.podcastmanager.Models.Podcast
import com.example.podcastmanager.Repositories.EpisodeRepository
import com.example.podcastmanager.Repositories.PodcastRepository
import com.example.podcastmanager.designPattern.EntityFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class EpisodeService(

    private val episodeFactory: EntityFactory<Episode>
) {
    @Autowired
    lateinit var episodeRepository:EpisodeRepository
    @Autowired
    lateinit var podcastRepository:PodcastRepository

    fun getAllEpisodes(): MutableList<Episode>{
        return episodeRepository.findAll()
    }

    fun makeNewEpisode(e: CreateEpisodeRequest): Episode {
        val podcast = podcastRepository.findById(e.podcastId).orElse(null)
            print(e.podcastId)
            print(podcast)

        val newEpisode = episodeFactory.createInstance().apply {
            episodeTitle = e.episodeTitle
            thumbnail = e.thumbnail
            video = e.video
            viewCount = e.viewCount
            this.podcast = podcast
        }

        return episodeRepository.save(newEpisode)
    }

    fun deleteEpisode(delEpisode: CreateEpisodeRequest){
        val episodeId = delEpisode.id
        episodeRepository.unlikeAllEpisode(episodeId)
        return episodeRepository.deleteById(delEpisode.id)
    }
    fun getEpisodeById(episodeId: Long): Episode {
        val seeEpisode = episodeRepository.findEpisodeById(episodeId)
        println(seeEpisode)
        return seeEpisode
    }
    fun likedEpisode(userId: Long, episodeId: Long): Any {
        val count = episodeRepository.countByUserIdAndEpisodeId(userId, episodeId)
        if (count > 0) {
            // episode already liked
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("episode was already liked")
        } else {
            // like the episode
            episodeRepository.likeEpisode(userId, episodeId)
            return ResponseEntity.ok("episode liked")
        }
    }
    fun unlikeEpisode(userId: Long, episodeId: Long): Any {
        val count = episodeRepository.countByUserIdAndEpisodeId(userId, episodeId)
        if (count > 0) {
            // like removed
            episodeRepository.unlikeEpisode(userId, episodeId)
            return ResponseEntity.ok("episode was unlikes")
        } else {
            // episode was not liked
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("like did not exist")
        }
    }
    fun getLikeCount(episodeId: Long): Long {
        val episodes = episodeRepository.getLikeCount(episodeId)

        return episodes

    }
    fun addView(episodeId: Long) {
        println("service $episodeId")
        return episodeRepository.addView(episodeId)
    }
    fun getTrending(): List<Episode> {
        var allEpisodes = episodeRepository.findAll()
        var trending = allEpisodes.sortedByDescending { it.viewCount }.take(5)
        return trending
    }


}