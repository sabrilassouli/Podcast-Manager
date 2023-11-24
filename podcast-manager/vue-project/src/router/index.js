import {
  createRouter,
  createWebHistory
} from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(
    import.meta.env.BASE_URL),
  routes: [{
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/allPodcasts',
      name: 'allPodcasts',
      component: () => import('../views/AllPodcastsView.vue')
    },
    {
      path: '/:podcastId/:episodeId',
      name: 'theEpisode',
      component: () => import('../views/TheEpisodeView.vue')
    },
    {
      path: '/podcasts/:podcastId/episodes',
      name: 'episodes',
      component: () => import('../views/AllPodcastEpisodes.vue'),
    },
    {
      path: '/Login',
      name: 'Login',
      component: () => import('../views/AllLoginView.vue')
    },
    {
      path: '/User',
      name: 'User',
      component: () => import('../views/UserView.vue')
    },
    {
      path: '/User/FavoritePodcasts',
      name: 'FavoritePodcasts',
      component: () => import('../views/FavoritePodcastsView.vue')
    },
    {
      path: '/User/MyPodcasts',
      name: 'MyPodcasts',
      component: () => import('../views/MyPodcastsView.vue')
    },
    {
      path: '/User/LikedEpisodes',
      name: 'LikedEpisodes',
      component: () => import('../views/LikedEpisodesView.vue')
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    }
  ]
})

export default router