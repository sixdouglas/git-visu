import axios from 'axios';

export default {
    getServerDeployments(serverId) {
        const url = `${process.env.VUE_APP_BASE_URL}/servers/` + serverId + `/deployments`;
        return axios.get(url).then(response => response.data).catch(err => err || 'Unable to retrieve data');
    },

    deleteEnvironmentServer(envServer) {
        const url = `${process.env.VUE_APP_BASE_URL}/servers/` + envServer.server.id ;
        return axios.delete(url).then(response => response.data).catch(err =>  err || 'Unable to save data');
    },

    addEnvironmentServer(envServer) {
        const url = `${process.env.VUE_APP_BASE_URL}/servers/`;
        return axios.post(url, {...envServer.server, environmentId: envServer.environmentId}, {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            }
        }).then(response => response.data).catch(err =>  err || 'Unable to save data');
    },

    updateEnvironmentServer(envServer) {
        const url = `${process.env.VUE_APP_BASE_URL}/servers/` + envServer.server.id ;
        return axios.put(url, envServer.server, {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            }
        }).then(response => response.data).catch(err =>  err || 'Unable to save data');
    }
};