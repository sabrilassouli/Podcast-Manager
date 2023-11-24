<script>
import Cookies from 'js-cookie'
export default {
    data() {
        return {
            user: []
        }
    },
    methods: {

        async getData() {
            try {
                const userId = Cookies.get('userId')
                const accessToken = Cookies.get('accessToken');
                const res = await fetch(`http://localhost:8080/Users/${userId}`, {
                    method: 'GET',
                    headers: { Authorization: `${accessToken}` }
                })
                const finalRes = await res.json();
                this.user = finalRes
                console.log(finalRes)

            } catch (error) {
                console.log(error)
            }
        },
    },
    mounted() {
        //This function runs when your component is mounted
        console.log(`Im the User component!`);

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

        <h2><strong>user Id: </strong>{{ user.id }}</h2>
        <h2><strong>you are logged in as: </strong>{{ user.username }}</h2>
        <h2><strong>your email is: </strong>{{ user.email }}</h2>

    </div>
</template>

<style>
.functions {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;

    width: 100%;
    font-size: 12px;
    text-align: center;
    margin-top: 2rem;
}

.functions a.router-link-exact-active {
    color: var(--color-text);
}

.functions a.router-link-exact-active:hover {
    background-color: transparent;
}

.functions a {
    display: inline-block;
    padding: 0 1rem;
    border-left: 1px solid var(--color-border);
}

.functions a:first-of-type {
    border: 0;
}
</style>