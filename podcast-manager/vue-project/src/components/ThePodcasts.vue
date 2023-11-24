<script>

import Cookies from 'js-cookie'
export default {
    data() {
        return {
            podcasts: []
        }
    },
    methods: {

        async getData() {
            try {
                const res = await fetch('http://localhost:8080/Podcasts/allPodcasts', {
                    method: 'GET'
                })
                const finalRes = await res.json();
                this.podcasts = finalRes
                console.log(finalRes)
            } catch (error) {
                console.log(error)
            }
        },
        goToEpisodesPage(podcastId) {
            this.$router.push(`/podcasts/${podcastId}/episodes`);
        },
        async addToFavorite(podcastId) {
            this.error = null

            try {
                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const response = await fetch(`http://localhost:8080/Podcasts/${userId}/${podcastId}/subscribe`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `${accessToken}`
                    },
                    body: JSON.stringify({
                        podcastName: this.podcastName,
                        channel: this.channel,
                        coverImage: this.coverImage
                    })
                })

                if (!response.ok) {
                    const error = await response.json()
                    this.error = error.message
                } else {
                    const data = await response.json()
                    console.log(data)
                    console.log("here is the data")
                }
            } catch (error) {
                this.error = error.message
                console.log(error)
            }

        },
    },
    mounted() {
        //This function runs when your component is mounted
        console.log(`Im the Podcasts component!`);

        this.getData();
    }
}
</script>

<template>
    <div>
        <ul v-if="podcasts.length">
            <li v-for="podcast in podcasts" :key="podcast.id">
                var thisPodcast = {{ podcast.id }}
                <h2><strong>channel: </strong>{{ podcast.channel }}</h2>
                <h2><strong>podcast: </strong>{{ podcast.podcastName }}</h2>
                <img class="coverImage" :src="podcast.coverImage">
                <br>
                <!--<router-link :to="`/podcasts/${podcast.id}/episodes`">Go to Podcast Episodes</router-link>-->
                <button class="button" @click="goToEpisodesPage(podcast.id)">Go to Podcast Episodes</button>
                <button class="button" @click="addToFavorite(podcast.id)">favorite</button>
            </li>
        </ul>
    </div>
</template>

<style>
.coverImage {
    width: 30%;
}

.button {
    border: none;
    border-radius: 5px;
    background-color: #00BD7E;
    color: white;
    padding: 14px 20px;
    margin: 10px;
    cursor: pointer;
}
</style>