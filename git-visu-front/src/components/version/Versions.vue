<template>
  <div class="container">
    <form>
      <div class="form-group">
        <label for="selectModule">Choose a module:</label>
        <select class="form-control" aria-label="Select module" id="selectModule" v-model="selectedModuleId" v-on:change="fetchBuilds">
          <option disabled value="">-- Modules --</option>
          <option v-for="module in sortedModules" v-bind:key="module.id" v-bind:value="module.id">{{ module.name }}</option>
        </select>
      </div>
      <div class="form-group" v-if="isShowVersion">
        <label for="selectVersion">Choose a version:</label>
        <select class="form-control" aria-label="Select version" id="selectVersion" data-live-search="true"
                v-model="selectedVersionId" v-on:change="fetchBuildDeployments">
          <optgroup v-for="(group, name) in getModuleBuildsSortedAndGrouped(selectedModuleId)" v-bind:key="name" v-bind:label="name">
            <option v-for="module in group" v-bind:key="module.id" v-bind:value="module.id">{{ module.name }}</option>
          </optgroup>
        </select>
      </div>
    </form>
    <div>
      <div v-for="(env) in getBuildDeployments(selectedVersionId)" v-bind:key="env.name">
        <EnvironmentList v-bind:environment-color="env.color" v-bind:environment-name="env.name" v-bind:environment-servers="env.servers"/>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters, mapState} from "vuex";
import _ from "lodash";
import compareVersions from 'compare-versions';
import EnvironmentList from "./EnvironmentList";

export default {
  name: "Versions",
  components: {EnvironmentList},
  data() {
    return {
      selectedModuleId: '',
      selectedVersionId: ''
    }
  },
  computed: {
    ...mapState({
      sortedModules: (state) => _.orderBy(state.versions.modules, ['name'], ['asc'])
    }),
    ...mapGetters({
      isModuleBuildsLoaded: 'versions/isModuleBuildsLoaded',
      isBuildDeploymentsLoaded: 'versions/isBuildDeploymentsLoaded',
      getModuleBuilds: 'versions/getModuleBuilds',
      getBuildDeployments: 'versions/getBuildDeployments'
    })
  },
  methods: {
    isShowVersion() {
      return this.isModuleBuildsLoaded(this.selectedModuleId.valueOf());
    },
    fetchBuilds() {
      if (!this.isModuleBuildsLoaded(this.selectedModuleId.valueOf())){
        this.$store.dispatch('versions/findModuleBuilds', this.selectedModuleId.valueOf());
      }
    },
    getModuleBuildsSortedAndGrouped(moduleId) {
      let builds = _.sortBy(this.getModuleBuilds(moduleId), ['snapshot', 'name'])
      return {
        releases: builds.filter(value => !value.snapshot).sort((a, b) => compareVersions(a.name, b.name)).reverse(),
        snapshots: builds.filter(value => value.snapshot).reverse(),
      }
    },
    fetchBuildDeployments() {
      if (!this.isBuildDeploymentsLoaded(this.selectedVersionId.valueOf())){
        this.$store.dispatch('versions/findBuildDeployments', this.selectedVersionId.valueOf());
      }
    },
    getBuildDeploymentsSorted() {
      return _.sortBy(this.getBuildDeployments(this.selectedVersionId.valueOf()), ['weight'])
    },
  },
  created () {
    this.$store.dispatch('versions/findAllModules')
  }
}
</script>

<style scoped>
  .dropdown {
    display: inline-block;
  }
  .container {
    margin-top: 1rem;
  }
</style>