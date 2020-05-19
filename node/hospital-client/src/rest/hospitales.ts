import Axios from "@/rest/axiosInstance";
import { AxiosPromise } from "axios";

/**
 * Hospital
 */
export class Hospital {
  id: number;
  nombre: string;
  direccion: string;
  nroCamas: number;
  email: string;
  constructor(data?: any) {
    data = data || {};
    this.id = data.id;
    this.nombre = data.nombre;
    this.direccion = data.direccion;
    this.nroCamas = data.nroCamas;
    this.email = data.email;
  }
}

/**
 * REST Client Definition
 */
export interface HospitalesRestApi {
  /**
   * Devuelve la lista de hospitales
   */
  getHospitales(): AxiosPromise<Hospital[]>;
}

/**
 * REST Client Implementation
 */
class Implementation implements HospitalesRestApi {
  /**
   * Retrieves the product license for the customer: with trial or not
   */
  getHospitales(): AxiosPromise<Hospital[]> {
    return Axios.axiosInstance().get<Hospital[]>("/hospital");
  }
}

const Hospitales = {
  getRestApi(): HospitalesRestApi {
    const api = new Implementation();
    return api;
  },
};

export default Hospitales;
