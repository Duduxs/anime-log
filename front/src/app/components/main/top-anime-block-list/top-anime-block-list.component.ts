import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-anime-block-list',
  templateUrl: './top-anime-block-list.component.html',
  styleUrls: ['./top-anime-block-list.component.scss']
})
export class TopAnimeBlockListComponent implements OnInit {
  @Input() title = '';
  @Input() img = '';
  constructor() { }

  ngOnInit(): void {
  }

}
