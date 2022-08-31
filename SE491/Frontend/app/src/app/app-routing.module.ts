import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdvisorComponent } from './advisor/advisor.component';
import { ClientComponent } from './client/client.component';
import { PermissionManagerService } from './core/permission-manager/permission-manager.service';
import { FacultyPanelComponent } from './faculty-panel/faculty-panel.component';
import { HomeComponent } from './home/home.component';
import { IndustryReviewPanelComponent } from './industry-review-panel/industry-review-panel.component';
import { InstructorComponent } from './instructor/instructor.component';
import { LoginComponent } from './login/login.component';
import { ProjectProposalComponent } from './shared/project-proposal/project-proposal.component';
import { StudentComponent } from './student/student.component';
import { SystemAdminComponent } from './system-admin/system-admin.component';
import { TeamComponent } from './team/team.component';

export const routes: Routes = [
  { 
    path: "", 
    component: LoginComponent,
  },
  { 
    path: "home", 
    component: HomeComponent,
    canActivate: [PermissionManagerService]
  },
  { 
    path: "advisor", 
    component: AdvisorComponent,
    canActivate: [PermissionManagerService]
   },
  { 
    path: "student", 
    component: StudentComponent,
    canActivate: [PermissionManagerService]
  },
  { 
    path: "team", 
    component: TeamComponent,
    canActivate: [PermissionManagerService]
  },
  { 
    path: "instructor", 
    component: InstructorComponent,
    canActivate: [PermissionManagerService]
  },
  { 
    path: "faculty-panel", 
    component: FacultyPanelComponent, 
    canActivate: [PermissionManagerService] 
  },
  { 
    path: "industry-panel", 
    component: IndustryReviewPanelComponent, 
    canActivate: [PermissionManagerService] 
  },
  { 
    path: "client", 
    component: ClientComponent, 
    canActivate: [PermissionManagerService] 
  },
  { 
    path: "project-proposal", 
    component: ClientComponent, 
    canActivate: [PermissionManagerService] 
  },
  { 
    path: "sys-admin", 
    component: SystemAdminComponent, 
    canActivate: [PermissionManagerService] 
  },
  { 
    path: "project-proposal-form", 
    component: ProjectProposalComponent,
    canActivate: [PermissionManagerService] 
  },
  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
