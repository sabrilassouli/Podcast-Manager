<script>
import Cookies from 'js-cookie'
export default {
    data() {
        return {
            podcasts: [],
            episodes: [],
            podcastName: '',
            channel: '',
            coverImage: null,
            coverImageName: '',
            episodeTitle: '',
            thumbnail: null,
            thumbnailName: '',
            video: null,
            videoName: '',
            podcastId: null,
            id: null,

        }
    },
    methods: {

        async getData() {
            try {
                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const res = await fetch(`http://localhost:8080/Podcasts/${userId}/myPodcasts`, {
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
        async getEpisodes(podcastId) {
            try {
                const res = await fetch(`http://localhost:8080/Episodes/${podcastId}`, {
                    method: 'GET'
                })
                const finalRes = await res.json();
                this.episodes = finalRes
                console.log(finalRes)

            } catch (error) {
                console.log(error)
            }
        },
        async newPodcast() {
            this.error = null

            const coverImage = `../../uploadedfiles/${this.coverImageName}`

            try {
                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const response = await fetch(`http://localhost:8080/Podcasts/${userId}/newPodcast`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `${accessToken}`
                    },
                    body: JSON.stringify({
                        podcastName: this.podcastName,
                        channel: this.channel,
                        coverImage: coverImage
                    })
                })

                if (!response.ok) {
                    const error = await response.json()
                    this.error = error.message
                } else {
                    const data = await response.json()
                    console.log(data)
                    console.log("here is the data")
                    this.uploadFile(this.coverImage)

                    window.location.reload()

                }
            } catch (error) {
                this.error = error.message
                console.log(error)
            }

        },
        async newEpisode() {
            this.error = null
            const videoPath = `../../uploadedfiles/${this.videoName}`
            const thumbnailName = `../../uploadedfiles/${this.thumbnailName}`

            try {

                const accessToken = Cookies.get('accessToken');
                const response = await fetch(`http://localhost:8080/Episodes/newEpisode`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `${accessToken}`
                    },
                    body: JSON.stringify({
                        episodeTitle: this.episodeTitle,
                        thumbnail: thumbnailName,
                        video: videoPath,
                        podcastId: this.podcastId
                    })
                })
                if (!response.ok) {
                    const error = await response.json()
                    this.error = error.message
                } else {
                    const data = await response.json()
                    console.log(data)
                    console.log("here is the episode data")
                    this.uploadFile(this.video)
                    this.uploadFile(this.thumbnail)

                    window.location.reload()
                }

            } catch (error) {
                this.error = error.message
                console.log(error)
            }

        },
        async uploadFile(file) {

            let formData = new FormData();
            formData.append('file', file)
            for (let [key, value] of formData) {
                console.log(`${key}: ${value.name}`)
            }
            try {
                const videoResponse = await fetch(`http://localhost:8080/fileupload/files`, {
                    method: 'POST',
                    body: formData
                });

                if (!videoResponse.ok) {
                    const error = await videoResponse.json();
                    console.log('file upload failed:', error);
                } else {
                    const data = await videoResponse.json();
                    console.log('file upload successful:', data);

                }
            } catch (error) {
                this.error = error.message
                console.log(error)
            }
        },
        async deletePodcast(podcastId) {
            this.error = null

            try {

                const accessToken = Cookies.get('accessToken');
                const response = await fetch(`http://localhost:8080/Podcasts/deletePodcast`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `${accessToken}`
                    },
                    body: JSON.stringify({
                        id: podcastId
                    })
                })
                window.location.reload()

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
        async deleteEpisode(EpisodeId) {
            this.error = null

            try {

                const accessToken = Cookies.get('accessToken');
                const response = await fetch(`http://localhost:8080/Episodes/deleteEpisode`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `${accessToken}`
                    },
                    body: JSON.stringify({
                        id: EpisodeId
                    })
                })
                window.location.reload()

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
        goToEpisodesPage(podcastId) {
            this.$router.push(`/podcasts/${podcastId}/episodes`);
        },
        openPodcastForm() {
            document.getElementById("podcastForm").style.display = "block";
        },
        closePodcastForm() {
            document.getElementById("podcastForm").style.display = "none";
        },
        openEpisodeForm(podcastId) {
            document.getElementById("episodeForm").style.display = "block";
            this.podcastId = podcastId;
        },
        closeEpisodeForm() {
            document.getElementById("episodeForm").style.display = "none";
        },
        openMore(podcast) {
            document.getElementById("moreForm").style.display = "block";
            this.getEpisodes(podcast.id);
            this.podcast = podcast;
        },
        closeMore() {
            document.getElementById("moreForm").style.display = "none";
        },
        handleVideoChange(event) {
            this.video = event.target.files[0];
            this.videoName = event.target.files[0].name;
        },
        handleThumbnailChange(event) {
            this.thumbnail = event.target.files[0];
            this.thumbnailName = event.target.files[0].name;
        },
        handleCoverImageChange(event) {
            this.coverImage = event.target.files[0];
            this.coverImageName = event.target.files[0].name;
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
    <div class="functions">
        <RouterLink to="/User/FavoritePodcasts">Favorite Podcasts</RouterLink>
        <RouterLink to="/User/LikedEpisodes">Liked Episodes</RouterLink>
        <RouterLink to="/User/MyPodcasts">My Podcasts</RouterLink>
    </div>

    <button class="button" @click="openPodcastForm()">make new Podcast</button>

    <div class="form-popup" id="podcastForm">
        <form @submit.prevent="newPodcast" class="formContainer">
            <h1>make a new podcast</h1>

            <label for="podcastName"><b>podcast Name: </b></label>
            <input v-model="podcastName" type="text" name="podcastName" required>
            <br>
            <label for="channel"><b>channel: </b></label>
            <input v-model="channel" type="text" name="channel" required>
            <br>
            <label for="coverImage"><b>coverImage: </b></label>
            <input type="file" name="file" @change="handleCoverImageChange($event)" accept="image/*" required>

            <button type="submit" class="btn" @click="closePodcastForm()">submit</button>
            <button type="button" class="btn cancel" @click="closePodcastForm()">Close</button>
        </form>
    </div>

    <div class="form-popup" id="episodeForm">
        <form @submit.prevent="newEpisode" class="formContainer">

            <h1>make a new </h1>

            <label for="episodeTitle"><b>episode Title: </b></label>
            <input v-model="episodeTitle" type="text" name="episodeTitle" required>
            <br>
            <label for="thumbnail"><b>Thumbnail:</b></label>
            <input type="file" name="file" @change="handleThumbnailChange($event)" accept="image/*" required>
            <br>
            <label for="video"><b>Video:</b></label>
            <input type="file" name="file" @change="handleVideoChange($event)" accept="video/*" required>


            <button type="submit" class="btn" @click="closeEpisodeForm()">submit</button>
            <button type="button" class="btn cancel" @click="closeEpisodeForm()">Close</button>

        </form>
    </div>

    <div class="form-popup" id="moreForm">
        <form class="formContainer">

            <h1>make a new episode</h1>

            <ul v-if="episodes.length">
                <li v-for="episode in episodes" :key="episode.id">
                    <h2><strong>Title: </strong>{{ episode.episodeTitle }}</h2>

                    <button class="button" @click="deleteEpisode(episode.id)">DELETE EPISODE</button><br>

                </li>
            </ul>

            <button class="button" @click="deletePodcast(podcast.id), closeMore()">DELETE PODCAST</button><br>
            <button type="button" class="button" @click="openEpisodeForm(podcast.id), closeMore()">make new Episode</button>
            <button type="button" class="btn cancel" @click="closeMore()">Close</button>

        </form>
    </div>
    <!--
            <button class="button" @click="openEpisodeForm(podcast.id), closeMore()">make new Episode</button><br>
        -->


    <div>
        <ul v-if="podcasts.length">
            <li v-for="podcast in podcasts" :key="podcast.id">
                var thisPodcast = {{ podcast.id }}
                <h2><strong>channel: </strong>{{ podcast.channel }}</h2>
                <h2><strong>podcast: </strong>{{ podcast.podcastName }}</h2>
                <img class="coverImage" :src="podcast.coverImage">
                <br>
                <!--<router-link :to="`/podcasts/${podcast.id}/episodes`">Go to Podcast Episodes</router-link>-->
                <button class="button" @click="goToEpisodesPage(podcast.id)">Go to Podcast Episodes</button><br>
                <button class="button" @click="openMore(podcast)">More</button>
            </li>
        </ul>
    </div>
</template>

<style>
.coverImage {
    width: 30%;
}


.Btn {
    display: flex;
    justify-content: left;
}


.button {
    border: none;
    border-radius: 5px;
    background-color: #00BD7E;
    color: white;
    padding: 14px 20px;
    cursor: pointer;
}

.form-popup {

    text-align: center;

    overflow: auto;
    max-height: 70vh;

    color: black;

    display: none;
    position: fixed;
    left: 45%;
    top: 20%;
    transform: translate(-50%, 5%);
    border: 3px solid #999999;
    z-index: 9;
}

.formContainer {
    max-width: 600px;
    padding: 30px;
    background-color: #fff;
}

.formContainer input[type=text],
.formContainer input[type=password] {
    width: 100%;
    padding: 15px;
    margin: 5px 0 20px 0;
    border: none;
    background: #eee;
}

.formContainer input[type=text]:focus,
.formContainer input[type=password]:focus {
    background-color: #ddd;
    outline: none;
}

.formContainer .btn {
    padding: 12px 20px;
    border: none;
    background-color: #00BD7E;
    color: #fff;
    cursor: pointer;
    width: 100%;
    margin-bottom: 15px;
    opacity: 0.8;
}

.formContainer .cancel {
    background-color: #005a3c;
}

.formContainer .btn:hover,
.button:hover {
    opacity: 1;
}
</style>