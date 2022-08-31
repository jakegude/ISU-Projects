import { Component, OnInit } from '@angular/core';
import { AuthenticationService, User } from '../core/authentication/authentication.service';

@Component({
  selector: 'app-industry-review-panel',
  templateUrl: './industry-review-panel.component.html',
  styleUrls: ['./industry-review-panel.component.scss']
})
export class IndustryReviewPanelComponent implements OnInit {

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
