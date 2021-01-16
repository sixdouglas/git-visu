<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-4 artifact-style">{{artifactName}}</div>
      <div class="col-4 deployment-style">{{formattedDate()}}</div>
      <div class="col-2 deployment-style">{{artifactDeployment.port}}</div>
      <div class="col-2 deployment-style">{{artifactDeployment.profile}}</div>
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "ArtifactList",
  props: {
    artifactName: String,
    artifactDeployment: Object
  },
  methods: {
    formattedDate(){
      return moment.parseZone(this.artifactDeployment.dateTime).format('YYYY/MM/DD HH:mm')
    }
  },
}
</script>

<style scoped>
.artifact-style {
  font-size: .90rem;
}
.deployment-style {
  font-size: .85rem;
}
</style>