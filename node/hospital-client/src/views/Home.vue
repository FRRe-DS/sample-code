<template>
  <div class="home">
    <img alt="FRRe-UTN logo" src="../assets/logo.svg" />
    <ListaHopitales />
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

// @ is an alias to /src
import ListaHopitales from "@/components/ListaHopitales.vue";

@Component({
  components: {
    ListaHopitales
  }
})
export default class Home extends Vue {

};
</script>
