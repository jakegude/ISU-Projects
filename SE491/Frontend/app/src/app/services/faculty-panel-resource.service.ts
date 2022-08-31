import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../appSettings';

@Injectable({
    providedIn: 'root'
  })
  export class FacultyPanelResource {
    constructor(private http: HttpClient) { }

    getAllFacultyPanels() {
        return this.http.get(`${AppSettings.apiURL}/api/facultyPanel/all`);
    }

    getPanel(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/facultyPanel/panel`, {params: params, responseType: 'text'})
    }

    getFaculty(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/facultyPanel/faculty`, {params: params, responseType: 'text'})
    }

    getTeam(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/facultyPanel/team`, {params: params, responseType: 'text'})
    }

    creatNewFacultyPanel(params : HttpParams) : Observable<Object> {
        return this.http.post(`${AppSettings.apiURL}/api/facultyPanel/new`, {params: params, responseType: 'text'})
    }

    updateFacultyPanel(params : HttpParams) : Observable<Object> {
        return this.http.put(`${AppSettings.apiURL}/api/facultyPanel/update`, {params: params, responseType: 'text'})
    }

    deleteFacultyPanel(params : HttpParams) : Observable<Object> {
        return this.http.delete(`${AppSettings.apiURL}/api/facultyPanel/delete`, {params: params, responseType: 'text'})
    }    
  }