<template>
  <div class="environment container-fluid mb-2 color-left" v-bind:style="{ borderLeftColor: environmentColor }">
    <div class="row">
      <div class="col env-style">
        {{environmentName}}
      </div>
    </div>
    <div v-for="(srv) in environmentServersSorted()" v-bind:key="srv.name">
      <ServerList v-bind:server-name="srv.name" v-bind:server-artifacts="srv.artifacts"/>
    </div>
  </div>
</template>

<script>
import ServerList from "./ServerList";
import _ from "lodash";

export default {
  name: "EnvironmentList",
  components: {ServerList},
  props: {
    environmentColor: String,
    environmentName: String,
    environmentServers: Array
  },
  methods: {
    environmentServersSorted() {
      return _.sortBy(this.environmentServers, ['name'])
    }
  },
}
</script>

<style scoped>
.environment {
  background-color: #fff;
  background-clip: border-box;
  border: 1px solid rgba(0,0,0,.125);
  border-radius: .25rem;
}
.color-left{
  border-left-style: solid;
  border-left-width: 2px;
}
.env-style {
  font-size: 1.2rem;
  font-weight: bold;
}
</style>