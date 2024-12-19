import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'https://nutriweb-railway-deploy-repo-production.up.railway.app/api/users'; // URL del backend para autenticación
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient) { this.currentUserSubject = new BehaviorSubject<any>(this.getTokenPayload());
    this.currentUser = this.currentUserSubject.asObservable();}

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { email, password });
  }

  register(userData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, userData);
  }

  getUserDetails(): Observable<any> {
    return this.http.get(`${this.apiUrl}/me`);
  }

  saveUserDetails(details: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/details`, details);
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  getToken(): string | null {
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser) {
      return JSON.parse(currentUser).token;
    }
    return null;
  }

  private getTokenPayload(): any {
    const token = this.getToken();
    if (token) {
      try {
        const payload = atob(token.split('.')[1]);
        return JSON.parse(payload);
      } catch (e) {
        console.error('Error parsing token payload', e);
        return null;
      }
    }
    return null;
  }

  
  
}
