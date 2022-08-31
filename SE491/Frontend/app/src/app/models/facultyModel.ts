import { FacultyPanel } from "./facultyPanelModel";
import { ResourceModel } from "./resourceModel";

export class Faculty extends ResourceModel<Faculty>{
    public netID!: string;
    public firstName!: string;
    public middleName!: string;
    public lastName!: string;
    public teams!: string[];
    public panel!: FacultyPanel;

    constructor(model?: Partial<Faculty>){
        super(model);
    }
}