<script>

import Cookies from 'js-cookie'
export default {
    data() {
        return {
            episodes: [],
            podcastId: null,
            episodeId: null,
            likeCount: null
        }
    },
    methods: {

        async getData() {
            try {
                this.episodeId = this.$route.params.episodeId;

                const res = await fetch(`http://localhost:8080/Episodes/podcast/${this.episodeId}`, {
                    method: 'GET'
                })
                const finalRes = await res.json();
                this.episodes = finalRes
                console.log(finalRes)

            } catch (error) {
                console.log(error)
            }
        },
        async getLikeCount() {
            try {
                this.episodeId = this.$route.params.episodeId;
                const res = await fetch(`http://localhost:8080/Episodes/likes/${this.episodeId}`, {
                    method: 'GET'
                })
                const finalRes = await res.json();
                this.likeCount = finalRes
                console.log(finalRes)

            } catch (error) {
                console.log(error)
            }

        },
        async addView() {
            try {
                this.episodeId = this.$route.params.episodeId;
                const res = await fetch(`http://localhost:8080/Episodes/${this.episodeId}/view`, {
                    method: 'PUT'
                })
                const finalRes = await res.json();
                this.likeCount = finalRes
                console.log(finalRes)

            } catch (error) {
                console.log(error)
            }

        },
        async likeEpisode(episodeId) {
            this.error = null

            try {
                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const response = await fetch(`http://localhost:8080/Episodes/like/${userId}/${episodeId}`, {
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
        console.log(`Im the Episodes component!`);

        this.getData();
        this.getLikeCount();
        this.addView();
    }
}
</script>

<template>
    <div>

        <h2><strong>episodeTitle: </strong>{{ episodes.episodeTitle }}</h2>
        <video :src="episodes.video" controls class="video"></video>
        <button @click="likeEpisode(episodes.id)">like</button>
        <h2><strong>likeCount: </strong>{{ likeCount }}</h2>
        <h2><strong>viewCount: </strong>{{ episodes.viewCount }}</h2>


    </div>
</template>
<style>
.video {
    width: 100%;
}
</style>