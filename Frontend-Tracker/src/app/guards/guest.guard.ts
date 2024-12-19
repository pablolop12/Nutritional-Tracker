import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GuestGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(): boolean | UrlTree {
    const token = localStorage.getItem('token');
    if (!token) {
      return true; // Permite el acceso si NO hay token
    } else {

      // Redirige al dashboard si ya está logueado
      return this.router.createUrlTree(['/dashboard']);
    }
  }
}
