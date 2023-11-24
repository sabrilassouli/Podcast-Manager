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
                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const res = await fetch(`http://localhost:8080/Users/${userId}/favoritePodcasts`, {
                    method: 'GET',
                    headers: { Authorization: `${accessToken}` }
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
        async unsubscribe(podcastId) {
            try {
                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const res = await fetch(`http://localhost:8080/Podcasts/${userId}/${podcastId}/unsubscribe`, {
                    method: 'POST',                    
                    headers: { Authorization: `${accessToken}` }
                })

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
        console.log(`Im the Podcasts component!`);

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
        <ul v-if="podcasts.length">
            <li v-for="podcast in podcasts" :key="podcast.id">
                var thisPodcast = {{ podcast.id }}
                <h2><strong>channel: </strong>{{ podcast.channel }}</h2>
                <h2><strong>podcast: </strong>{{ podcast.podcastName }}</h2>
                <img class="coverImage" :src="podcast.coverImage">
                <br>
                <!--<router-link :to="`/podcasts/${podcast.id}/episodes`">Go to Podcast Episodes</router-link>-->
                <button @click="goToEpisodesPage(podcast.id)">Go to Podcast Episodes</button>
                <button @click="unsubscribe(podcast.id)">unsubscribe</button>
            </li>
        </ul>
    </div>
</template>

<style>
.coverImage {
    width: 30%;
}
</style>