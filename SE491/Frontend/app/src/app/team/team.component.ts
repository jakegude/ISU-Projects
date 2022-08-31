import { Component, OnInit } from '@angular/core';
import { AuthenticationService, User } from '../core/authentication/authentication.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.scss']
})
export class TeamComponent implements OnInit {

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
