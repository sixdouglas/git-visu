<template>
    <div class="container">
        <h3>Artifact: {{artifact.name}}</h3>
        <form v-on:submit.prevent="save">
            <div class="form-group row mb-2 mt-2">
                <label for="inputName" class="col-sm-2 col-form-label">Name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputName" v-model="artifact.name">
                </div>
            </div>
            <div class="row justify-content-end">
                <div class="col-sm-4 text-right">
                    <div v-on:click="save" class="btn btn-primary"><i class="mdi mdi-save"></i> Save</div>
                    &nbsp;
                    <router-link :to="{name: 'ModuleEditWithArtifactsRoute', params: {id : artifact.moduleId }}">
                        <div class="btn btn-primary"><i class="mdi mdi-cancel"></i> Cancel</div>
                    </router-link>
                </div>
            </div>
        </form>
    </div>
</template>
<script>
    import {mapGetters} from "vuex";

    export default {
        name: 'ModuleEdit',
        props: {
            isNew: Boolean
        },
        data() {
            return {
                artifact: {}
            }
        },
        methods: {
            save: function() {
                if (this.isNew) {
                    this.$store.dispatch('modules/addModuleArtifact', {moduleId: this.$route.query.moduleId, artifact: this.artifact })
                        .then(() => { this.$router.push({ name: 'ModuleEditWithArtifactsRoute', params: {id : this.$route.query.moduleId }}) })
                } else {
                    this.$store.dispatch('modules/updateModuleArtifact', {moduleId: this.artifact.moduleId, artifact: this.artifact })
                        .then(() => { this.$router.push({ name: 'ModuleEditWithArtifactsRoute', params: {id : this.artifact.moduleId }}) })
                }
            }
        },
        created: function(){
            if (!this.isNew){
                this.artifact = {...this.getArtifact(this.$route.params.id)};
            }
        },
        computed: {
            ...mapGetters({
                getArtifact: 'modules/getArtifact'
            })
        }
    }
</script>

<style scoped>
    .frame {
        border: .2rem solid #f7f7f9;
        padding: .2rem;
    }
</style>
