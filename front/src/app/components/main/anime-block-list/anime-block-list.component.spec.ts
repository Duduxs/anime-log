import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimeBlockListComponent } from './anime-block-list.component';

describe('AnimeBlockListComponent', () => {
  let component: AnimeBlockListComponent;
  let fixture: ComponentFixture<AnimeBlockListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimeBlockListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnimeBlockListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
