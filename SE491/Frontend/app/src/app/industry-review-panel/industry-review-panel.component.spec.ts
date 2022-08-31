import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndustryReviewPanelComponent } from './industry-review-panel.component';

describe('IndustryReviewPanelComponent', () => {
  let component: IndustryReviewPanelComponent;
  let fixture: ComponentFixture<IndustryReviewPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IndustryReviewPanelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndustryReviewPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
