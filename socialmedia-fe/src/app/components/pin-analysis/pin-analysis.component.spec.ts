import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PinAnalysisComponent } from './pin-analysis.component';

describe('PinAnalysisComponent', () => {
  let component: PinAnalysisComponent;
  let fixture: ComponentFixture<PinAnalysisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PinAnalysisComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PinAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
