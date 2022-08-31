import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, flush, TestBed } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { User } from '../core/authentication/authentication.service';
import { AuthenticationResource } from '../resources/authentication/authentication-resource.service';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let router: Router;
  let authresource: AuthenticationResource;
  const testKey = "TESTKEY-eyJpc3MiOiJodHRwOlwvXC8xMC4yOS4xNjIuMTgyXC9hcGlcL2xvZ2luIiwiaWF0IjoxNjQ1NDk0NDcyLCJleHAiOjE2NDU0OTgwNzIsIm5iZiI6MTY0NTQ5NDQ3MiwianRpIjoiUWdVa0xHQ2d4UmVuQXA5dSIsInN1YiI6MywicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.FuMce100qfkwLMKiJNNJsDskPZmSGnfk4zr3BGBgPqU";

  let mockStorage: jest.SpyInstance;
  let mockRouter: jest.SpyInstance;

  const fakeActivatedRoute = {
    snapshot: { data: {  } }
  } as ActivatedRoute;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        LoginComponent,
      ],
      imports: [ 
        HttpClientTestingModule, 
        RouterTestingModule, 
        NgbModule,
        FormsModule,
        ReactiveFormsModule, ],
      providers: [ FormBuilder, {provide: ActivatedRoute, useValue: fakeActivatedRoute}]
    }).compileComponents();

    // Inject the http service and test controller for each test
    httpClient = TestBed.get(HttpClient);
    httpTestingController = TestBed.get(HttpTestingController);
    authresource = TestBed.get(AuthenticationResource);
    router = TestBed.get(Router);
    fixture = TestBed.createComponent(LoginComponent);
    mockStorage = jest.spyOn(window.localStorage.__proto__, 'setItem');
    mockRouter = jest.spyOn(router,'navigate').mockImplementation();
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe("login", () => {
    it('should handle a valid login', fakeAsync(() => {
      let mockAuthResource = jest.spyOn(authresource, "login").mockImplementation(() => {
        return of(testKey);
      });

      let user = {
        username: 'string',
        password: 'string',
        firstName: 'string',
        lastName: 'string',
        authdata: testKey
      } as User;

      /*let usernameBox = fixture.debugElement.query(By.css('#loginUsername'));
      usernameBox.nativeElement.value = "email";

      let passwordBox = fixture.debugElement.query(By.css('#loginPassword'));
      passwordBox.nativeElement.value = "pass";*/
      
      component.loginForm.get('username')?.setValue('email');  
      component.loginForm.get('password')?.setValue('pass');

      let submit = fixture.debugElement.query(By.css('#loginSubmit'));
      submit.nativeElement.click();

      flush();
      expect(mockAuthResource).toBeCalledWith(new HttpParams().set('email', "email").set('password', "pass"));
      expect(mockAuthResource).toBeCalledTimes(1);
      expect(mockStorage).toHaveBeenCalledTimes(1);
      expect(mockStorage).toHaveBeenCalledWith('currentUser', JSON.stringify(user));
      expect(mockRouter).toHaveBeenCalledWith(['/home']);
    })); 
    
    it('should handle an invalid login', fakeAsync(() => {

      const originalError = console.error;
      console.error = jest.fn();

      let mockAuthResource = jest.spyOn(authresource, "login").mockImplementation(() => {
        throw new Error('login error'); //Need to make this not print to console
      });

      let user = {
        username: 'string',
        password: 'string',
        firstName: 'string',
        lastName: 'string',
        authdata: testKey
      } as User;

      /*let usernameBox = fixture.debugElement.query(By.css('#loginUsername'));
      usernameBox.nativeElement.value = "email";

      let passwordBox = fixture.debugElement.query(By.css('#loginPassword'));
      passwordBox.nativeElement.value = "pass";*/
      
      component.loginForm.get('username')?.setValue('email');  
      component.loginForm.get('password')?.setValue('pass');

      let submit = fixture.debugElement.query(By.css('#loginSubmit'));
      submit.nativeElement.click();

      flush();
      expect(mockAuthResource).toBeCalledWith(new HttpParams().set('email', "email").set('password', "pass"));
      expect(mockAuthResource).toBeCalledTimes(1);
      expect(mockStorage).not.toHaveBeenCalledTimes(1);
      expect(mockStorage).not.toHaveBeenCalledWith('currentUser', JSON.stringify(user));
      expect(mockRouter).not.toHaveBeenCalledWith(['/home']);
      console.error = originalError;
    }));
  });

  describe("register", () => {
    it('should handle a valid register', fakeAsync(() => {
      let mockAuthResource = jest.spyOn(authresource, "register").mockImplementation(() => {
        return of("User created successfully");
      });

      component.registerForm.get('name')?.setValue('name');  
      component.registerForm.get('username')?.setValue('email');  
      component.registerForm.get('password')?.setValue('pass');

      let submit = fixture.debugElement.query(By.css('#registerSubmit'));
      submit.nativeElement.click();

      flush();
      expect(mockAuthResource).toBeCalledWith(new HttpParams().set('name', "name").set('email', "email").set('password', "pass"));
      expect(mockAuthResource).toBeCalledTimes(1);
    })); 
    
    it('should handle an invalid register', fakeAsync(() => {
      let mockAuthResource = jest.spyOn(authresource, "register").mockImplementation(() => {
        throw new Error('register error');
      });
      const originalError = console.error;
      console.error = jest.fn();

      component.registerForm.get('name')?.setValue('name');  
      component.registerForm.get('username')?.setValue('email');  
      component.registerForm.get('password')?.setValue('pass');

      let submit = fixture.debugElement.query(By.css('#registerSubmit'));
      submit.nativeElement.click();

      flush();
      expect(mockAuthResource).toBeCalledWith(new HttpParams().set('name', "name").set('email', "email").set('password', "pass"));
      expect(mockAuthResource).toBeCalledTimes(1);
      console.error = originalError;
    }));
  });
});
