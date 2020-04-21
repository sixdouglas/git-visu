<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <router-link to="/" class="navbar-brand">Git Visualisation</router-link>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <router-link to="/home" class="nav-link" active-class="active">Home</router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/environments" class="nav-link" active-class="active">Environments</router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/modules" class="nav-link" active-class="active">Modules</router-link>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>
    export default {
        name: 'NavBar',
        props: {
            msg: String
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
