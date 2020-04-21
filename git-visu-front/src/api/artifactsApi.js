import axios from 'axios';

export default {
    getArtifactDeployments(artifactId) {
        const url = `${process.env.VUE_APP_BASE_URL}/artifacts/` + artifactId + `/deployments`;
        return axios.get(url).then(response => response.data).catch(err => err || 'Unable to retrieve data');
    },

    deleteModuleArtifact(envArtifact) {
        const url = `${process.env.VUE_APP_BASE_URL}/artifacts/` + envArtifact.artifact.id ;
        return axios.delete(url).then(response => response.data).catch(err =>  err || 'Unable to save data');
    },

    addModuleArtifact(envArtifact) {
        const url = `${process.env.VUE_APP_BASE_URL}/artifacts/`;
        return axios.post(url, {...envArtifact.artifact, moduleId: envArtifact.moduleId}, {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            }
        }).then(response => response.data).catch(err =>  err || 'Unable to save data');
    },

    updateModuleArtifact(envArtifact) {
        const url = `${process.env.VUE_APP_BASE_URL}/artifacts/` + envArtifact.artifact.id ;
        return axios.put(url, envArtifact.artifact, {
            headers: {
                "Accept": "application/json",
                "Content-type": "application/json"
            }
        }).then(response => response.data).catch(err =>  err || 'Unable to save data');
    }
};