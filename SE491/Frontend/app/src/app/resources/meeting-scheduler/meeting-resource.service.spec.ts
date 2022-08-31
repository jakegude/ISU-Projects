import { TestBed } from '@angular/core/testing';

import { MeetingSchedulerService } from './meeting-resource.service';

describe('MeetingSchedulerService', () => {
  let service: MeetingSchedulerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MeetingSchedulerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
