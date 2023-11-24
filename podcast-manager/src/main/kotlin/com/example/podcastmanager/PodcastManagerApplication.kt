package com.example.podcastmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PodcastManagerApplication

fun main(args: Array<String>) {
	runApplication<PodcastManagerApplication>(*args)
}
