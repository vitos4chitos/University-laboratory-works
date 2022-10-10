import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainOfficialPageComponent } from './main-official-page.component';

describe('MainOfficialPageComponent', () => {
  let component: MainOfficialPageComponent;
  let fixture: ComponentFixture<MainOfficialPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainOfficialPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainOfficialPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
