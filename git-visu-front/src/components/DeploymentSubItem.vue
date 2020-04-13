<template>
    <div class="list-group-item list-group-item-action">
        <div class="row">
            <span class="col-sm-12 sname">{{moduleName}} :: {{artifactName}}</span>
        </div>
        <div class="row">
            <span class="col-sm-12 srole">{{port}}[{{profile}}]</span>
        </div>
        <div class="row">
            <span class="col-sm-6 srole">{{branch}}/{{buildName}}</span>
            <span class="col-sm-6 build">{{formattedDeploymentDate}}</span>
        </div>
    </div>
</template>

<script>
    import moment from 'moment'

    export default {
        name: 'DeploymentSubItem',
        props: {
            environmentId: Number,
            serverId: Number,
            moduleName: String,
            artifactName: String,
            port: String,
            profile: String,
            buildName: String,
            branch: String,
            deploymentDate: String,
        },
        computed: {
            formattedDeploymentDate() {
                return moment(this.deploymentDate).format('YYYY/MM/DD hh:mm')
            }
        }
    }
</script>

<style scoped>
    .sname {
        font-size: .6rem;
        font-weight: bold;
    }
    .srole {
        font-size: .6rem;
    }
    .build {
        font-size: .6rem;
        text-align: right;
    }
    .list-group-item {
        padding: .2rem;
    }
</style>