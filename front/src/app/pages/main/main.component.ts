import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent implements OnInit {
  constructor() {
    console.log(this.animes.indexOf(this.animes[0]));
  }

  animes = [
    {
      title: 'One piece',
      img:
        'https://segredosdomundo.r7.com/wp-content/uploads/2019/07/25-animes-que-voce-nao-pode-deixar-de-assistir.jpg',
    },
    {
      title: 'Death Note',
      img: 'https://br.web.img2.acsta.net/newsv7/19/08/14/00/05/1724103.jpg',
    },
    {
      title: 'Boku no Hero',
      img:
        'https://referencianerd.com/wp-content/uploads/2019/02/xMPcW4VHuoZvwPRh1kbCwkL7uC.jpg',
    },
  ];

  users = [
    {
      login: 'Duduxs',
      img:
        'https://uploads.metropoles.com/wp-content/uploads/2019/05/02185226/bolsonaro-anime.jpg',
    },
    {
      login: 'Feeh145',
      img: 'https://playreplay.com.br/wp-content/uploads/2019/04/crunchyroll-animes-mais-vistos-abril-2019.jpg',
    },
    {
      login: 'Ronaldo',
      img:
        'https://conteudo.imguol.com.br/c/entretenimento/58/2017/05/30/pikachu-nervoso-1496159464346_v2_1191x670.png',
    },
    {
      login: 'RoubaNet',
      img:
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxjLOPY7i76CQ_JHh3YOgf53-ABbbQl8cO2Q&usqp=CAU',
    },
  ];


  ngOnInit(): void {}
}
