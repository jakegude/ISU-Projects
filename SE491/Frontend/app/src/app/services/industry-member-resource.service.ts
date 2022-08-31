import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../appSettings';

@Injectable({
    providedIn: 'root'
  })
  export class IndustryMemberResource {
    constructor(private http: HttpClient) { }

    getAllIndustryMembers() {
        return this.http.get(`${AppSettings.apiURL}/api/industryMember/all`);
    }

    getTeam(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/industryMember/team`, {params: params, responseType: 'text'})
    }

    getProject(params : HttpParams) : Observable<string> {
        return this.http.get(`${AppSettings.apiURL}/api/industryMember/project`, {params: params, responseType: 'text'})
    }

    createNewIndustryMember(params : HttpParams) : Observable<Object> {
        return this.http.post(`${AppSettings.apiURL}/api/industryMember/new`, {params: params, responseType: 'text'})
    }

    updateIndustryMember(params : HttpParams) : Observable<Object> {
        return this.http.put(`${AppSettings.apiURL}/api/industryMember/update`, {params: params, responseType: 'text'})
    }

    deleteIndustryMember(params : HttpParams) : Observable<Object> {
        return this.http.delete(`${AppSettings.apiURL}/api/industryMember/delete`, {params: params, responseType: 'text'})
    }
  }