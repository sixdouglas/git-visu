<template>
    <div class="list-group-item list-group-item-action">
        <div class="row">
            <span class="col-sm-12 name">{{moduleName}} :: {{artifactName}}</span>
        </div>
        <div class="row">
            <span class="col-sm-6 port">{{port}}[{{profile}}]</span>
            <span class="col-sm-6 date">{{formattedDeploymentDate}}</span>
        </div>
        <div class="row">
            <span class="col-sm-12 build">{{branch}}/{{buildName}}</span>
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
    .name {
        font-size: .6rem;
        font-weight: bold;
    }
    .port, .date {
        font-size: .6rem;
    }
    .date {
        text-align: right;
    }
    .build {
        font-size: .5rem;
        text-align: center;
    }
    .list-group-item {
        padding: .2rem;
    }
</style>