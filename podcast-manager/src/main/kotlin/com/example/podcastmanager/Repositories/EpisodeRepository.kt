package com.example.podcastmanager.Repositories

import com.example.podcastmanager.Models.Episode
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface EpisodeRepository: JpaRepository<Episode,Long> {

    fun findEpisodeById(id: Long): Episode

    @Query(value = "SELECT COUNT(*) FROM liked_episodes WHERE user_id = :userId AND episode_id = :episodeId", nativeQuery = true)
    fun countByUserIdAndEpisodeId(@Param("userId") userId: Long, @Param("episodeId") episodeId: Long): Long

    @Query(value = "SELECT COUNT(*) FROM liked_episodes WHERE episode_id = :episodeId", nativeQuery = true)
    fun getLikeCount(@Param("episodeId") episodeId: Long): Long

    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO `liked_episodes` (`user_id`, `episode_id`) VALUES (:userId,:episodeId);",
        nativeQuery = true
    )
    fun likeEpisode(@Param("userId") userId: Long, @Param("episodeId") episodeId: Long)

    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM `liked_episodes` WHERE `user_id` = :userId AND `episode_id` = :episodeId",
        nativeQuery = true
    )
    fun unlikeEpisode(@Param("userId") userId: Long, @Param("episodeId") episodeId: Long)
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM `liked_episodes` WHERE `episode_id` = :episodeId",
        nativeQuery = true
    )
    fun unlikeAllEpisode( @Param("episodeId") episodeId: Long)

    @Modifying
    @Transactional
    @Query(
        value = "UPDATE `episode` SET `view_count` = `view_count` + 1 WHERE `episode`.`id` = :episodeId",
        nativeQuery = true)
    fun addView(@Param("episodeId") episodeId: Long)
}

