import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {
  private apiUrl = 'http://nutriweb-railway-deploy-repo.railway.internal/api/users/me'; // Ajusta según tu URL

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getUserDetailsByUserId(userId: number): Observable<any> {
    return this.http.get<any>(`http://nutriweb-railway-deploy-repo.railway.internal/api/user-details/user/${userId}`, { headers: this.getAuthHeaders() });
  }

  getCurrentUser(): Observable<any> {
    return this.http.get<any>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  updateUserField(data: any): Observable<any> {
    return this.http.put<any>(
      'http://nutriweb-railway-deploy-repo.railway.internal/api/user-details/update',
      data,
      { headers: { 'Content-Type': 'application/json' } }
    );
  }

  updateUserDetails(data: any, resetMacros: boolean = false): Observable<any> {
    const params = resetMacros ? '?resetMacros=true' : '';
    return this.http.put<any>(`http://nutriweb-railway-deploy-repo.railway.internal/api/user-details/update${params}`, data);
  }
  
  
  
  
  
}
