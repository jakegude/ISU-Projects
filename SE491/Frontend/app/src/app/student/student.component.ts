import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from '../models/projectModel';
import { Student } from '../models/studentModel';
import { StudentService } from '../services/student.service';
import { ProjectService } from '../services/project.service';
import { AuthenticationService, User } from '../core/authentication/authentication.service';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {

  ProjetcForm: FormGroup = this.formBuilder.group({
    PreferedProjects: [[] as Project[], Validators.required],
    PreferedStudents: [[] as Student[], Validators.required],
    PreferedSkills: [[] /*as Skills[]*/, Validators.required],
  });

  Projects: Project[] = [];
  Students: Student[] = [];
  user!: User;

  constructor(
    private userService: AuthenticationService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService,
    private studentService: StudentService
  ) { }

  ngOnInit(): void {
    this.userService.currentUser.subscribe( x => {
      this.user = x;
    });
    this.projectService.getAllProjects().subscribe(info => {
      this.Projects = JSON.parse(info);
    });
    this.studentService.getAllStudents().subscribe(info => {
      this.Students = JSON.parse(info);
    });
    //TODO initalize the skills array from endpoint
  }

}
