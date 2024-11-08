import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {

  private apiUrl = 'http://localhost:8080/api/consumptions'; // Asegúrate de que la URL sea correcta

  constructor(private http: HttpClient) { }

  createConsumption(consumption: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, consumption);
  }

  getConsumptionsByUser(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`);
  }

  deleteConsumption(consumptionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${consumptionId}`);
  }
}
