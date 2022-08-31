import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService, Roles, User } from '../authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UserToken {
  token: string | null = localStorage.getItem('token');
}
@Injectable({
  providedIn: 'root'
})
export class Permissions {

  constructor(
    private authenticationService: AuthenticationService,
    private state: ActivatedRoute,
    private router: Router
  ) {}

  canActivate(id: string): boolean {
    const currentUser = this.authenticationService.currentUserValue;
    switch(id){
      case "/home":
        if (currentUser) {
          return true;
        }
        break;
      case "/student":
        if (currentUser){
          if(currentUser.roles.includes(Roles.Student)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/team":
        if (currentUser){
          if(currentUser.roles.includes(Roles.Team)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/instructor":
        if (currentUser){
          if(currentUser.roles.includes(Roles.Instructor)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/advisor":
        if (currentUser){
          if(currentUser.roles.includes(Roles.Advisor)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/client":
        if (currentUser){
          if(currentUser.roles.includes(Roles.Client)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/project-proposal-form":
        if (currentUser){
          if(currentUser.roles.includes(Roles.Client)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/industry-panel":
        if (currentUser){
          if(currentUser.roles.includes(Roles.IndustryReviewPanel)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/industry-panel":
        if (currentUser){
          if(currentUser.roles.includes(Roles.IndustryReviewPanel)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/faculty-panel":
        if (currentUser){
          if(currentUser.roles.includes(Roles.FacultyReviewPanel)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
      case "/sys-admin":
        if (currentUser){
          if(currentUser.roles.includes(Roles.Admin)) {
            return true;
          }
          this.router.navigate(['/home'], { queryParams: { returnUrl: this.state.snapshot.url } });
          return false;
        }
        break;
    }
    this.router.navigate(['/login'], { queryParams: { returnUrl: this.state.snapshot.url } });
    return false;
  }
}

@Injectable({
  providedIn: 'root'
})
export class PermissionManagerService implements CanActivate {
  constructor(private permissions: Permissions) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean|UrlTree>|Promise<boolean|UrlTree>|boolean|UrlTree {
    return this.permissions.canActivate(state.url);
  }
}