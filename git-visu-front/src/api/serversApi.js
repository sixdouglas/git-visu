import axios from 'axios';

export default {
    getServerDeployments(serverId) {
        const url = `${process.env.VUE_APP_BASE_URL}/servers/` + serverId + `/deployments`;
        return axios.get(url).then(response => response.data).catch(err => err || 'Unable to retrieve data');
    }
};