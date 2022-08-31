import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../appSettings';

@Injectable({
    providedIn: 'root'
  })
  export class ClientResource {
    constructor(private http: HttpClient){ }

    getAllClients() {
        return this.http.get(`${AppSettings.apiURL}/api/client/all`);
    }

    getClient(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/client/client`, {params: params, responseType: 'text'})
    }

    getProject(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/client/project`, {params: params, responseType: 'text'})
    }

    createNewClient(params : HttpParams) : Observable<Object> {
        return this.http.post(`${AppSettings.apiURL}/api/client/new`, {params: params, responseType: 'text'})
    }

    updateClient(params : HttpParams) : Observable<Object> {
        return this.http.put(`${AppSettings.apiURL}/api/client/update`, {params: params, responseType: 'text'})
    }

    deleteClient(params : HttpParams) : Observable<Object> {
        return this.http.delete(`${AppSettings.apiURL}/api/client/delete`, {params: params, responseType: 'text'})
    }

  }