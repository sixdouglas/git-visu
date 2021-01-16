<template>
    <div class="container-fluid">
      <ul class="nav nav-tabs mt-3">
        <li class="nav-item">
          <a class="nav-link" href="#" v-on:click="switchEnv('DEV')" v-bind:class="{active: envRole === 'DEV'}">Développement</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#" v-on:click="switchEnv('INT')" v-bind:class="{active: envRole === 'INT'}">Intégration</a>
        </li>
      </ul>
        <div class="row mt-3">
            <div class="col-sm-12 col-md-6 col-lg-4 col-xl-3" v-for="(env) in sortedEnvironments" v-bind:key="env.id">
                <EnvironmentCard v-bind:environment-color="env.color" v-bind:environment-name="env.name" v-bind:environment-id="env.id" v-bind:environment-code="env.code"/>
            </div>
        </div>
    </div>
</template>
<script>
    import EnvironmentCard from "./EnvironmentCard";
    import _ from 'lodash'
    import {mapState} from "vuex";

    export default {
        name: 'Deployments',
        data() {
          return {
            envRole: 'DEV'
          }
        },
        computed: {
          ...mapState({
            filteredEnvironments(state) {
              return _.filter(state.environments.environments, ['role', this.envRole])
            },
            sortedEnvironments() {
              return _.orderBy(this.filteredEnvironments, ['weight'], ['asc'])
            }
          })
        },
        methods: {
          switchEnv(env) {
            this.envRole = env;
          }
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
