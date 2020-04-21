<template>
    <div class="container">
        <h3>Server: {{server.name}}</h3>
        <form v-on:submit.prevent="save">
            <div class="form-group row mb-2 mt-2">
                <label for="inputName" class="col-sm-2 col-form-label">Name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputName" v-model="server.name">
                </div>
            </div>
            <div class="form-group row mb-2">
                <label for="inputFullname" class="col-sm-2 col-form-label">Fullname</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputFullname" v-model="server.fullName">
                </div>
            </div>
            <div class="form-group row mb-2 mt-2">
                <label for="inputRole" class="col-sm-2 col-form-label">Role</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputRole" v-model="server.role">
                </div>
            </div>
            <div class="row justify-content-end">
                <div class="col-sm-4 text-right">
                    <div v-on:click="save" class="btn btn-primary"><i class="mdi mdi-save"></i> Save</div>
                    &nbsp;
                    <router-link :to="{name: 'EnvironmentEditWithServersRoute', params: {id : server.environmentId }}">
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
        name: 'EnvironmentEdit',
        props: {
            isNew: Boolean
        },
        data() {
            return {
                server: {}
            }
        },
        methods: {
            save: function() {
                if (this.isNew) {
                    this.$store.dispatch('environments/addEnvironmentServer', {environmentId: this.$route.query.environmentId, server: this.server })
                        .then(() => { this.$router.push({ name: 'EnvironmentEditWithServersRoute', params: {id : this.$route.query.environmentId }}) })
                } else {
                    this.$store.dispatch('environments/updateEnvironmentServer', {environmentId: this.server.environmentId, server: this.server })
                        .then(() => { this.$router.push({ name: 'EnvironmentEditWithServersRoute', params: {id : this.server.environmentId }}) })
                }
            }
        },
        created: function(){
            if (!this.isNew){
                this.server = {...this.getServer(this.$route.params.id)};
            }
        },
        computed: {
            ...mapGetters({
                getServer: 'environments/getServer'
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
