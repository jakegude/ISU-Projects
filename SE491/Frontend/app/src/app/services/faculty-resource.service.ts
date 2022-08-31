import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../appSettings';

@Injectable({
    providedIn: 'root'
  })
  export class FacultyResource {
    constructor(private http: HttpClient) { }

    getAllFaculty() {
        return this.http.get(`${AppSettings.apiURL}/api/faculty/all`);
    }

    getFaculty(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/faculty/faculty`, {params: params, responseType: 'text'})
    }

    getPanel(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/faculty/panel`, {params: params, responseType: 'text'})
    }

    getTeam(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/faculty/team`, {params: params, responseType: 'text'})
    }

    createNewFaculty(params : HttpParams) : Observable<Object> {
        return this.http.post(`${AppSettings.apiURL}/api/faculty/new`, {params: params, responseType: 'text'})
    }

    updateFaculty(params : HttpParams) : Observable<Object> {
        return this.http.put(`${AppSettings.apiURL}/api/faculty/update`, {params: params, responseType: 'text'})
    }

    deleteFaculty(params : HttpParams) : Observable<Object> {
        return this.http.delete(`${AppSettings.apiURL}/api/faculty/delete`, {params: params, responseType: 'text'})
    }
  }