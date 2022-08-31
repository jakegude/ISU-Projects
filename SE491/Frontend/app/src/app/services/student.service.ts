import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { StudentResource } from './student-resource.service';
import { Student } from '../models/studentModel';

@Injectable({ providedIn: 'root' })
export class StudentService {

    constructor(private studentResource: StudentResource) {
        
    }

    getAllStudents(){
        return this.studentResource.getAllStudents();
    }

    getStudent(netID: string) {
        const params = new HttpParams().set('netID', netID);

        return this.studentResource.getStudent(params);
    }

    getTeam(netID: string) {
        const params = new HttpParams().set('netID', netID);

        return this.studentResource.getTeam(params);
    }

    getProject(netID: string) {
        const params = new HttpParams().set('netID', netID);

        return this.studentResource.getProject(params);
    }

    getPreferences(netID: string) {
        const params = new HttpParams().set('netID', netID);

        return this.studentResource.getPreferences(params);
    }

    createNewStudent(student: Student) {
        const params = new HttpParams().set('student', student.toJson());

        return this.studentResource.createNewStudent(params);
    }

    updateStudent(netID: string, student: Student) {
        const params = new HttpParams().set('netID', netID).set('student', student.toJson());

        return this.studentResource.updateStudent(params);
    }

    deleteStudent(netID: string) {
        const params = new HttpParams().set('netId', netID);

        return this.studentResource.deleteStudent(params);
    }
}
