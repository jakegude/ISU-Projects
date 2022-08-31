import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { PermissionManagerService } from './permission-manager.service';

describe('PermissionManagerService', () => {
  let service: PermissionManagerService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  const fakeActivatedRoute = {
    snapshot: { data: {  } }
  } as ActivatedRoute;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [ {provide: ActivatedRoute, useValue: fakeActivatedRoute}]
    });
    service = TestBed.inject(PermissionManagerService);

    // Inject the http service and test controller for each test
    httpClient = TestBed.get(HttpClient);
    httpTestingController = TestBed.get(HttpTestingController);
  });


  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
