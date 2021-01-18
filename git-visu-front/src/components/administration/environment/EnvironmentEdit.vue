<template>
    <div class="container">
        <h3>Environment: {{environment.name}}</h3>
        <form v-on:submit.prevent="save">
            <div class="form-group row mb-2 mt-2">
                <label for="inputName" class="col-sm-2 col-form-label">Name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputName" v-model="environment.name">
                </div>
            </div>
            <div class="form-group row mb-2">
                <label for="inputColor" class="col-sm-2 col-form-label">Color</label>
                <div class="col-sm-10">
                    <input type="color" class="form-control" id="inputColor" v-model="environment.color">
                </div>
            </div>
            <div class="form-group row mb-2 mt-2">
                <label for="inputCode" class="col-sm-2 col-form-label">Code</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputCode" maxlength="7" v-model="environment.code">
                </div>
            </div>
            <div class="form-group row mb-2 mt-2">
                <label for="inputRole" class="col-sm-2 col-form-label">Role</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputRole" maxlength="7" v-model="environment.role">
                </div>
            </div>
            <div class="form-group row mb-2">
                <label for="inputWeight" class="col-sm-2 col-form-label">Weight</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputWeight" v-model="environment.weight">
                </div>
            </div>
            <div class="row justify-content-end">
                <div class="col-sm-4 text-right">
                    <div v-on:click="save" class="btn btn-primary"><i class="mdi mdi-save"></i> Save</div>
                    &nbsp;
                    <router-link to="/environments">
                        <div class="btn btn-primary"><i class="mdi mdi-cancel"></i> Cancel</div>
                    </router-link>
                </div>
            </div>
        </form>
        <div class="container mt-4">
            <div class="frame">
                <router-view></router-view>
            </div>
        </div>
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
                environment: {}
            }
        },
        methods: {
            save: function() {
                if (this.isNew) {
                    this.$store.dispatch('environments/addEnvironment', this.environment)
                        .then(() => { this.$router.push({ name: 'EnvironmentsRoute'}) })
                } else {
                    this.$store.dispatch('environments/updateEnvironment', this.environment)
                        .then(() => { this.$router.push({ name: 'EnvironmentsRoute'}) })
                }
            }
        },
        created: function(){
            if (!this.isNew){
                this.environment = {...this.getEnvironment(this.$route.params.id)};
            }
        },
        computed: {
            ...mapGetters({
                getEnvironment: 'environments/getEnvironment'
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
