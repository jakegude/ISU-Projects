export abstract class ResourceModel<T> {
    public id?: number;
    public email?: string;

    constructor(model?: Partial<T>){
        if(model){
            Object.assign(this, model);
        }
    }

    public toJson(): any {
        return JSON.parse(JSON.stringify(this));
    }
}