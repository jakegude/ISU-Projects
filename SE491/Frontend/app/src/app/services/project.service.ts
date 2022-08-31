import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { ProjectResource } from './project-resource.service';
import { Project } from '../models/projectModel';

@Injectable({ providedIn: 'root' })
export class ProjectService {

    constructor(private projectResource: ProjectResource) {
        
    }

    getAllProjects(){
        return this.projectResource.getAllProjects();
    }

    getProject(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.projectResource.getProject(params);
    }

    getClient(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.projectResource.getClient(params);
    }

    getTeam(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.projectResource.getTeam(params);
    }

    getFaculty(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.projectResource.getFaculty(params);
    }

    createNewProject(project: Project) {
        const params = new HttpParams().set('project', project.toJson());

        return this.projectResource.creatNewProject(params);
    }

    updateProject(ID: string, project: Project) {
        const params = new HttpParams().set('ID', ID).set('project', project.toJson());

        return this.projectResource.updateProject(params);
    }

    deleteProject(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.projectResource.deleteProject(params);
    }
}
