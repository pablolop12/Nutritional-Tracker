import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NutritionService {
  private apiUrl = 'http://localhost:8080/api/nutrition'; // URL del backend para nutrición

  constructor(private http: HttpClient) {}

  calculateMacros(details: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/calculate`, details);
  }
}
