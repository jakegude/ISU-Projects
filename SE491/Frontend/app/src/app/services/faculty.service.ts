import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { FacultyResource } from './faculty-resource.service';
import { Faculty } from '../models/facultyModel';

@Injectable({ providedIn: 'root' })
export class StudentService {

    constructor(private projectResource: FacultyResource) {
        
    }

    getAllFaculty(){
        return this.projectResource.getAllFaculty();
    }

    getFaculty(netID: string) {
        const params = new HttpParams().set('netID', netID);

        return this.projectResource.getFaculty(params);
    }

    getPanel(netID: string) {
        const params = new HttpParams().set('netID', netID);

        return this.projectResource.getPanel(params);
    }

    getTeam(netID: string) {
        const params = new HttpParams().set('netID', netID);

        return this.projectResource.getTeam(params);
    }

    createNewFaculty(faculty: Faculty) {
        const params = new HttpParams().set('faculty', faculty.toJson());

        return this.projectResource.createNewFaculty(params);
    }

    updateFaculty(netID: string, faculty: Faculty) {
        const params = new HttpParams().set('netID', netID).set('faculty', faculty.toJson());

        return this.projectResource.updateFaculty(params);
    }

    deleteTeam(netID: string) {
        const params = new HttpParams().set('netID', netID);

        return this.projectResource.deleteFaculty(params);
    }
}
