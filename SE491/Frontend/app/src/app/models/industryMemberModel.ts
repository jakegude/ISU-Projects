import { ResourceModel } from "./resourceModel";

export class IndustryMember extends ResourceModel<IndustryMember>{
    public firstName!: string;
    public lastName!: string;

    constructor(model?: Partial<IndustryMember>){
        super(model);
    }
}