import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/Index'
import Home from '../components/Home'
import Environments from '../components/Environments'

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Index',
            component: Index
        },
        {
            path: '/home',
            name: 'Home',
            component: Home
        },
        {
            path: '/environments',
            name: 'Environments',
            component: Environments
        },
        {
            path: '/builds',
            name: 'Builds',
            component: Environments
        }
    ]
})