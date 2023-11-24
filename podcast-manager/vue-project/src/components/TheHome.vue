<script>
export default {
  data() {
    return {
      episodes: []
    }
  },
  methods: {

    async getData() {
      try {
        const res = await fetch(`http://localhost:8080/Episodes/Trending`, {
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
  <div>
    <ul v-if="episodes.length">
      <li v-for="episode in episodes" :key="episode.id">
        <h2><strong>channel: </strong>{{ episode.podcast.channel }}</h2>
        <h2><strong>episodeTitle: </strong>{{ episode.episodeTitle }}</h2>
        <img :src="episode.thumbnail"><br>
        <h2><strong>views: </strong>{{ episode.viewCount }}</h2>

        <button @click="goToEpisodesPage(episode.podcast.id, episode.id)">Go to Episode</button>

      </li>
    </ul>
  </div>
</template>
