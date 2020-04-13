import environmentsApi from '../../api/environmentsApi'
import serversApi from "../../api/serversApi";

// initial state
const state = {
    all: []
};

// getters
const getters = {
    getEnvironmentServers: (state) => (environmentId) => {
        return state.all
            .find(environment => environment.id === environmentId)
            .servers;
    },
    getServerDeployments: (state) => (environmentId, serverId) => {
        return state.all
            .find(environment => environment.id === environmentId)
            .servers
            .find(server => server.id === serverId)
            .deployments
        ;
    }
};

// actions
const actions = {
    getAllEnvironments ({ commit }) {
        environmentsApi.getEnvironments().then(environments => {
            commit('setEnvironments', environments)
        })
    },
    findEnvironmentServers ({ commit }, environmentId) {
        environmentsApi.getEnvironmentServers(environmentId).then(servers => {
            commit('setEnvironmentServers', {environmentId: environmentId, servers: servers})
        })
    },
    findServerDeployments ({ commit }, deploymentData) {
        serversApi.getServerDeployments(deploymentData.serverId).then(deployments => {
            commit('setServerDeployments', {environmentId: deploymentData.environmentId, serverId: deploymentData.serverId, deployments: deployments})
        })
    }
};

// mutations
const mutations = {
    setEnvironments (state, environments) {
        environments.forEach(environment => environment.servers = []);
        state.all = environments
    },
    setEnvironmentServers (state, envServers) {
        const environment = state.all.find(environment => environment.id === envServers.environmentId);
        envServers.servers.forEach(server => server.deployments = []);
        environment.servers = {...environment.servers, servers: envServers.servers}.servers
    },
    setServerDeployments (state, serverDeployment) {
        const environment = state.all.find(environment => environment.id === serverDeployment.environmentId);
        const server = environment.servers.find(server => server.id === serverDeployment.serverId);
        server.deployments = {...server.deployments, deployments: serverDeployment.deployments}.deployments
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
};
