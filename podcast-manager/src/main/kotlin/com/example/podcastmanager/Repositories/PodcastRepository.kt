package com.example.podcastmanager.Repositories

import com.example.podcastmanager.Models.Podcast
import com.example.podcastmanager.Models.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import java.util.*


@Repository
interface PodcastRepository : JpaRepository<Podcast,Long>{

//val entityManager: EntityManager

    fun findPodcastById(id: Long): Podcast

    @Query(value = "SELECT COUNT(*) FROM favorite_podcasts WHERE user_id = :userId AND podcast_id = :podcastId", nativeQuery = true)
    fun countByUserIdAndPodcastId(@Param("userId") userId: Long,@Param("podcastId") podcastId: Long): Long

    //INSERT INTO `favorite_podcasts` (`user_id`, `podcast_id`) VALUES ('1', '28');
    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO favorite_podcasts (user_id, podcast_id) VALUES (:userId, :podcastId)",
        nativeQuery = true
    )
    fun subscribeToPodcast(@Param("userId") userId: Long, @Param("podcastId") podcastId: Long)

    /*fun addPodcastToFavorites(user: User, podcast: Podcast) {
        entityManager.createNativeQuery(
            "INSERT INTO favorite_podcasts (user_id, podcast_id) VALUES (?, ?)"
        ).apply {
            setParameter(1, user.id)
            setParameter(2, podcast.id)
            executeUpdate()
        }
    }*/
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM `favorite_podcasts` WHERE `user_id` = :userId AND `podcast_id` = :podcastId",
        nativeQuery = true
    )
    fun unsubscribeToPodcast(@Param("userId") userId: Long, @Param("podcastId") podcastId: Long)
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM `favorite_podcasts` WHERE `podcast_id` = :podcastId",
        nativeQuery = true
    )
    fun forceRemovesubscriptions(@Param("podcastId") podcastId: Long)

}