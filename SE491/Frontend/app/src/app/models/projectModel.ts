import { Client } from "./clientModel";
import { ResourceModel } from "./resourceModel";
import { Team } from "./teamModel";

export class Project extends ResourceModel<Project>{
    public name!: string;
    public facultyName!: string; // do we need this if faculyAdvisor should refernce that object?
    public description!: string;
    public team!: Team; //Needs to refernce a team 
    public client!: Client; //Needs to reference a client
    public facultyAdvisor!: number;
    public SEmax!: number;
    public EEmax!: number;
    public CSmax!: number;
    public CEmax!: number;
    public skills!: number[];
    public meetingFrequency!: string;


    constructor(model?: Partial<Project>){
        super(model);
    }
}