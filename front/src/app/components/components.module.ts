import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms'; 
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
 
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { BlockComponent } from './block/block.component';
import { FormComponent } from './form/form.component';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent, 
    BlockComponent, 
    FormComponent
  ],
  imports: [
    MatToolbarModule,
    MatIconModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    FormsModule,
    MatButtonModule,
    RouterModule,
    MatMenuModule,
    MatSelectModule
  ],
  exports:[
    HeaderComponent,
    FooterComponent,
    BlockComponent,
    FormComponent,
  ]
})
export class ComponentsModule { }
