<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <router-link to="/" class="navbar-brand">Git Visualisation</router-link>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <router-link to="/deployments" class="nav-link" active-class="active">Deployments</router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/actualDeployments" class="nav-link" active-class="active">Actual Deployments</router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/versions" class="nav-link" active-class="active">Versions</router-link>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      Administration
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <router-link to="/environments" class="nav-link" active-class="active">Environments</router-link>
                      <div class="dropdown-divider"></div>
                      <router-link to="/modules" class="nav-link" active-class="active">Modules</router-link>
                    </div>
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
