import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FoodFormComponent } from './components/food-form/food-form.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

const routes: Routes = [
  { path: 'new', component: FoodFormComponent }
];

@NgModule({
  declarations: [
    FoodFormComponent
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
export class FoodModule { }
