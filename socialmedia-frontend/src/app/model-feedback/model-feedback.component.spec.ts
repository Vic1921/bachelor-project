import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModelFeedbackComponent } from './model-feedback.component';

describe('ModelFeedbackComponent', () => {
  let component: ModelFeedbackComponent;
  let fixture: ComponentFixture<ModelFeedbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModelFeedbackComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModelFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
