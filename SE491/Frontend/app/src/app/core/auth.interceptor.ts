import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService, User } from './authentication/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
        // add authorization header with token
        const currentUser = this.authenticationService.currentUserValue;
        const token = this.authenticationService.currentToken;
        if (currentUser && token) {
            request = request.clone({
                setHeaders: { 
                    Authorization: `${token}`
                }
            });
        }

        return next.handle(request);
    }
}