import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(): boolean | UrlTree {
    const token = localStorage.getItem('token');
    if (token) {
      return true; // Permite el acceso si existe un token
    } else {

      // Redirige a la página principal si no está logueado
      return this.router.createUrlTree(['/']);
    }
  }
}
