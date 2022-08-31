import { Faculty } from "./facultyModel";
import { ResourceModel } from "./resourceModel";
import { Team } from "./teamModel";

export class FacultyPanel extends ResourceModel<FacultyPanel>{
    public facultyMembers!: Faculty[];
    public teamReviewing!: Team;

    constructor(model?: Partial<FacultyPanel>){
        super(model);
    }
}