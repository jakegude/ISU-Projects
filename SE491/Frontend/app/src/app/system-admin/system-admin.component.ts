import { Component, OnInit } from '@angular/core';
import { AuthenticationService, User } from '../core/authentication/authentication.service';

@Component({
  selector: 'app-system-admin',
  templateUrl: './system-admin.component.html',
  styleUrls: ['./system-admin.component.scss']
})
export class SystemAdminComponent implements OnInit {

  user!: User;

  constructor(
    private userService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.userService.currentUser.subscribe( x => {
      this.user = x;
    });
  }


}
