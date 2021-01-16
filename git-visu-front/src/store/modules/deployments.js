import _ from 'lodash'
import deploymentsApi from '../../api/deploymentsApi'

// initial state
const state = {
    all:[],
    columns:[],
    modules: [],
    actualDeployment: [],
    loaded: false
};

// getters
const getters = {
    isLoaded: (state) => {
        return state && state.loaded;
    },
    getActualDeployments: (state) => {
            return state.all;
    },
    getActualDeploymentsGrouped: (state) => {
            return state.actualDeployment;
    },
    getColumns: (state) => {
            return state.columns;
    },
    getModules: (state) => {
            return state.modules;
    },
};

// actions
const actions = {
    findActualEnvironments ({ state, commit }) {
        if (!getters.isLoaded(state)) {
            deploymentsApi.getActualDeployments().then(actualDeployments => {
                commit('setActualDeployments', actualDeployments)
            })
        }
    },
};

// mutations
const mutations = {
    setActualDeployments (state, actualDeployments) {
        let id = 0
        state.all = _.sortBy(
            actualDeployments
                .map(obj=> ({ ...obj, id: id++, snapshot: obj.version.indexOf('SNAPSHOT') >= 0, shortVersion: obj.version.indexOf('SNAPSHOT') >= 0 ? 'snapshot' : obj.version, compName: obj.component.replace('APP_GHA-', '').replace('-DIST', '').replace('_CORP', '') }))
            , ['moduleName', 'component', 'envWeight']
        );

        const modules = actualDeployments
            .map(item => {return {name: item.moduleName, code: item.moduleCode }})
        state.modules = _.uniqWith(modules, (o1, o2) => o1.name === o2.name);

        const environments = actualDeployments
            .map(item => {
                return {name: item.envName, weight: item.envWeight}
            })
        state.columns = _.sortBy(_.uniqWith(environments, (o1, o2) => o1.name === o2.name), ['envWeight']);

        let moduleNames = _(state.all)
            .groupBy('moduleName')
            .map((item, key) => ({
                module: key,
                lines: item
            }))
            .value()

        moduleNames
            .forEach((item) => {
                let componentNames = _(item.lines)
                    .groupBy('component')
                    .map((item, key) => ({
                        component: key,
                        lines: item
                    }))
                    .value()

                componentNames
                    .forEach((subitem) => {
                        subitem.environments = _(subitem.lines)
                            .groupBy('envName')
                            .map((item, key) => ({
                                environment: key,
                                lines: item
                            }))
                            .value()
                    })

                item.components = componentNames
            })

        state.actualDeployment = moduleNames;
        state.loaded = true;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
};
