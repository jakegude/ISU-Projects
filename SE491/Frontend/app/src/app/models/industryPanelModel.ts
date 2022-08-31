import { IndustryMember } from "./industryMemberModel";
import { ResourceModel } from "./resourceModel";
import { Team } from "./teamModel";

export class IndustryPanel extends ResourceModel<IndustryPanel>{
    public members!: IndustryMember[];
    public teamReviewing!: Team;

    constructor(model?: Partial<IndustryPanel>){
        super(model);
    }
}