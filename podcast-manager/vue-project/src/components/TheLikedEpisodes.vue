<script>

import Cookies from 'js-cookie'
export default {
    data() {
        return {
            episodes: []
        }
    },
    methods: {

        async getData() {
            try {

                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const res = await fetch(`http://localhost:8080/Users/${userId}/likedEpisodes`, {
                    method: 'GET',
                    headers: { Authorization: `${accessToken}` }
                })
                const finalRes = await res.json();
                this.episodes = finalRes
                console.log(finalRes)

            } catch (error) {
                console.log(error)
            }
        },
        goToEpisodesPage(podcastId, episodeId) {
            this.$router.push(`/${podcastId}/${episodeId}`);
        },
        async unlike(episodeId) {
            try {
                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const res = await fetch(`http://localhost:8080/Episodes/unlike/${userId}/${episodeId}`, {
                    method: 'POST',
                    headers: { Authorization: `${accessToken}` }
                })

                window.location.reload()
                const finalRes = await res.json();
                this.episodes = finalRes
                console.log(finalRes)
            } catch (error) {
                console.log(error)
            }
        }
    },
    mounted() {
        //This function runs when your component is mounted
        console.log(`Im the Episodes component!`);

        this.getData();
    }
}
</script>

<template>
    <div class="functions">
        <RouterLink to="/User/FavoritePodcasts">Favorite Podcasts</RouterLink>
        <RouterLink to="/User/LikedEpisodes">Liked Episodes</RouterLink>
        <RouterLink to="/User/MyPodcasts">My Podcasts</RouterLink>
    </div>
    <div>
        <ul v-if="episodes.length">
            <li v-for="episode in episodes" :key="episode.id">
                <h2><strong>channel: </strong>{{ episode.podcast.channel }}</h2>
                <h2><strong>episodeTitle: </strong>{{ episode.episodeTitle }}</h2>
                <h2><strong>podcastName: </strong>{{ episode.podcast.podcastName }}</h2>
                <img :src="episode.thumbnail"><br>
                <button @click="goToEpisodesPage(episode.podcast.id, episode.id)">Go to Episode</button>
                <button @click="unlike(episode.id)">unlike</button>

            </li>
        </ul>
    </div>
</template>

<style>
.video {
    width: 100%;
}
</style>