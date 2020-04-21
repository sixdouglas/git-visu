<template>
    <table class="table">
        <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Role</th>
                <th scope="col" class="text-right"><router-link :to="{name: 'ServerAddRoute', query: {environmentId : this.$route.params.id }}"><div class="btn btn-primary btn-sm"><i class="mdi mdi-add"></i> Add</div></router-link></th>
            </tr>
        </thead>
        <tbody>
            <ServerLine v-for="(env) in getEnvironmentServers(this.$route.params.id)" v-bind:key="env.id" v-bind:server-name="env.name" v-bind:server-id="env.id" v-bind:server-role="env.role"/>
        </tbody>
    </table>
</template>
<script>
    import ServerLine from "./ServerLine";
    import {mapGetters} from "vuex";

    export default {
        name: 'Servers',
        computed:
            mapGetters({
                getEnvironmentServers: 'environments/getEnvironmentServers',
                isEnvironmentServersLoaded: 'environments/isEnvironmentServersLoaded'
            })
        ,
        components: {
            ServerLine
        },
        mounted () {
            if (!this.isEnvironmentServersLoaded(this.$route.params.id)) {
                this.$store.dispatch('environments/findEnvironmentServers', this.$route.params.id);
            }
        }
    }
</script>

<style scoped>
    .table thead th {
        border: none;
    }
    .table {
        margin-bottom: unset;
    }
</style>
