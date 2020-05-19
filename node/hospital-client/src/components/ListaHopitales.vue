<template>
  <v-container>
    <v-row class="text-center">
      <v-col cols="12">
        <v-img
          :src="require('../assets/logo.svg')"
          class="my-3"
          contain
          height="200"
        />
      </v-col>
    </v-row>
    <v-row class="text-center">
      <v-col cols="12">
        <v-btn text color="primary" @click="onAddHospital" >Agregar Hospitar</v-btn>
      </v-col>
    </v-row>
    <v-row class="text-center">
      <v-col cols="12">
        <v-data-table
          :headers="headers"
          :items="hospitales"
          :items-per-page="5"
          class="elevation-1"
        ></v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import Hospitales from "@/rest/hospitales";
import { Hospital } from "@/rest/hospitales";

@Component
export default class HelloWorld extends Vue {
  @Prop() private name!: string;

  headers: {
    text: string;
    align?: string;
    sortable?: boolean;
    value: string;
    class: string;
  }[] = [
    {
      text: "Id",
      align: "left",
      sortable: true,
      value: "ID",
      class: "table-header-font-white"
    },
    {
      text: "Nombre",
      align: "left",
      sortable: true,
      value: "Nombre",
      class: "table-header-font-white"
    },
    {
      text: "Dirección",
      align: "left",
      sortable: true,
      value: "Direccion",
      class: "table-header-font-white"
    },
    {
      text: "# Camas",
      align: "left",
      sortable: true,
      value: "NroCamas",
      class: "table-header-font-white"
    },
    {
      text: "Email",
      align: "left",
      sortable: true,
      value: "Email",
      class: "table-header-font-white"
    }
  ];

  hospitales: Hospital[] = [];

  /**
   * Hook Method
   * Load Hospitales on created
   */
  created() {
    this.loadHospitales();
  }

  /**
   * Load hospitales using the REST API
   */
  async loadHospitales() {
    try {
      const response = await Hospitales.getRestApi().getHospitales();
      this.hospitales = response.data;
    } catch (error) {
      alert(error);
    }
  }

  onAddHospital() {
    console.log("Agregar hospital");
  }
}
</script>
