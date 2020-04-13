import axios from 'axios';

export default {
    getEnvironments() {
        const url = `${process.env.VUE_APP_BASE_URL}/environments`;
        return axios.get(url).then(response => response.data).catch(err =>  err || 'Unable to retrieve data');
    },

    getEnvironmentServers(environmentId) {
        const url = `${process.env.VUE_APP_BASE_URL}/environments/` + environmentId + `/servers`;
        return axios.get(url).then(response => response.data).catch(err =>  err || 'Unable to retrieve data');
    }
}