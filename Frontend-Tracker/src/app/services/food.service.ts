import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FoodService {
  private apiUrl = 'https://nutriweb-railway-deploy-repo-production.up.railway.app/api/foods';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    if (typeof window !== 'undefined' && localStorage.getItem('token')) {
      const token = localStorage.getItem('token');
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    return new HttpHeaders();
  }

  getFoodsByUser(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user`, { headers: this.getAuthHeaders() });
  }

  createFood(food: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, food, { headers: this.getAuthHeaders() });
  }

  deleteFood(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }
  
}
