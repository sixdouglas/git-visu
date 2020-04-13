<template>
    <div class="card mb-4">
        <div class="card-header text-center font-weight-bold" v-bind:style="{ fontcolor: fontColorReworked, backgroundColor: environmentColor }">
            {{environmentName}}
        </div>
        <div class="card-body">
            <div class="list-group">
                <ServerItem v-for="(server) in getSortedEnvironmentServers" v-bind:key="server.id"
                            v-bind:server-name="server.name" v-bind:server-role="server.role"
                            v-bind:environment-id="environmentId" v-bind:server-id="server.id"
                />
            </div>
        </div>
    </div>
</template>

<script>
    import ServerItem from "./ServerItem";
    import { mapGetters } from 'vuex'
    import _ from 'lodash'

    export default {
        name: 'EnvironmentCard',
        components: {ServerItem},
        props: {
            environmentName: String,
            environmentColor: String,
            environmentId: Number,
            environmentCode: String
        },
        computed: {
            // https://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
            fontColorReworked(){
                const rgbColor = this.hexToRgb(this.environmentColor);
                const L = 0.2126 * this.toLinearRgb(rgbColor.r) + 0.7152 * this.toLinearRgb(rgbColor.g) + 0.0722 * this.toLinearRgb(rgbColor.b);
                if (L > (Math.sqrt(0.0525) - 0.05)) {
                    return '#000000'
                } else {
                    return '#FFFFFF'
                }
            },
            getSortedEnvironmentServers(){
                return _.orderBy(this.getEnvironmentServers(this.environmentId.valueOf()), ['name'], ['asc'])
            },
            ...mapGetters({
                getEnvironmentServers: 'environments/getEnvironmentServers'
            })
        },
        methods: {
            // https://stackoverflow.com/questions/5623838/rgb-to-hex-and-hex-to-rgb
            hexToRgb(hex) {
                const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
                return result ? {
                    r: parseInt(result[1], 16),
                    g: parseInt(result[2], 16),
                    b: parseInt(result[3], 16)
                } : null;
            },
            toLinearRgb(color){
                let c = color / 255.0;
                if (c <=0.03928) {
                    c = c / 12.92;
                } else {
                    c = ((c + 0.055) / 1.055) ^ 2.4;
                }
                return c;
            }
        },
        mounted () {
            this.$store.dispatch('environments/findEnvironmentServers', this.environmentId.valueOf())
        }
    }
</script>

<style>
.card-body {
    padding: .5rem;
}
</style>
