import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopAnimeBlockListComponent } from './top-anime-block-list.component';

describe('TopAnimeBlockListComponent', () => {
  let component: TopAnimeBlockListComponent;
  let fixture: ComponentFixture<TopAnimeBlockListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopAnimeBlockListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TopAnimeBlockListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
