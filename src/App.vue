<template>
    <div id="app">
        <HeaderDiv/>
        <router-view></router-view>
        <FooterDiv/>
    </div>
</template>
<script>
    import HeaderDiv from "@/components/header";
    import FooterDiv from "@/components/footer"

    export default {
        components: {HeaderDiv, FooterDiv},
        name: "APP",
        mounted() {
        },
    }
</script>
