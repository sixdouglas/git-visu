<template>
    <div class="container-fluid">
        <div class="row mt-3">
            <div class="col-sm-12 col-md-6 col-lg-4 col-xl-3" v-for="(env) in sortedEnvironments" v-bind:key="env.id">
                <EnvironmentCard v-bind:environment-color="env.color" v-bind:environment-name="env.name" v-bind:environment-id="env.id" v-bind:environment-code="env.code"/>
            </div>
        </div>
    </div>
</template>
<script>
    import EnvironmentCard from "./EnvironmentCard";
    import {mapState} from 'vuex'
    import _ from 'lodash'

    export default {
        name: 'Home',
        computed: {
            ...mapState({
                sortedEnvironments: (state) => _.orderBy(state.environments.environments, ['weight'], ['asc'])
            })
        },
        components: {
            EnvironmentCard
        },
        created () {
            this.$store.dispatch('environments/findAllEnvironments')
        }
    }
</script>

<style>
    .row {
        margin: 0;
    }
</style>
