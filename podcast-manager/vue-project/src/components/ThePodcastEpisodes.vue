<script>
export default {
    data() {
        return {
            episodes: [],
            podcastId: null
        }
    },
    methods: {

        async getData() {
            try {
                this.podcastId = this.$route.params.podcastId;
                const res = await fetch(`http://localhost:8080/Episodes/${this.podcastId}`, {
                    method: 'GET'
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
    },
    mounted() {
        //This function runs when your component is mounted
        console.log(`Im the Episodes component!`);

        this.getData();
    }
}
</script>

<template>
    <div>
        <h1>Episodes for Podcast ID: {{ podcastId }}</h1>
        <ul v-if="episodes.length">
            <li v-for="episode in episodes" :key="episode.id">
                <h2><strong>channel: </strong>{{ episode.podcast.channel }}</h2>
                <h2><strong>episodeTitle: </strong>{{ episode.episodeTitle }}</h2>
                <h2><strong>podcastName: </strong>{{ episode.podcast.podcastName }}</h2>
                <img class="thumbnail" :src="episode.thumbnail">
                <button @click="goToEpisodesPage(podcastId, episode.id)">Go to Episode</button>

            </li>
        </ul>
    </div>
</template>


<style>
.thumbnail {
    width: 50%;
}

.video {
    width: 50%;
}
</style>