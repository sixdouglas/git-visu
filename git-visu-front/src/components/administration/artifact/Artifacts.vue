<template>
    <div>
        <h4>Artifacts</h4>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Role</th>
                    <th scope="col" class="text-right">
                        <router-link :to="{name: 'ArtifactAddRoute', query: {moduleId : this.$route.params.id }}">
                            <div class="btn btn-primary btn-sm"><i class="mdi mdi-add"></i> Add</div>
                        </router-link>
                    </th>
                </tr>
            </thead>
            <tbody>
                <ArtifactLine v-for="(env) in getModuleArtifacts(this.$route.params.id)" v-bind:key="env.id" v-bind:artifact-name="env.name" v-bind:artifact-role="env.role" v-bind:artifact-id="env.id"/>
            </tbody>
        </table>
    </div>
</template>
<script>
    import ArtifactLine from "./ArtifactLine";
    import {mapGetters} from "vuex";

    export default {
        name: 'Artifacts',
        computed:
            mapGetters({
                getModuleArtifacts: 'modules/getModuleArtifacts',
                isModuleArtifactsLoaded: 'modules/isModuleArtifactsLoaded'
            })
        ,
        components: {
            ArtifactLine
        },
        mounted () {
            if (!this.isModuleArtifactsLoaded(this.$route.params.id)) {
                this.$store.dispatch('modules/findModuleArtifacts', this.$route.params.id);
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
