import axios from 'axios';

export default {
    getModuleBuilds(moduleId) {
        const url = `${process.env.VUE_APP_BASE_URL}/modules/` + moduleId + `/builds`;
        return axios.get(url).then(response => response.data).catch(err => err || 'Unable to retrieve data');
    },
    getBuildDeployments(buildId) {
        const url = `${process.env.VUE_APP_BASE_URL}/builds/` + buildId + `/deployments`;
        return axios.get(url).then(response => response.data).catch(err => err || 'Unable to retrieve data');
    },
};