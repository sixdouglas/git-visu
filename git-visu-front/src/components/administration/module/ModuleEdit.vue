<template>
    <div class="container">
        <h3>Module: {{module.name}}</h3>
        <form v-on:submit.prevent="save">
            <div class="form-group row mb-2 mt-2">
                <label for="inputName" class="col-sm-2 col-form-label">Name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputName" v-model="module.name">
                </div>
            </div>
            <div class="form-group row mb-2 mt-2">
                <label for="inputCode" class="col-sm-2 col-form-label">Code</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputCode" maxlength="7" v-model="module.code">
                </div>
            </div>
            <div class="row justify-content-end">
                <div class="col-sm-4 text-right">
                    <div v-on:click="save" class="btn btn-primary"><i class="mdi mdi-save"></i> Save</div>
                    &nbsp;
                    <router-link to="/modules">
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
        name: 'ModuleEdit',
        props: {
            isNew: Boolean
        },
        data() {
            return {
                module: {}
            }
        },
        methods: {
            save: function() {
                if (this.isNew) {
                    this.$store.dispatch('modules/addModule', this.module)
                        .then(() => { this.$router.push({ name: 'ModulesRoute'}) })
                } else {
                    this.$store.dispatch('modules/updateModule', this.module)
                        .then(() => { this.$router.push({ name: 'ModulesRoute'}) })
                }
            }
        },
        created: function(){
            if (!this.isNew){
                this.module = {...this.getModule(this.$route.params.id)};
            }
        },
        computed: {
            ...mapGetters({
                getModule: 'modules/getModule'
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
