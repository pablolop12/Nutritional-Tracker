import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HistoryComponent } from './components/history/history.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

const routes: Routes = [
  { path: '', component: HistoryComponent }
];

@NgModule({
  declarations: [
    HistoryComponent
  ],
  imports: [
    RouterModule.forChild(routes), 
    CommonModule, 
    FormsModule, 
    HttpClientModule,
    ReactiveFormsModule 
  ],
  exports: [RouterModule],
})
export class HistoryModule { }
