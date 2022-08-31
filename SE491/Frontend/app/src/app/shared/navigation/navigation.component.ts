import { Component, OnInit } from '@angular/core';
import { AuthenticationService, Roles } from 'src/app/core/authentication/authentication.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  roles : String = "";
  isStudent: boolean = false;
  isClient: boolean = false;
  isAdvisor: boolean = false;
  isIRP: boolean = false;
  isFRP: boolean = false;
  isInstructor: boolean = false;
  isAdmin: boolean = false;
  isTeam: boolean = false;
  constructor(
    private authenticationService: AuthenticationService,
  ) { }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe(user => {
      this.roles = user?.roles ?? "";
      this.isAdmin = false;
      this.isAdvisor = false;
      this.isClient = false
      this.isFRP = false;
      this.isIRP = false;
      this.isInstructor = false;
      this.isStudent = false;
      this.isTeam = false;
      switch(this.roles){
        case Roles.Admin:
          this.isAdmin = true;
          break;
        case Roles.Advisor:
          this.isAdvisor = true;
          break;
        case Roles.Client:
          this.isClient = true
          break;
        case Roles.FacultyReviewPanel:
          this.isFRP = true;
          break;
        case Roles.IndustryReviewPanel:
          this.isIRP = true;
          break;
        case Roles.Instructor:
          this.isInstructor = true;
          break;
        case Roles.Student:
          this.isStudent = true;
          break;
        case Roles.Team:
          this.isTeam = true;
          break;
      }
    });
  }
}
