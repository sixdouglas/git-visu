<template>
  <div class="container-fluid mt-3">
    <table class="myTable">
      <thead>
        <tr>
          <td colspan="2" class="border-bottom border-right"></td>
          <th class="border-top border-bottom border-right smaller text-center" v-for="(env) in getColumns" v-bind:key="env.name">{{env.name}}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(module) in getModules" v-bind:key="module.name">
          <td class="smaller text-lowercase border-left border-bottom border-right">{{module.name}}</td>
          <td>
            <table class="myTable">
              <tbody>
                <tr v-for="(component) in getComponents(module)" v-bind:key="component.name">
                  <td class="smaller text-lowercase border-bottom border-right">{{component.shortName}}</td>
                </tr>
              </tbody>
            </table>
          </td>
          <td v-for="(env) in getColumns" v-bind:key="env">
            <table class="myTable">
              <tbody>
                <tr v-for="(component) in getComponents(module)" v-bind:key="component.name">
                  <td class="smaller text-lowercase text-center text-truncate border-bottom border-right" v-for="(ver) in getVersion(module, component.name, env)" v-bind:key="ver.id">
                    <a v-if="ver.version.indexOf('SNAPSHOT') === -1" v-bind:href="githubVersionLink(module.code, ver.version) " v-bind:title="ver.version">{{ver.shortVersion}}{{'\xa0'}}</a>
                    <span v-if="ver.version.indexOf('SNAPSHOT') >= 0" v-bind:title="ver.branch">{{ver.shortVersion}}{{'\xa0'}}</span>
                  </td>
                </tr>
              </tbody>
            </table>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {mapGetters} from "vuex";
import _ from "lodash";

export default {
  data() {
    return {
      moduleLink: {
        'CORE': 'gest-achats--module-core',
        'SECU-PROXY': 'gest-achats--module-secu-proxy',
        'INVOICE': 'gest-achats--module-facturation',
        'TRANSCO': 'gest-achats--module-transcodification',
        'TALEND': 'gest-achats--module-talend',
        'DWH': 'gest-achats--module-talend-dwh',
        'DECLA': 'gest-achats--module-declaratif',
        'DATA-QUALITY': 'gest-achats--module-data-quality',
        'BACKUP': '',
      }
    }
  },
  computed: {
    ...mapGetters('deployments', ['getActualDeployments', 'getActualDeploymentsGrouped', 'getColumns', 'getModules'])
  },
  methods: {
    getComponents(module) { const components = this.getActualDeployments
        .filter(item => item.moduleName === module.name)
        .map(item => {
          return {name: item.component, shortName: item.compName}
        })
        return _.uniqWith(components, (o1, o2) => o1.name === o2.name);
    },
    getVersion(module, component, env) {
      let id = 0
      let value = this.getActualDeployments
          .filter(item => item.moduleName === module.name && item.component === component && item.envName === env.name)
          .map(obj=> ({ ...obj, id: id++ }))
      ;
      if (value === undefined || !Array.isArray(value) || value.length === 0) {
        value = [{id: 0, module: '', component: '', compName: '', environment: '', moduleCode: '', version: '', shortVersion: '', branch: ''}];
      } else {
        value = [value[0]]
      }
      if(value[0].version.indexOf("SNAPSHOT") >= 0) {
        value[0].shortVersion = 'snapshot';
      } else {
        value[0].shortVersion = value[0].version;
      }
      return value;
    },
    githubVersionLink(moduleCode, version) {
      return 'https://github.com/adeo/' + this.moduleLink[moduleCode] + '/releases/tag/' + version
    }
  },
  created () {
    this.$store.dispatch('deployments/findActualEnvironments')
  }
}
</script>
<style>
.smaller{
  font-size: .8rem;
}
.myTable {
  width: 100%;
}
.myTable TD {
  padding: 0;
  margin: 0;
}
</style>