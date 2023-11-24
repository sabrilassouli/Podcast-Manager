package com.example.podcastmanager.Controller

import com.example.podcastmanager.Dto.CreateUserRequest
import com.example.podcastmanager.Dto.loginUserRequest
import com.example.podcastmanager.Models.Episode
import com.example.podcastmanager.Models.Podcast
import com.example.podcastmanager.Models.User
import com.example.podcastmanager.Services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("Users")
class UserController {
    @Autowired
    lateinit var service: UserService

    @GetMapping("allUsers")
    fun getAllUsers(): MutableList<User> {
        return service.getAllUsers()
    }

    @GetMapping("/{userId}")
    fun getUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String, @PathVariable userId: Long): User? {

        if (service.isAuthenticated(token)) {
            return service.getUserById(userId)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }

    @GetMapping("/{userId}/favoritePodcasts")
    fun getUserFavoritesPodcasts(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,@PathVariable userId: Long): List<Podcast> {
        if (service.isAuthenticated(token)) {
            return service.getFavoritePodcasts(userId)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }
    @GetMapping("/{userId}/likedEpisodes")
    fun getUserLikedEpisodes(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,@PathVariable userId: Long): List<Episode> {

        if (service.isAuthenticated(token)) {
            return service.getLikedEpisodes(userId)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }
    @PostMapping("newUser")
    fun makeNewUser(@RequestBody u: CreateUserRequest): Any {
        return service.makeNewUser(u)
    }
    @PostMapping("deleteUser")
    fun deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,@RequestBody delUser: CreateUserRequest) {

        if (service.isAuthenticated(token)) {
            return service.deleteUser(delUser)
        } else {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token not present or accepted ")
        }
    }

    @RequestMapping("/login")
    @PostMapping
    fun loginUser(@RequestBody userRequest: loginUserRequest): User? {
        return service.loginUser(userRequest)
    }

}