<template>
    <tr>
        <td>{{environmentName}}</td>
        <td>{{environmentColor}}</td>
        <td class="text-right">
            <router-link :to="{name: 'EnvironmentEditWithServersRoute', params: {id : environmentId }}"><div class="btn btn-primary btn-sm"><i class="mdi mdi-edit"></i> Edit</div></router-link>
            &nbsp;
            <div class="btn btn-primary btn-sm" v-on:click="deleteEnvironment"><i class="mdi mdi-delete"></i> Delete</div>
        </td>
    </tr>
</template>
<script>

    export default {
        name: 'EnvironmentLine',
        methods: {
            deleteEnvironment : function() {
                this.$store.dispatch('environments/deleteEnvironment', this.environmentId)
            }
        },
        props: {
            environmentId: Number,
            environmentName: String,
            environmentColor: String
        }
    }
</script>

<style>
</style>
