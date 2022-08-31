import { ResourceModel } from "./resourceModel";

export class Skills extends ResourceModel<Skills>{
    
    public pid!: number;
    public skills!: Skills[];	
    	
    constructor(model?: Partial<Skills>){
        super(model);
    }
}