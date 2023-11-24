package com.example.podcastmanager.Services

import com.example.podcastmanager.Dto.CreateUserRequest
import com.example.podcastmanager.Dto.loginUserRequest
import com.example.podcastmanager.Models.Episode
import com.example.podcastmanager.Models.Podcast
import com.example.podcastmanager.Models.User
import com.example.podcastmanager.Repositories.UserRepository
import com.example.podcastmanager.designPattern.EntityFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(

    private val userFactory: EntityFactory<User>
) {
    @Autowired
    lateinit var userRepository: UserRepository

    fun getAllUsers():MutableList<User>{
        return userRepository.findAll()
    }

    fun getUserById(userId: Long): User {
        val seeUser = userRepository.findUserById(userId)
        println(seeUser)
        return seeUser
    }
    fun getFavoritePodcasts(userId: Long): List<Podcast> {
        return userRepository.findFavoritePodcasts(userId)
    }
    fun getLikedEpisodes(userId: Long): List<Episode> {
        return userRepository.findLikedEpisodes(userId)
    }

    fun makeNewUser(u:CreateUserRequest): Any {

        val newUser = userFactory.createInstance().apply {

            username = u.username
            email = u.email
            password = u.password
        }

        val user = newUser.username?.let { userRepository.findByUsername(it) }
         if (user != null) {
            return  "User found"
        } else {
            return userRepository.save(newUser)
        }
    }

    fun deleteUser(delUser:CreateUserRequest){
        return userRepository.deleteById(delUser.id)
    }

    fun loginUser(user: loginUserRequest): User? {
        val u = userRepository.findByUsername(user.username)
        if(u != null) {
            if(user.password == u.password) {
                u.accessToken = java.util.UUID.randomUUID().toString()
                //Setting expiration date to 1 day
                //(Millis * seconds * minutes * hours * days)
                u.expirationDate =  System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 1)
                userRepository.save(u)
                return u
            } else {
                return null
            }
        } else {
            return null
        }
    }
    fun isAuthenticated(token: String ): Boolean {
        val u = userRepository.findByAccessToken(token)
        if(u != null) {
            println("token still valid")
            return u.expirationDate > System.currentTimeMillis()
        } else {
            return false
        }
    }


}