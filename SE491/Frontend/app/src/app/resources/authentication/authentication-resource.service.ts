import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppSettings } from '../../../appSettings';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationResource {

  constructor(private http: HttpClient) { }

  /**
     * Retrieves from fake backend
     * @returns 
     */
   getAll() {
    return this.http.get(`${AppSettings.apiURL}/users`);
  }

  register(params : HttpParams) : Observable<string> {
    return this.http.get(`${AppSettings.apiURL}/api/register`, {params: params, responseType: 'text'});
  }
  login(params : HttpParams) : Observable<string> {
    return this.http.get(`${AppSettings.apiURL}/api/login`, {params: params, responseType: 'text'});
  }
  logout(params : HttpParams) : Observable<string> {
    return this.http.get(`${AppSettings.apiURL}/users/logout`, {params: params, responseType: 'text'});
  }
  user(params : HttpParams) : Observable<string> {
    return this.http.get(`${AppSettings.apiURL}/users/user`, {params: params, responseType: 'text'});
  }
}