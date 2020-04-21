<template>
    <tr>
        <td>{{moduleName}}</td>
        <td>{{moduleCode}}</td>
        <td class="text-right">
            <router-link :to="{name: 'ModuleEditWithArtifactsRoute', params: {id : moduleId }}"><div class="btn btn-primary btn-sm"><i class="mdi mdi-edit"></i> Edit</div></router-link>
            &nbsp;
            <div class="btn btn-primary btn-sm" v-on:click="deleteModule"><i class="mdi mdi-delete"></i> Delete</div>
        </td>
    </tr>
</template>
<script>

    export default {
        name: 'ModuleLine',
        methods: {
            deleteModule : function() {
                this.$store.dispatch('modules/deleteModule', this.moduleId)
            }
        },
        props: {
            moduleId: Number,
            moduleName: String,
            moduleCode: String
        }
    }
</script>

<style>
</style>
