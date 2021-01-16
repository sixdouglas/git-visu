import environmentsApi from '../../api/environmentsApi'
import serversApi from "../../api/serversApi";

// initial state
const state = {
    all:[],
    environments: {},
    servers: {},
    deployments: {},
    loaded: false
};

// getters
const getters = {
    isLoaded: (state) => {
        return state && state.loaded;
    },
    getEnvironments: (state) => {
            return state.all.map(environmentId => state.deployments[environmentId]);
    },
    isEnvironmentServersLoaded: (state) => (environmentId) => {
        if (getters.isLoaded(state)){
            const environment = state.environments[environmentId];
            return environment && environment.serversLoaded;
        }
        return false;
    },
    getEnvironmentServers: (state) => (environmentId) => {
        return state.environments[environmentId]
            .servers
            .map(serverId => state.servers[serverId])
    },
    getServer: (state) => (serverId) => {
        return state.servers[serverId]
    },
    getEnvironment: (state) => (environmentId) => {
        return state.environments[environmentId];
    },
    isServerDeploymentsLoaded: (state) => (serverId) => {
        return state.servers[serverId] && state.servers[serverId].deploymentsLoaded;
    },
    getServerDeployments: (state) => (serverId) => {
        return state.servers[serverId]
            .deployments
            .map(deploymentId => state.deployments[deploymentId]);
    }
};

// actions
const actions = {
    findAllEnvironments ({ state, commit }) {
        if (!getters.isLoaded(state)) {
            environmentsApi.getEnvironments().then(environments => {
                commit('setEnvironments', environments)
            })
        }
    },
    addEnvironment ({ commit }, environment) {
        environmentsApi.addEnvironment(environment)
            .then(environment => {
                commit('addNewEnvironment', environment)
            })
            .catch(e => console.log(e));
    },
    updateEnvironment ({ commit }, environment) {
        environmentsApi.updateEnvironment(environment)
            .then(environment => {
                commit('changeEnvironment', environment)
            })
            .catch(e => console.log(e));
    },
    deleteEnvironment ({ commit }, environmentId) {
        environmentsApi.deleteEnvironment(environmentId)
            .then(() => {
                commit('removeEnvironment', environmentId)
            })
            .catch(e => console.log(e));
    },

    findEnvironmentServers ({ state, commit }, environmentId) {
        if(!getters.isEnvironmentServersLoaded(state)(environmentId)) {
            environmentsApi.getEnvironmentServers(environmentId).then(servers => {
                commit('setEnvironmentServers', {environmentId: environmentId, servers: servers})
            })
        }
    },
    addEnvironmentServer ({ commit }, envServer) {
        serversApi.addEnvironmentServer(envServer)
            .then(server => {
                const nextEnvServer = {server: server}
                commit('addNewEnvironmentServer', {...envServer, ...nextEnvServer})
            })
            .catch(e => console.log(e));
    },
    updateEnvironmentServer ({ commit }, envServer) {
        serversApi.updateEnvironmentServer(envServer)
            .then(server => {
                const nextEnvServer = {server: server}
                commit('changeEnvironmentServer', {...envServer, ...nextEnvServer})
            })
            .catch(e => console.log(e));
    },
    deleteEnvironmentServer ({ commit }, envServer) {
        serversApi.deleteEnvironmentServer(envServer)
            .then(() => {
                commit('removeEnvironmentServer', envServer)
            })
            .catch(e => console.log(e));
    },

    findServerDeployments ({ commit }, deploymentData) {
        serversApi.getServerDeployments(deploymentData.serverId).then(deployments => {
            commit('setServerDeployments', {environmentId: deploymentData.environmentId, serverId: deploymentData.serverId, deployments: deployments})
        })
    },
};

// mutations
const mutations = {
    setEnvironments (state, environments) {
        let newEnvironments = {}
        environments.forEach(environment => {
            environment.servers = [];
            environment.serversLoaded = false;
            newEnvironments[environment.id] = environment;
            state.all = [...state.all, environment.id];
        });
        state.environments = newEnvironments;
        state.loaded = true;
    },
    addNewEnvironment(state, environment) {
        let newEnvironments = {...state.environments};
        newEnvironments[environment.id] = environment;
        state.environments = newEnvironments;
        state.all = [...state.all, environment.id];
    },
    changeEnvironment(state, changedEnvironment) {
        state.environments[changedEnvironment.id] = changedEnvironment;
    },
    removeEnvironment(state, envIdToRemove) {
        let newEnvironments = {...state.environments};
        Object.keys(state.environments).forEach(function (key) {
            if(key.match('^'+envIdToRemove)) {
                [key, ...state.all] = state.all
            }
        });
        delete newEnvironments[envIdToRemove];
        state.environments = newEnvironments
    },

    setEnvironmentServers (state, envServers) {
        let newServers = {...state.servers};
        const newEnvironment = {...state.environments[envServers.environmentId]};
        envServers.servers.forEach(server => {
            server.deployments = [];
            server.deploymentsLoaded = false;
            newServers[server.id] = server;
            newEnvironment.servers = [...newEnvironment.servers, server.id];
        });
        newEnvironment.serversLoaded = true;
        state.environments[envServers.environmentId] = newEnvironment;

        state.servers = newServers;
    },
    addNewEnvironmentServer(state, envServer) {
        const environment = state.environments[envServer.environmentId];
        environment.servers = [...environment.servers, envServer.server.id];

        let newServers = {...state.servers};
        newServers[envServer.server.id] = { ...envServer.server, deployments : []};
        state.servers = newServers;
    },
    changeEnvironmentServer(state, envServer) {
        state.servers[envServer.server.id] = envServer.server;
    },
    removeEnvironmentServer(state, envServer) {
        const environment = state.environments[envServer.environmentId];
        const srvIndex = environment.servers.findIndex(srv => srv === envServer.server.id);
        environment.servers.splice(srvIndex, 1);

        Object.keys(state.servers).forEach(function (key) {
            if(key.match('^'+envServer.server.id)) delete state.servers[key];
        });
    },

    setServerDeployments (state, serverDeployment) {
        let newDeployments = {...state.deployments};
        const server = state.servers[serverDeployment.serverId];
        serverDeployment.deployments.forEach(deployment => {
            let id;
            if (deployment.deploymentId === undefined || deployment.deploymentId === null) {
                id = deployment.installationId;
            } else {
                id = deployment.deploymentId;
            }
            newDeployments[id] = deployment;
            server.deployments = [...server.deployments, id];
            server.deploymentsLoaded = true;
        });
        state.deployments = newDeployments;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
};
