import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministComponent } from './administ.component';

describe('AdministComponent', () => {
  let component: AdministComponent;
  let fixture: ComponentFixture<AdministComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdministComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdministComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
