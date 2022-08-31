import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AuthenticationService, User } from '../core/authentication/authentication.service';



@Component({ templateUrl: 'home.component.html' })
export class HomeComponent {
    loading = false;
    users!: User[];
    user!: User;

    constructor(
      private userService: AuthenticationService, 
      private router: Router,
      private state: ActivatedRoute,
      ) { }

    ngOnInit() {
        this.loading = true;
        this.userService.getAll().pipe(first()).subscribe(users => {
            this.loading = false;
            this.users = users as User[];
        });
        this.userService.currentUser.subscribe( x => {
          this.user = x;
        });
    }

    logout(): void{
      this.userService.logout();
      this.router.navigate(['/login']);
    }
}