<template>
    <div class="card mb-4">
        <div class="card-header text-center font-weight-bold" v-bind:style="{ backgroundColor: environmentColor }">
            {{environmentName}}
        </div>
        <div class="card-body">
            <div class="list-group">
                <ServerItem v-for="(server) in getSortedEnvironmentServers" v-bind:key="server.id"
                            v-bind:server-name="server.name" v-bind:server-role="server.role"
                            v-bind:environment-id="environmentId" v-bind:server-id="server.id"
                />
            </div>
        </div>
    </div>
</template>

<script>
    import ServerItem from "./ServerItem";
    import { mapGetters } from 'vuex'
    import _ from 'lodash'

    export default {
        name: 'EnvironmentCard',
        components: {ServerItem},
        props: {
            environmentName: String,
            environmentColor: String,
            environmentId: Number,
            environmentCode: String
        },
        computed: {
            getSortedEnvironmentServers(){
                return _.orderBy(this.getEnvironmentServers(this.environmentId.valueOf()), ['role'], ['asc'])
            },
            ...mapGetters({
                getEnvironmentServers: 'environments/getEnvironmentServers'
            })
        },
        mounted () {
            this.$store.dispatch('environments/findEnvironmentServers', this.environmentId.valueOf())
        }
    }
</script>

<style>
.card-body {
    padding: .5rem;
}
</style>
