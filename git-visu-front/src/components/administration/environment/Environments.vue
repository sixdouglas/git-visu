<template>
    <div class="container">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Color</th>
                    <th scope="col" class="text-right"><router-link to="/environments/add"><div class="btn btn-primary btn-sm"><i class="mdi mdi-add"></i> Add</div></router-link></th>
                </tr>
            </thead>
            <tbody>
                <EnvironmentLine v-for="(env) in sortedEnvironments" v-bind:key="env.id" v-bind:environment-color="env.color" v-bind:environment-id="env.id" v-bind:environment-name="env.name"/>
            </tbody>
        </table>
    </div>
</template>
<script>
    import EnvironmentLine from "./EnvironmentLine";
    import _ from "lodash";
    import {mapState} from "vuex";

    export default {
        name: 'Environments',
        computed: mapState({
            sortedEnvironments: (state) => _.orderBy(state.environments.environments, ['weight'], ['asc'])
        }),
        components: {
            EnvironmentLine
        },
        created () {
            this.$store.dispatch('environments/findAllEnvironments')
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
