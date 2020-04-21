<template>
    <div v-on:click="showOrHide" class="list-group-item list-group-item-action">
        <div class="row">
            <span class="col-sm-6 sname">{{serverName}}</span>
            <span class="col-sm-6 srole">{{serverRole}}</span>
        </div>
        <div class="list-group mt-2" v-if="!isHidden">
            <DeploymentSubItem v-for="(deployment) in getSortedServerDeployments" v-bind:key="deployment.deploymentId"
                               v-bind:environment-id="environmentId" v-bind:server-id="serverId"
                               v-bind:module-name="deployment.moduleName" v-bind:artifact-name="deployment.artifactName"
                               v-bind:port="deployment.port" v-bind:profile="deployment.profile"
                               v-bind:build-name="deployment.buildName" v-bind:branch="deployment.branch"
                               v-bind:deployment-date="deployment.deploymentDate"
            />
        </div>
    </div>
</template>

<script>
    import DeploymentSubItem from "./DeploymentSubItem";
    import {mapGetters} from "vuex";
    import _ from 'lodash'

    export default {
        name: 'ServerItem',
        components: {DeploymentSubItem},
        data() {
            return {
                isHidden: true,
                deploymentLoaded: false
            }
        },
        props: {
            environmentId: Number,
            serverId: Number,
            serverName: String,
            serverRole: String
        },
        computed: {
            getSortedServerDeployments(){
                return _.orderBy(this.getServerDeployments(this.serverId.valueOf()), ['moduleName', 'artifactName'], ['asc'])
            },
            ...mapGetters({
                isServerDeploymentsLoaded: 'environments/isServerDeploymentsLoaded',
                getServerDeployments: 'environments/getServerDeployments'
            })
        },
        methods: {
            showOrHide() {
                this.isHidden = !this.isHidden;
                if (!this.deploymentLoaded) {
                    this.fetchDeployment();
                    this.deploymentLoaded = true;
                }
            },
            fetchDeployment() {
                if (!this.isServerDeploymentsLoaded(this.serverId.valueOf())){
                    this.$store.dispatch('environments/findServerDeployments', {environmentId: this.environmentId.valueOf(), serverId: this.serverId.valueOf()})
                }
            }
        }
    }
</script>

<style scoped>
    .sname {
        font-size: .8rem;
        font-weight: bold;
    }
    .srole {
        font-size: .8rem;
    }
    .list-group-item {
        padding: .2rem;
    }
</style>