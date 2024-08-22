import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalKPIComponent } from './global-kpi.component';

describe('GlobalKPIComponent', () => {
  let component: GlobalKPIComponent;
  let fixture: ComponentFixture<GlobalKPIComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GlobalKPIComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlobalKPIComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
