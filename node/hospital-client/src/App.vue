<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <v-img
          alt="Desarrollo de Aplicaciones Cliente-Servidor"
          class="shrink mr-2"
          contain
          src="@/assets/logo.svg"
          transition="scale-transition"
          width="120"
        />
      </div>

      <v-spacer></v-spacer>
    </v-app-bar>

    <v-content>
      <ListaHopitales />
    </v-content>
  </v-app>
</template>

<script lang="ts">
import Vue from "vue";
import ListaHopitales from "@/components/ListaHopitales.vue";

export default Vue.extend({
  name: "App",

  components: {
    ListaHopitales
  },

  data: () => ({
    //
  })
});
</script>
