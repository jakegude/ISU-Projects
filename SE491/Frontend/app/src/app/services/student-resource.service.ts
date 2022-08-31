import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../appSettings';

@Injectable({
  providedIn: 'root'
})
export class StudentResource {

  constructor(private http: HttpClient) { }

   getAllStudents() {
    return this.http.get(`${AppSettings.apiURL}/api/student/all`, {responseType: 'text'});
  }

  getStudent(params : HttpParams) : Observable<string> {
    return this.http.get(`${AppSettings.apiURL}/api/student/api/student`, {params: params, responseType: 'text'});
  }

  getTeam(params : HttpParams) : Observable<string> {
      return this.http.get(`${AppSettings.apiURL}/api/student/team`, {params: params, responseType: 'text'})
  }

  getProject(params : HttpParams) : Observable<string>{
    return this.http.get(`${AppSettings.apiURL}/api/student/project`, {params: params, responseType: 'text'})
  }

  getPreferences(params : HttpParams) : Observable<string> {
    return this.http.get(`${AppSettings.apiURL}/api/student/preferences`, {params: params, responseType: 'text'})
  }

  createNewStudent(params : HttpParams) : Observable<Object> {
    return this.http.post(`${AppSettings.apiURL}/api/student/new`, {params: params, responseType: 'text'})
  }

  updateStudent(params : HttpParams) : Observable<Object> {
    return this.http.put(`${AppSettings.apiURL}/api/student/update`, {params: params, responseType: 'text'})
  }

  deleteStudent(params : HttpParams) : Observable<Object> {
    return this.http.delete(`${AppSettings.apiURL}/api/student/delete`, {params: params, responseType: 'text'})
  }

}
