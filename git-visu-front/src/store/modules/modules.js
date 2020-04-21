import modulesApi from '../../api/modulesApi'
import artifactsApi from "../../api/artifactsApi";

// initial state
const state = {
    all:[],
    modules: {},
    artifacts: {},
    loaded: false
};

// getters
const getters = {
    isLoaded: (state) => {
        return state && state.loaded;
    },
    getModules: (state) => {
            return state.all.map(moduleId => state.deployments[moduleId]);
    },
    isModuleArtifactsLoaded: (state) => (moduleId) => {
        if (getters.isLoaded(state)){
            const module = state.modules[moduleId];
            return module && module.artifactsLoaded;
        }
        return false;
    },
    getModuleArtifacts: (state) => (moduleId) => {
        return state.modules[moduleId]
            .artifacts
            .map(artifactId => state.artifacts[artifactId])
    },
    getArtifact: (state) => (artifactId) => {
        return state.artifacts[artifactId]
    },
    getModule: (state) => (moduleId) => {
        return state.modules[moduleId];
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
    addModule ({ commit }, module) {
        modulesApi.addModule(module)
            .then(module => {
                commit('addNewModule', module)
            })
            .catch(e => console.log(e));
    },
    updateModule ({ commit }, module) {
        modulesApi.updateModule(module)
            .then(module => {
                commit('changeModule', module)
            })
            .catch(e => console.log(e));
    },
    deleteModule ({ commit }, moduleId) {
        modulesApi.deleteModule(moduleId)
            .then(() => {
                commit('removeModule', moduleId)
            })
            .catch(e => console.log(e));
    },

    findModuleArtifacts ({ state, commit }, moduleId) {
        if(!getters.isModuleArtifactsLoaded(state)(moduleId)) {
            modulesApi.getModuleArtifacts(moduleId).then(artifacts => {
                commit('setModuleArtifacts', {moduleId: moduleId, artifacts: artifacts})
            })
        }
    },
    addModuleArtifact ({ commit }, envArtifact) {
        artifactsApi.addModuleArtifact(envArtifact)
            .then(artifact => {
                const nextEnvArtifact = {artifact: artifact}
                commit('addNewModuleArtifact', {...envArtifact, ...nextEnvArtifact})
            })
            .catch(e => console.log(e));
    },
    updateModuleArtifact ({ commit }, envArtifact) {
        artifactsApi.updateModuleArtifact(envArtifact)
            .then(artifact => {
                const nextEnvArtifact = {artifact: artifact}
                commit('changeModuleArtifact', {...envArtifact, ...nextEnvArtifact})
            })
            .catch(e => console.log(e));
    },
    deleteModuleArtifact ({ commit }, envArtifact) {
        artifactsApi.deleteModuleArtifact(envArtifact)
            .then(() => {
                commit('removeModuleArtifact', envArtifact)
            })
            .catch(e => console.log(e));
    },
};

// mutations
const mutations = {
    setModules (state, modules) {
        let newModules = {}
        modules.forEach(module => {
            module.artifacts = [];
            module.artifactsLoaded = false;
            newModules[module.id] = module;
            state.all = [...state.all, module.id];
        });
        state.modules = newModules;
        state.loaded = true;
    },
    addNewModule(state, module) {
        let newModules = {...state.modules};
        newModules[module.id] = module;
        state.modules = newModules;
        state.all = [...state.all, module.id];
    },
    changeModule(state, changedModule) {
        state.modules[changedModule.id] = changedModule;
    },
    removeModule(state, envIdToRemove) {
        let newModules = {...state.modules};
        Object.keys(state.modules).forEach(function (key) {
            if(key.match('^'+envIdToRemove)) {
                [key, ...state.all] = state.all
            }
        });
        delete newModules[envIdToRemove];
        state.modules = newModules
    },

    setModuleArtifacts (state, envArtifacts) {
        let newArtifacts = {...state.artifacts};
        const newModule = {...state.modules[envArtifacts.moduleId]};
        envArtifacts.artifacts.forEach(artifact => {
            artifact.deployments = []
            newArtifacts[artifact.id] = artifact;
            newModule.artifacts = [...newModule.artifacts, artifact.id];
        });
        newModule.artifactsLoaded = true;
        state.modules[envArtifacts.moduleId] = newModule;

        state.artifacts = newArtifacts;
    },
    addNewModuleArtifact(state, envArtifact) {
        const module = state.modules[envArtifact.moduleId];
        module.artifacts = [...module.artifacts, envArtifact.artifact.id];

        let newArtifacts = {...state.artifacts};
        newArtifacts[envArtifact.artifact.id] = envArtifact.artifact;
        state.artifacts = newArtifacts;
    },
    changeModuleArtifact(state, envArtifact) {
        state.artifacts[envArtifact.artifact.id] = envArtifact.artifact;
    },
    removeModuleArtifact(state, envArtifact) {
        const module = state.modules[envArtifact.moduleId];
        const srvIndex = module.artifacts.findIndex(srv => srv.id === envArtifact.artifact.id);
        module.artifacts.splice(srvIndex, 1);

        Object.keys(state.artifacts).forEach(function (key) {
            if(key.match('^'+envArtifact.artifact.id)) delete state.artifacts[key];
        });
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
};
