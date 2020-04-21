<template>
    <div class="container">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Code</th>
                    <th scope="col" class="text-right"><router-link to="/modules/add"><div class="btn btn-primary btn-sm"><i class="mdi mdi-add"></i> Add</div></router-link></th>
                </tr>
            </thead>
            <tbody>
                <ModuleLine v-for="(env) in sortedModules" v-bind:key="env.id" v-bind:module-code="env.code" v-bind:module-id="env.id" v-bind:module-name="env.name"/>
            </tbody>
        </table>
    </div>
</template>
<script>
    import ModuleLine from "./ModuleLine";
    import _ from "lodash";
    import {mapState} from "vuex";

    export default {
        name: 'Modules',
        computed: mapState({
            sortedModules: (state) => _.orderBy(state.modules.modules, ['weight'], ['asc'])
        }),
        components: {
            ModuleLine
        },
        created () {
            this.$store.dispatch('modules/findAllModules')
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
