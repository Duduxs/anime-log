import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-block-list',
  templateUrl: './user-block-list.component.html',
  styleUrls: ['./user-block-list.component.scss']
})
export class UserBlockListComponent implements OnInit {

  @Input()login = ''
  @Input()img = ''
  
  constructor() { }

  ngOnInit(): void {
  }

}
