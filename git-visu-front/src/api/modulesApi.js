import axios from 'axios';

export default {
    getModules() {
        const url = `${process.env.VUE_APP_BASE_URL}/modules`;
        return axios.get(url).then(response => response.data).catch(err =>  err || 'Unable to retrieve data');
    },

    getModuleArtifacts(moduleId) {
        const url = `${process.env.VUE_APP_BASE_URL}/modules/` + moduleId + `/artifacts`;
        return axios.get(url).then(response => response.data).catch(err =>  err || 'Unable to retrieve data');
    },

    deleteModule(moduleId) {
        const url = `${process.env.VUE_APP_BASE_URL}/modules/` + moduleId ;
        return axios.delete(url).then(response => response.data).catch(err =>  err || 'Unable to save data');
    },

    addModule(module) {
        const url = `${process.env.VUE_APP_BASE_URL}/modules/`;
        return axios.post(url, module, {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            }
        }).then(response => response.data).catch(err =>  err || 'Unable to save data');
    },

    updateModule(module) {
        const url = `${process.env.VUE_APP_BASE_URL}/modules/` + module.id ;
        return axios.put(url, module, {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            }
        }).then(response => response.data).catch(err =>  err || 'Unable to save data');
    }
}