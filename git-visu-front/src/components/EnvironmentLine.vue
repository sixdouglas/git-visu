<template>
    <tr>
        <td><a href="#" class="list-group-item-action col-sm-5 sname">{{environmentName}}</a></td>
        <td><span class="col-sm-5 build">{{environmentColor}}</span></td>
        <td><span class="col-sm-2 build"><i class="mdi mdi-edit"></i></span></td>
    </tr>
</template>
<script>
    export default {
        name: 'EnvironmentLine',
        props: {
            environmentId: Number,
            environmentName: String,
            environmentColor: String
        }
    }
</script>

<style>
</style>
