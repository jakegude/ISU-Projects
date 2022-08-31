import { Component, OnInit } from '@angular/core';
import { AuthenticationService, User } from '../core/authentication/authentication.service';
import { Team } from '../models/teamModel';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'advisor',
  templateUrl: './advisor.component.html',
  styleUrls: ['./advisor.component.scss']
})
export class AdvisorComponent implements OnInit {

  Teams: Team[] = [];
  user!: User;

  constructor(
    private teamService: TeamService,
    private userService: AuthenticationService,
  ) { }

  ngOnInit(): void {
    this.userService.currentUser.subscribe( x => {
      this.user = x;
    });
    this.teamService.getAllTeams().subscribe((info : any) => {
      this.Teams = JSON.parse(info);
    });
  }

}
