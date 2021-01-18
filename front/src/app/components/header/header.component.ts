import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
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
  inputSearchBlur:boolean;
  isMainPage: boolean;
  selectedValue: string;

  types: Type[] = [
    {value: 'user-0', viewValue: 'Usuário'},
    {value: 'anime-1', viewValue: 'Anime'},

  ];

  constructor(private location: Location) { 

  }

  ngOnInit(): void {
    if(this.location.path() === "/main") 
        this.isMainPage = true;

  }

  blur(value: boolean){
     this.inputSearchBlur = value;
  }

}
