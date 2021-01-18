import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface DialogAnimeData {
  author: String;
  studio: String;
  imgUrl: String;
  videoUrl: String;
  sinopse: String;
  // episodes: Integer;
  englishTitle: String;
  japaneseTitle: String;
  portugueseTitle: String;
  // releaseDate: 
  // tvHour: String;
  // duration: String
  //falta por o resto e tipar

}


@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogAnimeData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
