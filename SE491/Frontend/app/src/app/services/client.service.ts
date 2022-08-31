import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { ClientResource } from './client-resource.service';
import { Client } from '../models/clientModel';

@Injectable({ providedIn: 'root' })
export class ClientService {

    constructor(private clientResource: ClientResource) {
        
    }

    getAllClients(){
        return this.clientResource.getAllClients();
    }

    getClient(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.clientResource.getClient(params);
    }

    getProject(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.clientResource.getProject(params);
    }

    createNewClient(client: Client) {
        const params = new HttpParams().set('client', client.toJson());

        return this.clientResource.createNewClient(params);
    }

    updateClient(ID: string, client: Client) {
        const params = new HttpParams().set('ID', ID).set('client', client.toJson());

        return this.clientResource.updateClient(params);
    }

    deleteClient(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.clientResource.deleteClient(params);
    }
}
