import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedCertifComponent } from './med-certif.component';

describe('MedCertifComponent', () => {
  let component: MedCertifComponent;
  let fixture: ComponentFixture<MedCertifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedCertifComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedCertifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
