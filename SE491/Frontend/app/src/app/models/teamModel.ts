import { Project } from "./projectModel";
import { ResourceModel } from "./resourceModel";
import { Student } from "./studentModel";

export class Team extends ResourceModel<Team>{
    public name!: string;
    public project!: Project; // This needs to reference a project
    public size!: number;
    public students!: Student[];

    constructor(model?: Partial<Team>){
        super(model);
    }
}