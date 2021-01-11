import { Component, OnInit } from '@angular/core';

interface Type {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  value = "";

  selectedValue: string;

  types: Type[] = [
    {value: 'user-0', viewValue: 'Usuário'},
    {value: 'anime-1', viewValue: 'Anime'},
  ];

  constructor() { }

  ngOnInit(): void {
  }


}
