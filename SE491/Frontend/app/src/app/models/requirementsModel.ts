import { ResourceModel } from "./resourceModel";

export class Requirements extends ResourceModel<Requirements>{
    
    public pid!: number;
    public skills!: Skills[];	
    	
    constructor(model?: Partial<Requirements>){
        super(model);
    }
}

export enum Skills {
    Java = "Java",
    Laravel = "Laravel",
    SQL = "SQL",
    PHP = "PHP",
    Algorithm = "Algorithm"
}