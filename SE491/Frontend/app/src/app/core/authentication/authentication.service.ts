import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthenticationResource } from '../../resources/authentication/authentication-resource.service';


@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject!: BehaviorSubject<any>;
    public currentUser!: Observable<any>;
    public token!: string | null;
    public roles: Roles[] = [];

    constructor(private authResource: AuthenticationResource) {
        this.token = localStorage.getItem('token');
        let cacheUser = localStorage.getItem('currentUser');
        if(cacheUser) {
            let cUser = JSON.parse(cacheUser);
            this.currentUserSubject = new BehaviorSubject(cUser);
            this.currentUser = this.currentUserSubject.asObservable();
            this.roles = cUser.roles;
        } else {
            this.currentUserSubject = new BehaviorSubject(null);
            this.currentUser = this.currentUserSubject.asObservable();
        }
        if(this.token) {
            this.token = JSON.parse(this.token);
        }
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    public get currentToken(): string | null {
        return this.token;
    }
    getAll(){
        return this.authResource.getAll();
    }

    register(name: string, username: string, password: string) {
        let params = new HttpParams().set('name', name).set('email', username).set('password', password);
        return this.authResource.register(params);
    }

    login(username: string, password: string) {
        let params = new HttpParams().set('email', username).set('password', password);

        return this.authResource.login(params)
            .pipe(
                tap(info => {
                    //console.log(info);
                    if(info){
                        // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
                        let loginResponse = JSON.parse(info) as LoginResponse;
                        localStorage.setItem('currentUser', JSON.stringify(loginResponse.user));
                        localStorage.setItem('token', JSON.stringify(loginResponse.access_token));
                        this.currentUserSubject.next(loginResponse.user);

                        console.log(localStorage.getItem('token'));
                        console.log(localStorage.getItem('currentUser'));
                        return loginResponse.user;
                    }
                    return null;
                })
            );
    }
    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null!);

        let params = new HttpParams();

        return this.authResource.user(params)
            .pipe(
                map<string, any>(key => {
                    return null;
                })
            );
    }
    user(username: string, password: string) {
        let params = new HttpParams().set('email', username).set('password', password);

        return this.authResource.user(params)
            .pipe(
                map<string, any>(key => {
                    return null;
                })
            );
    }
}

export interface LoginResponse {
    access_token: string
    expires_in: number
    token_type: string
    user: User
  }

export interface User {
    created_at: string
    email: string
    email_verified_at: null
    id: number
    name: string
    objectguid: string
    updated_at: string
    roles: Roles[]
  }

  export enum Roles {
    Student = "Student",
    Team = "Team",
    Instructor = "Instructor",
    Client = "Client",
    Advisor = "Advisor",
    FacultyReviewPanel = "FacultyReviewPanel",
    IndustryReviewPanel = "IndustryReviewPanel",
    Admin = "Admin",
  }
