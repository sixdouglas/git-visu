<template>
    <tr>
        <td>{{artifactName}}</td>
        <td class="text-right">
            <router-link :to="{name: 'ArtifactEditRoute', params: {id : artifactId }}">
                <div class="btn btn-primary btn-sm"><i class="mdi mdi-edit"></i> Edit</div>
            </router-link>
            &nbsp;
            <div class="btn btn-primary btn-sm" v-on:click="deleteModuleArtifact"><i class="mdi mdi-delete"></i> Delete</div>
        </td>
    </tr>
</template>
<script>

    export default {
        name: 'ArtifactLine',
        methods: {
            deleteModuleArtifact : function() {
                this.$store.dispatch('modules/deleteModuleArtifact', {moduleId: this.$route.params.id, artifact: {id: this.artifactId}})
            }
        },
        props: {
            artifactId: Number,
            artifactName: String,
            artifactRole: String
        }
    }
</script>

<style>
</style>
