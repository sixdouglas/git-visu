<template>
    <div class="container">
        <table class="table">
            <thead>
                <tr><th>Name</th><th>Color</th><th></th></tr>
            </thead>
            <tbody>
                <EnvironmentLine v-for="(env) in environments" v-bind:key="env.id" v-bind:environment-color="env.color" v-bind:environment-id="env.id" v-bind:environment-name="env.name"/>
            </tbody>
        </table>

    </div>
</template>
<script>
    import EnvironmentLine from "./EnvironmentLine";
    import { mapState } from "vuex";

    export default {
        name: 'Environments',
        computed: mapState({
            environments: state => state.environments
        }),
        components: {
            EnvironmentLine
        },
        created () {
            this.$store.dispatch('environments/getAllEnvironments')
        }
    }
</script>

<style>
    .row {
        margin: 0;
    }
</style>
