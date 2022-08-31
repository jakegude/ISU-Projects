import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../appSettings';

@Injectable({
    providedIn: 'root'
  })
  export class TeamResource {
    constructor(private http: HttpClient) { }

    getAllTeams() {
        return this.http.get(`${AppSettings.apiURL}/api/team/all`);
    }

    getTeam(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/team/api/team`, {params: params, responseType: 'text'})
    }

    getProject(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/team/project`, {params: params, responseType: 'text'})
    }

    createNewTeam(params : HttpParams) : Observable<Object> {
        return this.http.post(`${AppSettings.apiURL}/api/team/new`, {params: params, responseType: 'text'})
    }

    updateTeam(params : HttpParams) : Observable<Object> {
        return this.http.put(`${AppSettings.apiURL}/api/team/update`, {params: params, responseType: 'text'})
    }

    deleteTeam(params : HttpParams) : Observable<Object> {
        return this.http.delete(`${AppSettings.apiURL}/api/team/delete`, {params: params, responseType: 'text'})
    }

  }