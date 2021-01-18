import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-anime-block-list',
  templateUrl: './anime-block-list.component.html',
  styleUrls: ['./anime-block-list.component.scss'],
})
export class AnimeBlockListComponent implements OnInit {
  @Input() title = '';
  @Input() img = '';

  constructor() {}

  ngOnInit(): void {}
}
