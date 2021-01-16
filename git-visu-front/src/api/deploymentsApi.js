import axios from 'axios';

export default {
    getActualDeployments() {
        const url = `${process.env.VUE_APP_BASE_URL}/deployments/actual`;
        return axios.get(url).then(response => response.data).catch(err => err || 'Unable to retrieve data');
    },
};