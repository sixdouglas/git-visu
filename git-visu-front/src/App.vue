<template>
    <div id="app">
        <NavBar/>
        <router-view/>
    </div>
</template>

<script>
    import NavBar from "./components/NavBar";
    import store from "./store";

    export default {
        name: 'App',
        store,
        components: {
            NavBar
        }
    }
</script>

<style>
    #app {
        -webkit-font-smoothing: antialiased;
    }
</style>
