import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { TeamResource } from './team-resource.service';
import { Team } from '../models/teamModel';

@Injectable({ providedIn: 'root' })
export class TeamService {

    constructor(private teamResource: TeamResource) {
        
    }

    getAllTeams(){
        return this.teamResource.getAllTeams();
    }

    getTeam(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.teamResource.getTeam(params);
    }

    getProject(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.teamResource.getProject(params);
    }

    createNewTeam(team: Team) {
        const params = new HttpParams().set('team', team.toJson());

        return this.teamResource.createNewTeam(params);
    }

    updateTeam(ID: string, team: Team) {
        const params = new HttpParams().set('ID', ID).set('team', team.toJson());

        return this.teamResource.updateTeam(params);
    }

    deleteTeam(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.teamResource.deleteTeam(params);
    }
}
