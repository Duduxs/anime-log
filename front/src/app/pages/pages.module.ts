import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';

import { HomeComponent } from '../pages/home/home.component';
import { ComponentsModule } from '../components/components.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RouterModule } from '@angular/router';
import { MainComponent } from './main/main.component';


@NgModule({
  declarations: [HomeComponent, LoginComponent, RegisterComponent, NotFoundComponent, NotFoundComponent, MainComponent],
  imports: [
    CommonModule,
    ComponentsModule,
    RouterModule,
    MatToolbarModule
  ]
})
export class PagesModule { }
