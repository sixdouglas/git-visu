import buildsApi from '../../api/buildsApi'
import modulesApi from '../../api/modulesApi'

// initial state
const state = {
    all:[],
    modules: {},
    builds: {},
    deployments: {},
    loaded: false
};

// getters
const getters = {
    isLoaded: (state) => {
        return state && state.loaded;
    },
    getModules: (state) => {
        return state.all.map(moduleId => state.modules[moduleId]);
    },
    isModuleBuildsLoaded: (state) => (moduleId) => {
        if (getters.isLoaded(state)){
            const module = state.modules[moduleId];
            return module && module.buildsLoaded;
        }
        return false;
    },
    isBuildDeploymentsLoaded: (state) => (buildId) => {
        if (getters.isLoaded(state)){
            const build = state.builds[buildId];
            return build && build.deploymentsLoaded;
        }
        return false;
    },
    getModuleBuilds: (state) => (moduleId) => {
        if (moduleId === undefined || moduleId === '')
            return []
        return state.modules[moduleId]
            .builds
            .map(buildId => state.builds[buildId])
    },
    getBuildDeployments: (state) => (buildId) => {
        if (buildId === undefined || buildId === '')
            return []
        return state.builds[buildId]
            .deployments
            .map(deploymentName => state.deployments[deploymentName])
    },
};

// actions
const actions = {
    findAllModules ({ state, commit }) {
        if (!getters.isLoaded(state)) {
            modulesApi.getModules().then(modules => {
                commit('setModules', modules)
            })
        }
    },
    findModuleBuilds ({ state, commit }, moduleId) {
        if(!getters.isModuleBuildsLoaded(state)(moduleId)) {
            buildsApi.getModuleBuilds(moduleId).then(builds => {
                commit('setModuleBuilds', {moduleId: moduleId, builds: builds})
            })
        }
    },
    findBuildDeployments ({ state, commit }, buildId) {
        if(!getters.isBuildDeploymentsLoaded(state)(buildId)) {
            buildsApi.getBuildDeployments(buildId).then(deployments => {
                commit('setBuildDeployments', {buildId: buildId, deployments: deployments})
            })
        }
    },
};

// mutations
const mutations = {
    setModules (state, modules) {
        let newModules = {}
        modules.forEach(module => {
            module.builds = [];
            module.buildsLoaded = false;
            newModules[module.id] = module;
            state.all = [...state.all, module.id];
        });
        state.modules = newModules;
        state.loaded = true;
    },
    setModuleBuilds (state, modBuilds) {
        let newBuilds = {...state.builds};
        const newModule = {...state.modules[modBuilds.moduleId]};
        modBuilds.builds.forEach(build => {
            build.deployments = [];
            build.deploymentsLoaded = false;
            newBuilds[build.id] = build;
            newModule.builds = [...newModule.builds, build.id];
        });
        newModule.buildsLoaded = true;
        state.modules[modBuilds.moduleId] = newModule;

        state.builds = newBuilds;
    },
    setBuildDeployments (state, buildDeployments) {
        let newDeployments = {...state.deployments};
        const newBuild = {...state.builds[buildDeployments.buildId]};
        buildDeployments.deployments.forEach(deployment => {
            newDeployments[deployment.name] = deployment;
            newBuild.deployments = [...newBuild.deployments, deployment.name];
        });
        newBuild.deploymentsLoaded = true;
        state.builds[buildDeployments.buildId] = newBuild;

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
