<template>
    <tr>
        <td>{{serverName}}</td>
        <td>{{serverRole}}</td>
        <td class="text-right">
            <router-link :to="{name: 'ServerEditRoute', params: {id : serverId }}">
                <div class="btn btn-primary btn-sm"><i class="mdi mdi-edit"></i> Edit</div>
            </router-link>
            &nbsp;
            <div class="btn btn-primary btn-sm" v-on:click="deleteEnvironmentServer"><i class="mdi mdi-delete"></i> Delete</div>
        </td>
    </tr>
</template>
<script>

    export default {
        name: 'ServerLine',
        methods: {
            deleteEnvironmentServer : function() {
                this.$store.dispatch('environments/deleteEnvironmentServer', {environmentId: this.$route.params.id, server: {id: this.serverId}})
            }
        },
        props: {
            serverId: Number,
            serverName: String,
            serverRole: String
        }
    }
</script>

<style>
</style>
