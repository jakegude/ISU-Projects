import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User, AuthenticationService } from '../core/authentication/authentication.service';
import { Project } from '../models/projectModel';
import { Student } from '../models/studentModel';
import { ProjectService } from '../services/project.service';
import { StudentService } from '../services/student.service';

@Component({
  selector: 'app-instructor',
  templateUrl: './instructor.component.html',
  styleUrls: ['./instructor.component.scss']
})
export class InstructorComponent implements OnInit {

  Projects: Project[] = [];
  Students: Student[] = [];
  user!: User;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService,
    private studentService: StudentService,
    private userService: AuthenticationService,
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
  }

}
