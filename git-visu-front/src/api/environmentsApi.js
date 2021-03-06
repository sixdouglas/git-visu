import axios from 'axios';

export default {
    getEnvironments() {
        const url = `${process.env.VUE_APP_BASE_URL}/environments`;
        return axios.get(url).then(response => response.data).catch(err =>  err || 'Unable to retrieve data');
    },

    getEnvironmentServers(environmentId) {
        const url = `${process.env.VUE_APP_BASE_URL}/environments/` + environmentId + `/servers`;
        return axios.get(url).then(response => response.data).catch(err =>  err || 'Unable to retrieve data');
    },

    deleteEnvironment(environmentId) {
        const url = `${process.env.VUE_APP_BASE_URL}/environments/` + environmentId ;
        return axios.delete(url).then(response => response.data).catch(err =>  err || 'Unable to save data');
    },

    addEnvironment(environment) {
        const url = `${process.env.VUE_APP_BASE_URL}/environments/`;
        return axios.post(url, environment, {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            }
        }).then(response => response.data).catch(err =>  err || 'Unable to save data');
    },

    updateEnvironment(environment) {
        const url = `${process.env.VUE_APP_BASE_URL}/environments/` + environment.id ;
        return axios.put(url, environment, {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            }
        }).then(response => response.data).catch(err =>  err || 'Unable to save data');
    }
}