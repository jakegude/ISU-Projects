import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from 'src/app/models/clientModel';
import { Project } from 'src/app/models/projectModel';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-project-proposal',
  templateUrl: './project-proposal.component.html',
  styleUrls: ['./project-proposal.component.scss']
})
export class ProjectProposalComponent implements OnInit {

  AbetDescriptions =  [
    'On this project, students will need to apply knowledge of mathematics, science, and engineering.',
    'This project gives students an opportunity to design a system, component, or process to meet desired needs within realistic constraints such as economic, environmental, social, political, ethical, health and safety, manufacturability, and sustainability',
    'This project involves students from a variety of programs, i.e., CPrE, EE, SE, and CybE.',
    'This project requires students to identify, formulate, and solve engineering problems.',
    'This project gives students an opportunity to use the techniques, skills, and modent engineering tools necessary for engineering practice.'
]

  ProjetcProposalForm: FormGroup = this.formBuilder.group({
    clientName: ['', Validators.required],
    submitterName: ['', Validators.required],
    pointOfContactName: ['', Validators.required],
    pointOfContactEmail: ['', Validators.required, Validators.email],
    projectTitle: ['', Validators.required],
    projectAbstract: ['', Validators.required],
    projectDeliverables: ['', Validators.required],
    specializedResources: ['', Validators.required],
    financialResources: ['', Validators.required],
    preferredStudents: [[], Validators.required],
    preferredSkills: [[], Validators.required],
    clientInteractionTimes: [0, Validators.required],
    clientInteractionType: [0, Validators.required],
    abet1: [0, Validators.required],
    abet2: [0, Validators.required],
    abet3: [0, Validators.required],
    abet4: [0, Validators.required],
    abet5: [0, Validators.required],
  });;

  get pf() { return this.ProjetcProposalForm.controls; }

  proposalSubmitMessage = '';
  proposalAlertType = '';

  currentPreferredStudents = [] as string[];

  studentTypes = Object.values(StudentTypes);
  meetingtTypes = Object.values(MeetingTypes);
  meetingFrequency = Object.values(MeetingFrequency);

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) { }

  ngOnInit(): void {
    //TODO initalize the skills array from endpoint
  }

  onProposalFormSubmit(){
    const fcontrols = this.ProjetcProposalForm.controls;
    let cName = (fcontrols.clientName.value as String).split(' ');
    let projectToAdd = new Project({
      name: fcontrols.projectTitle.value,
      description: fcontrols.projectAbstract.value,
      client: new Client({
        firstName: cName[0],
        lastName: cName.splice(0,1).join(' '),
      }),
      skills: fcontrols.preferredSkills.value,
      meetingFrequency: fcontrols.clientInteractionTimes.value,
    });
    this.projectService.createNewProject(projectToAdd);
  }

  onPreferredStudentsChange(name: string, isChecked: boolean) {
    const fcontrol = this.ProjetcProposalForm.controls.preferredStudents;
    if (isChecked) {
      this.currentPreferredStudents.push(name);
      fcontrol.setValue(this.currentPreferredStudents);
    } else {
      this.currentPreferredStudents = this.currentPreferredStudents.filter(x => x !== name);
      fcontrol.setValue(this.currentPreferredStudents);
    }
  }

  numSequence(n: number): Array<number> {
    let tempArray = new Array();
    for(let i = 0; i < n; i++){
      tempArray.push(i);
    }
    return tempArray;
  }

}


export enum StudentTypes {
  ElectricalEngineering = 'Electrical Engineering',
  ComputerEngineering = 'Computer Engineering',
  SoftwareEngineering = 'Software Engineering',
  CyberSecurityEngineering = 'Cyber Security Engineering'
}

export enum MeetingTypes {
  InPerson = 'In Person',
  Phone = 'Over the Phone',
  Video = 'Video Conferencing',
}


export enum MeetingFrequency {
  Weekly = 'Once a week',
  Monthly = 'Once a month',
  WeeklyToMonthly = 'More than once a month',
  Semester = 'Once a semester'
}
