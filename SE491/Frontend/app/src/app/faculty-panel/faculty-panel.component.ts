import { Component, OnInit } from '@angular/core';
import { AuthenticationService, User } from '../core/authentication/authentication.service';

@Component({
  selector: 'app-faculty-panel',
  templateUrl: './faculty-panel.component.html',
  styleUrls: ['./faculty-panel.component.scss']
})
export class FacultyPanelComponent implements OnInit {

  user!: User;

  constructor(
    private userService: AuthenticationService,
  ) { }

  ngOnInit(): void {
    this.userService.currentUser.subscribe( x => {
      this.user = x;
    });
  }

}
