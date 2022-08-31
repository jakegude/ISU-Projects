import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { IndustryPanelResource } from './industry-panel-resource.sevice';
import { IndustryPanel } from '../models/industryPanelModel';

@Injectable({ providedIn: 'root' })
export class StudentService {

    constructor(private industryPanelResource: IndustryPanelResource) {
        
    }

    getAllIndustryPanels(){
        return this.industryPanelResource.getAllIndustryPanels();
    }

    getIndustryPanel(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.industryPanelResource.getIndustryPanel(params);
    }

    getFaculty(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.industryPanelResource.getFaculty(params);
    }

    getTeam(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.industryPanelResource.getTeam(params);
    }

    createNewTeam(industryPanel: IndustryPanel) {
        const params = new HttpParams().set('industryPanel', industryPanel.toJson());

        return this.industryPanelResource.createNewIndustryPanel(params);
    }

    updateTeam(ID: string, industryPanel: IndustryPanel) {
        const params = new HttpParams().set('ID', ID).set('industryPanel', industryPanel.toJson());

        return this.industryPanelResource.updateIndustryPanel(params);
    }

    deleteTeam(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.industryPanelResource.deleteIndustryPanel(params);
    }
}
