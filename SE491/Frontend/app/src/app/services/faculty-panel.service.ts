import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { FacultyPanelResource } from './faculty-panel-resource.service';
import { FacultyPanel } from '../models/facultyPanelModel';

@Injectable({ providedIn: 'root' })
export class StudentService {

    constructor(private facultyPanelResource: FacultyPanelResource) {
        
    }

    getAllFacultyPanels(){
        return this.facultyPanelResource.getAllFacultyPanels();
    }

    getPanel(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.facultyPanelResource.getPanel(params);
    }

    getFaculty(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.facultyPanelResource.getFaculty(params);
    }

    getTeam(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.facultyPanelResource.getTeam(params);
    }

    createNewFacultyPanel(facultyPanel: FacultyPanel) {
        const params = new HttpParams().set('facultyPanel', facultyPanel.toJson());

        return this.facultyPanelResource.creatNewFacultyPanel(params);
    }

    updateFacultyPanel(ID: string, facultyPanel: FacultyPanel) {
        const params = new HttpParams().set('ID', ID).set('facultyPanel', facultyPanel.toJson());

        return this.facultyPanelResource.updateFacultyPanel(params);
    }

    deleteFacultyPanel(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.facultyPanelResource.deleteFacultyPanel(params);
    }
}
