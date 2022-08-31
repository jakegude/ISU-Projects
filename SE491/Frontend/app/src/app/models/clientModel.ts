import { Project } from "./projectModel";
import { ResourceModel } from "./resourceModel";

export class Client extends ResourceModel<Client>{
    public isIndustry!: boolean;
    public firstName!: string;
    public lastName!: string;
    public company!: string;
    public project!: Project;

    constructor(model?: Partial<Client>){
        super(model);
    }
}