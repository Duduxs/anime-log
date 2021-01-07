import { Component, Input, OnInit, SimpleChange } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  form: FormGroup
  hide: boolean = true;
  
  @Input() login: boolean = true;

  constructor(){

  }

  onSubmit(){
    console.log(`${this.form.value.email}, ${this.form.value.login}. ${this.form.value.senha}`)
  }

  ngOnInit(): void {
    this.form = new FormGroup( !this.login ? {
      'email': new FormControl(null, Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(35), Validators.email])),
      'login': new FormControl(null, Validators.compose([Validators.required, Validators.minLength(4), Validators.maxLength(10)])),
      'senha': new FormControl(null, Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(10)])),
    }: {
      'login': new FormControl(null, Validators.compose([Validators.required, Validators.minLength(4), Validators.maxLength(10)])),
      'senha': new FormControl(null, Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(10)])),
    })
  }

}
