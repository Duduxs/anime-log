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
import { MatCardModule } from '@angular/material/card';

import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { BlockComponent } from './block/block.component';
import { FormComponent } from './form/form.component';
import { AnimeBlockListComponent } from './main/anime-block-list/anime-block-list.component';
import { UserBlockListComponent } from './main/user-block-list/user-block-list.component';
import { TopAnimeBlockListComponent } from './main/top-anime-block-list/top-anime-block-list.component';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    BlockComponent,
    FormComponent,
    AnimeBlockListComponent,
    UserBlockListComponent,
    TopAnimeBlockListComponent,
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
    MatSelectModule,
    MatCardModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    BlockComponent,
    FormComponent,
    AnimeBlockListComponent,
    UserBlockListComponent,
    TopAnimeBlockListComponent,
    MatButtonModule,
    MatIconModule,
    MatCardModule
  ],
})
export class ComponentsModule {}
