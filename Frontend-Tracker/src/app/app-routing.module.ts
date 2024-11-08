import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: "", loadChildren: () => import('./modules/index/index.module').then(m => m.IndexModule)},
  {path: "auth", loadChildren: () => import('./modules/auth/auth.module').then(m => m.AuthModule)},
  {path: "welcome", loadChildren: () => import('./modules/welcome/welcome.module').then(m => m.WelcomeModule)},
  {path: "dashboard", loadChildren: () => import('./modules/dashboard/dashboard.module').then(m => m.DashboardModule)},
  {path: "food", loadChildren: () => import('./modules/food/food.module').then(m => m.FoodModule)},
  {path: "history", loadChildren: () => import('./modules/history/history.module').then(m => m.HistoryModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
