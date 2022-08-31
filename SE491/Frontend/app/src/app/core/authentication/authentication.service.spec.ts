import { ComponentFixtureAutoDetect, fakeAsync, flush, TestBed } from '@angular/core/testing';

import { AuthenticationService, User } from './authentication.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing'
import { HttpClient, HttpParams } from '@angular/common/http';
import { AuthenticationResource } from '../../resources/authentication/authentication-resource.service';
import { Observable, of } from 'rxjs';

describe('AuthenticationService', () => {
  let service: AuthenticationService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let authresource: AuthenticationResource;
  const testKey = "TESTKEY-eyJpc3MiOiJodHRwOlwvXC8xMC4yOS4xNjIuMTgyXC9hcGlcL2xvZ2luIiwiaWF0IjoxNjQ1NDk0NDcyLCJleHAiOjE2NDU0OTgwNzIsIm5iZiI6MTY0NTQ5NDQ3MiwianRpIjoiUWdVa0xHQ2d4UmVuQXA5dSIsInN1YiI6MywicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.FuMce100qfkwLMKiJNNJsDskPZmSGnfk4zr3BGBgPqU";
  let mockStorage: jest.SpyInstance;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: []
    });
    service = TestBed.inject(AuthenticationService);

    // Inject the http service and test controller for each test
    httpClient = TestBed.get(HttpClient);
    httpTestingController = TestBed.get(HttpTestingController);
    authresource = TestBed.get(AuthenticationResource);

    mockStorage = jest.spyOn(window.localStorage.__proto__, 'setItem');
  });
  
  afterEach(() => {    
    jest.clearAllMocks();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
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
      
      service.login("username", "pass").subscribe(res => expect(res).toEqual(user));
      flush();
      expect(mockAuthResource).toBeCalledWith(new HttpParams().set('email', "username").set('password', "pass"));
      expect(mockAuthResource).toBeCalledTimes(1);
      expect(mockStorage).toHaveBeenCalledTimes(1);
      expect(mockStorage).toHaveBeenCalledWith('currentUser', JSON.stringify(user));
    })); 
    
    it('should handle an invalid login', fakeAsync(() => {
      let mockAuthResource = jest.spyOn(authresource, "login").mockImplementation(() => {
        return of("");
      });
      
      service.login("username", "pass").subscribe(res => expect(res).toEqual(null));
      flush();
      expect(mockAuthResource).toBeCalledWith(new HttpParams().set('email', "username").set('password', "pass"));
      expect(mockAuthResource).toBeCalledTimes(1);
      expect(mockStorage).not.toHaveBeenCalled();
    }));
  });

  describe("register", () => {
    it('should handle a valid register', fakeAsync(() => {
      let mockAuthResource = jest.spyOn(authresource, "register").mockImplementation(() => {
        return of("User created successfully");
      });
      
      service.register("name", "username", "pass").subscribe(res => expect(res).toEqual("User created successfully"));
      flush();
      expect(mockAuthResource).toBeCalledWith(new HttpParams().set('name', "name").set('email', "username").set('password', "pass"));
    })); 
    
    it('should handle an invalid register', fakeAsync(() => {
      let mockAuthResource = jest.spyOn(authresource, "register").mockImplementation(() => {
        return of("Error");
      });
      
      service.register("name", "username", "pass").subscribe(res => expect(res).toEqual("Error"));
      flush();
      expect(mockAuthResource).toBeCalledWith(new HttpParams().set('name', "name").set('email', "username").set('password', "pass"));
    }));
  });

  it('should handle logout', fakeAsync(() => {
    
    let mockStorageRemove = jest.spyOn(window.localStorage.__proto__, 'removeItem');
    
    service.logout();
    flush();
    service.currentUser.subscribe(res => expect(res).toBe(null));
    expect(mockStorageRemove).toBeCalledTimes(1);
    expect(mockStorageRemove).toHaveBeenCalledWith('currentUser');

  }));


});
