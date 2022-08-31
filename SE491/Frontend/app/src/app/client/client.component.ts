import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService, User } from '../core/authentication/authentication.service';
import { Team } from '../models/teamModel';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.scss']
})
export class ClientComponent implements OnInit {

  Teams: Team[] = [];
  user!: User;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: AuthenticationService,
    private teamService: TeamService
  ) { }

  ngOnInit(): void {
    this.userService.currentUser.subscribe( x => {
      this.user = x;
    });
    this.teamService.getAllTeams().subscribe((info : any) => {
      this.Teams = JSON.parse(info);
    });
  }

  onProposalFormSubmit(){}

  
}
