package com.example.podcastmanager.designPattern

import com.example.podcastmanager.Models.Podcast
import com.example.podcastmanager.Models.User
import com.example.podcastmanager.Models.Episode
import org.springframework.stereotype.Component

interface EntityFactory<T> {
    fun createInstance(): T
}
@Component // Use @Component or any appropriate annotation for dependency injection
class PodcastFactory : EntityFactory<Podcast> {
    override fun createInstance(): Podcast {
        return Podcast()
    }
}

@Component
class UserFactory : EntityFactory<User> {
    override fun createInstance(): User {
        return User()
    }
}

@Component
class EpisodeFactory : EntityFactory<Episode> {
    override fun createInstance(): Episode {
        return Episode()
    }
}