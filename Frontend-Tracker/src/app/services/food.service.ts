import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FoodService {
  private apiUrl = 'http://localhost:8080/api/foods'; // Ajusta según tu URL

  constructor(private http: HttpClient) {}

  getFoodsByUser(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user`);
  }

  createFood(food: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, food);
  }
}
