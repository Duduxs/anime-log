import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserBlockListComponent } from './user-block-list.component';

describe('UserBlockListComponent', () => {
  let component: UserBlockListComponent;
  let fixture: ComponentFixture<UserBlockListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserBlockListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserBlockListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
