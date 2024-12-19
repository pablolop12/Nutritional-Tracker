import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { GuestGuard } from './guards/guest.guard';

const routes: Routes = [
  {path: "", loadChildren: () => import('./modules/index/index.module').then(m => m.IndexModule),canActivate:[GuestGuard]},
  {path: "auth", loadChildren: () => import('./modules/auth/auth.module').then(m => m.AuthModule),canActivate:[GuestGuard]},
  {path: "welcome", loadChildren: () => import('./modules/welcome/welcome.module').then(m => m.WelcomeModule),canActivate:[AuthGuard]},
  {path: "dashboard", loadChildren: () => import('./modules/dashboard/dashboard.module').then(m => m.DashboardModule),canActivate:[AuthGuard]},
  {path: "food", loadChildren: () => import('./modules/food/food.module').then(m => m.FoodModule),canActivate:[AuthGuard]},
  {path: "history", loadChildren: () => import('./modules/history/history.module').then(m => m.HistoryModule),canActivate:[AuthGuard]},
  {path: "profile", loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule), canActivate:[AuthGuard]},
  { path: '**', redirectTo: '' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
