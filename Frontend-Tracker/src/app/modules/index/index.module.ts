import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndexComponent } from './components/index/index.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from '../../auth.interceptor';

const routes: Routes = [
  { path: '', component: IndexComponent }
];

@NgModule({
  declarations: [IndexComponent],
  imports: [
    RouterModule.forChild(routes), 
    CommonModule, 
    FormsModule, 
    HttpClientModule  
  ],
  exports: [RouterModule],
})
export class IndexModule { }
