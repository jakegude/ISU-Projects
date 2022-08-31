import { TestBed } from '@angular/core/testing';

import { PermissionManagerResource } from './permission-resource.service';

describe('PermissionManagerResource', () => {
  let service: PermissionManagerResource;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PermissionManagerResource);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
