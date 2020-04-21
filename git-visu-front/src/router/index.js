import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/Index'
import Home from '../components/Home'
import Environments from '../components/administration/environment/Environments'
import EnvironmentEdit from '../components/administration/environment/EnvironmentEdit'
import Servers from "../components/administration/server/Servers";
import ServerEdit from "../components/administration/server/ServerEdit";
import Modules from '../components/administration/module/Modules'
import ModuleEdit from '../components/administration/module/ModuleEdit'
import Artifacts from "../components/administration/artifact/Artifacts";
import ArtifactEdit from "../components/administration/artifact/ArtifactEdit";

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'IndexRoute',
            component: Index
        },
        {
            path: '/home',
            name: 'HomeRoute',
            component: Home
        },
        {
            path: '/environments',
            name: 'EnvironmentsRoute',
            component: Environments,
        },
        {
            path: '/environments/:id/edit',
            component: EnvironmentEdit,
            props: {isNew: false},
            children: [{
                path: '',
                name: 'EnvironmentEditWithServersRoute',
                component: Servers
            }]
        },
        {
            path: '/environments/add',
            name: 'EnvironmentAddRoute',
            component: EnvironmentEdit,
            props: {isNew: true}
        },
        {
            path: '/servers/:id/edit',
            name: 'ServerEditRoute',
            component: ServerEdit,
            props: {isNew: false}
        },
        {
            path: '/servers/add',
            name: 'ServerAddRoute',
            component: ServerEdit,
            props: {isNew: true}
        },
        {
            path: '/modules',
            name: 'ModulesRoute',
            component: Modules,
        },
        {
            path: '/modules/:id/edit',
            component: ModuleEdit,
            props: {isNew: false},
            children: [{
                path: '',
                name: 'ModuleEditWithArtifactsRoute',
                component: Artifacts
            }]
        },
        {
            path: '/modules/add',
            name: 'ModuleAddRoute',
            component: ModuleEdit,
            props: {isNew: true}
        },
        {
            path: '/artifacts/:id/edit',
            name: 'ArtifactEditRoute',
            component: ArtifactEdit,
            props: {isNew: false}
        },
        {
            path: '/artifacts/add',
            name: 'ArtifactAddRoute',
            component: ArtifactEdit,
            props: {isNew: true}
        }
    ]
})