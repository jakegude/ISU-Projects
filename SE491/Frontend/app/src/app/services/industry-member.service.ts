import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { IndustryMemberResource } from './industry-member-resource.service';
import { IndustryMember } from '../models/industryMemberModel';

@Injectable({ providedIn: 'root' })
export class StudentService {

    constructor(private industryMemberResource: IndustryMemberResource) {
        
    }

    getAllIndustryMembers(){
        return this.industryMemberResource.getAllIndustryMembers();
    }

    getTeam(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.industryMemberResource.getTeam(params);
    }

    getProject(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.industryMemberResource.getProject(params);
    }

    createNewIndustryMember(industryMember: IndustryMember) {
        const params = new HttpParams().set('industryMember', industryMember.toJson());

        return this.industryMemberResource.createNewIndustryMember(params);
    }

    updateIndustryMember(ID: string, industryMember: IndustryMember) {
        const params = new HttpParams().set('ID', ID).set('industryMember', industryMember.toJson());

        return this.industryMemberResource.updateIndustryMember(params);
    }

    delelteIndustryMember(ID: string) {
        const params = new HttpParams().set('ID', ID);

        return this.industryMemberResource.deleteIndustryMember(params);
    }
}
