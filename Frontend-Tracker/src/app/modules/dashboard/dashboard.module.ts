import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ProgressbarsComponent } from './components/progressbars/progressbars.component';
import { MenuComponent } from './components/menu/menu.component';

const routes: Routes = [
  { path: '', component: DashboardComponent }
];

@NgModule({
  declarations: [
    DashboardComponent,
    ProgressbarsComponent,
    MenuComponent
  ],
  imports: [
    RouterModule.forChild(routes), 
    CommonModule, 
    FormsModule, 
    HttpClientModule
  ]
})
export class DashboardModule { }
