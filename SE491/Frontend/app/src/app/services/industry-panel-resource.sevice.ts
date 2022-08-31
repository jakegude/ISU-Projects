import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../appSettings';

@Injectable({
    providedIn: 'root'
  })
  export class IndustryPanelResource {
    constructor(private http: HttpClient) { }

    getAllIndustryPanels() {
        return this.http.get(`${AppSettings.apiURL}/api/industryPanel/all`);
    }

    getIndustryPanel(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/industryPanel/panel`, {params: params, responseType: 'text'})
    }

    getFaculty(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/industryPanel/faculty`, {params: params, responseType: 'text'})
    }

    getTeam(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/industryPanel/team`, {params: params, responseType: 'text'})
    }

    createNewIndustryPanel(params : HttpParams) : Observable<Object> {
        return this.http.post(`${AppSettings.apiURL}/api/industryPanel/new`, {params: params, responseType: 'text'})
    }

    updateIndustryPanel(params : HttpParams) : Observable<Object> {
        return this.http.put(`${AppSettings.apiURL}/api/industryPanel/update`, {params: params, responseType: 'text'})
    }

    deleteIndustryPanel(params : HttpParams) : Observable<Object> {
        return this.http.delete(`${AppSettings.apiURL}/api/industryPanel/delete`, {params: params, responseType: 'text'})
    }

  }