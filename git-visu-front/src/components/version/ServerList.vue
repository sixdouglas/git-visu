<template>
  <div class="server container-fluid">
    <div class="row">
      <div class="col text-uppercase server-style">
        {{serverName}}
      </div>
    </div>
    <div v-for="(art) in serverArtifactsSorted()" v-bind:key="art.name">
      <ArtifactList v-bind:artifact-name="art.name" v-bind:artifact-deployment="art.deployment"/>
    </div>
  </div>
</template>

<script>
import ArtifactList from "./ArtifactList";
import _ from "lodash";
export default {
  name: "ServerList",
  components: {ArtifactList},
  props: {
    serverName: String,
    serverArtifacts: Array
  },
  methods: {
    serverArtifactsSorted() {
      return _.sortBy(this.serverArtifacts, ['name'])
    }
  },
}
</script>

<style scoped>
.server-style {
  font-size: 1.0rem;
  font-family: 'Fira Mono', monospace;
}
</style>