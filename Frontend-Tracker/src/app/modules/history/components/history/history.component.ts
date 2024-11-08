import { Component, OnInit } from '@angular/core';
import { ConsumptionService } from '../../../../services/consumption.service'; // Ajusta la ruta del servicio según tu estructura de archivos
import moment from 'moment';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {
  consumptions: any[] = [];
  userId: number = 0;

  constructor(private consumptionService: ConsumptionService) {}

  ngOnInit(): void {
    this.userId = this.getUserIdFromToken();
    this.loadTodayConsumptions();
  }

  loadTodayConsumptions() {
    this.consumptionService.getConsumptionsByUser(this.userId).subscribe(
      (consumptions) => {
        const today = moment().startOf('day');
        this.consumptions = consumptions.filter(consumption => 
          moment(consumption.consumedAt).isSame(today, 'day')
        );
        console.log('Consumos de hoy:', this.consumptions);
      },
      (error) => {
        console.error('Error al obtener los consumos del usuario', error);
      }
    );
  }

  deleteConsumption(consumptionId: number) {
    this.consumptionService.deleteConsumption(consumptionId).subscribe(
      () => {
        this.consumptions = this.consumptions.filter(consumption => consumption.id !== consumptionId);
        console.log('Consumo eliminado:', consumptionId);
      },
      (error) => {
        console.error('Error al eliminar el consumo', error);
      }
    );
  }

  getUserIdFromToken(): number {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = JSON.parse(atob(token.split('.')[1]));
      return decodedToken.userId;
    }
    return 0; 
  }
}
