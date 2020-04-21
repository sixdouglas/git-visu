import Vue from "vue";
import Vuex from "vuex";
import environments from './modules/environments';
import modules from "./modules/modules";

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
    modules: {
        environments,
        modules
    },
    strict: debug
});
