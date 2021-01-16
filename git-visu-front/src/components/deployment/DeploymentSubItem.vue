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
            <a v-if="!snapshot" v-bind:href="githubVersionLink" target="_blank" class="col-sm-12 build">{{branch}}/{{buildName}}</a>
            <span v-if="snapshot" class="col-sm-12 build">{{branch}}/{{buildName}}</span>
        </div>
    </div>
</template>

<script>
    import moment from 'moment';

    export default {
        name: 'DeploymentSubItem',
        data() {
          return {
            moduleLink: {
              'CORE': 'gest-achats--module-core',
              'SECU-PROXY': 'gest-achats--module-secu-proxy',
              'INVOICE': 'gest-achats--module-facturation',
              'TRANSCO': 'gest-achats--module-transcodification',
              'TALEND': 'gest-achats--module-talend',
              'DWH': 'gest-achats--module-talend-dwh',
              'DECLA': 'gest-achats--module-declaratif',
              'DATA-QUALITY': 'gest-achats--module-data-quality',
              'BACKUP': '',
            }
          }
        },
        props: {
            environmentId: Number,
            serverId: Number,
            moduleName: String,
            moduleCode: String,
            artifactName: String,
            port: String,
            profile: String,
            buildName: String,
            branch: String,
            snapshot: Boolean,
            deploymentDate: String,
        },
        computed: {
            formattedDeploymentDate() {
                return moment(this.deploymentDate).format('YYYY/MM/DD HH:mm')
            },
            githubVersionLink() {
                return 'https://github.com/adeo/' + this.moduleLink[this.moduleCode] + '/releases/tag/' + this.buildName
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