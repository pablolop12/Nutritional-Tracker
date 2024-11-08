import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users'; // URL del backend para autenticación

  constructor(private http: HttpClient) {}

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

  
  
}
