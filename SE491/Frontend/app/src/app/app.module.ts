import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule, routes } from './app-routing.module';
import { AppComponent } from './app.component';
import { StudentComponent } from './student/student.component';
import { TeamComponent } from './team/team.component';
import { InstructorComponent } from './instructor/instructor.component';
import { AdvisorComponent } from './advisor/advisor.component';
import { FacultyPanelComponent } from './faculty-panel/faculty-panel.component';
import { IndustryReviewPanelComponent } from './industry-review-panel/industry-review-panel.component';
import { SystemAdminComponent } from './system-admin/system-admin.component';
import { ClientComponent } from './client/client.component';
import { CalendarComponent } from './shared/calendar/calendar.component';
import { NavigationComponent } from './shared/navigation/navigation.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './core/auth.interceptor';
import { ErrorInterceptor } from './core/error.interceptor';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProjectProposalComponent } from './shared/project-proposal/project-proposal.component';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  declarations: [
    AppComponent,
    StudentComponent,
    TeamComponent,
    InstructorComponent,
    AdvisorComponent,
    FacultyPanelComponent,
    IndustryReviewPanelComponent,
    SystemAdminComponent,
    ClientComponent,
    CalendarComponent,
    NavigationComponent,
    HomeComponent,
    LoginComponent,
    ProjectProposalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    ReactiveFormsModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgSelectModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    HttpClientModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
