package com.example.podcastmanager.Repositories

import com.example.podcastmanager.Models.Episode
import com.example.podcastmanager.Models.Podcast
import com.example.podcastmanager.Models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<User,Long> {


    fun findByUsername(username: String): User?

    fun findByAccessToken(accessToken: String): User?

    fun findUserById(id: Long): User

    //INSERT INTO `favorite_podcasts` (`user_id`, `podcast_id`) VALUES ('1', '1');
    @Query("SELECT p FROM Podcast p JOIN p.usersFollowing u WHERE u.id = :userId")
    fun findFavoritePodcasts(@Param("userId") userId: Long): List<Podcast>

    @Query("SELECT p FROM Episode p JOIN p.UsersLiking u WHERE u.id = :userId")
    fun findLikedEpisodes(@Param("userId") userId: Long): List<Episode>

}