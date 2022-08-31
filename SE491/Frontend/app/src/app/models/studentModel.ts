import { ResourceModel } from "./resourceModel";
import { Team } from "./teamModel";

export class Student extends ResourceModel<Student>{
    public netID!: string;
    public firstName!: string;
    public middleName!: string;
    public lastName!: string;
    public major!: string;
    public team!: Team;

    constructor(model?: Partial<Student>){
        super(model);
    }
}