import $api from "../http";

import type { UserType } from "../Models/users/UserType";


export default class ClientService {
static updateClient(id: string, data: Partial<UserType>) {
  return $api.put(`/clients/${id}`, data);
} 

}