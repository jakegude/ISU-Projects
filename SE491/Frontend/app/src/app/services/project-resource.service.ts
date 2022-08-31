import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../appSettings';

@Injectable({
    providedIn: 'root'
  })
  export class ProjectResource {
    constructor(private http: HttpClient) { }

    getAllProjects() : Observable<string>{
        return this.http.get(`${AppSettings.apiURL}/api/project/all`, {responseType: 'text'});
    }

    getProject(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/project/api/project`, {params: params, responseType: 'text'})
    }

    getClient(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/project/client`, {params: params, responseType: 'text'})
    }

    getTeam(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/project/team`, {params: params, responseType: 'text'})
    }

    getFaculty(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/project/faculty`, {params: params, responseType: 'text'})
    }

    creatNewProject(params : HttpParams) : Observable<Object> {
        return this.http.post(`${AppSettings.apiURL}/api/project/new`, {params: params, responseType: 'text'})
    }
    
    updateProject(params : HttpParams) : Observable<Object> {
        return this.http.put(`${AppSettings.apiURL}/api/project/update`, {params: params, responseType: 'text'})
    }

    deleteProject(params : HttpParams) : Observable<Object> {
        return this.http.delete(`${AppSettings.apiURL}/api/project/delete`, {params: params, responseType: 'text'})
    }
    
  }