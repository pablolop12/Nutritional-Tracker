import { Component, OnInit } from '@angular/core';
import { UserDetailsService } from '../../../../services/user-details.service';
import { ConsumptionService } from '../../../../services/consumption.service'; // Asegúrate de que la ruta sea correcta
import moment from 'moment';


@Component({
  selector: 'app-progressbars',
  templateUrl: './progressbars.component.html',
  styleUrls: ['./progressbars.component.scss']
})
export class ProgressbarsComponent implements OnInit {
  userDetails: any = null;
  userId: number = 0;
  consumedNutrients: any = {
    calories: 0,
    fats: 0,
    proteins: 0,
    carbs: 0,
    saturatedFats: 0,
    sugars: 0
  };

  constructor(
    private userDetailsService: UserDetailsService,
    private consumptionService: ConsumptionService
  ) {}

  ngOnInit(): void {
    this.userId = this.getUserIdFromToken();

    if (this.userId) {
      this.loadUserDetails();
      this.loadConsumptions();
    }
  }

  loadUserDetails() {
    this.userDetailsService.getUserDetailsByUserId(this.userId).subscribe(
      (data) => {
        this.userDetails = data;
        console.log('Detalles del usuario recibidos:', this.userDetails);
      },
      (error) => {
        console.error('Error al obtener los detalles del usuario', error);
      }
    );
  }

  loadConsumptions() {
    this.consumptionService.getConsumptionsByUser(this.userId).subscribe(
      (consumptions) => {
        console.log('Consumptions recibidos:', consumptions); // Depuración
        this.calculateNutrients(consumptions);
      },
      (error) => {
        console.error('Error al obtener los consumos del usuario', error);
      }
    );
  }
  

  calculateNutrients(consumptions: any[]) {
    const today = moment().startOf('day');
  
    consumptions.forEach(consumption => {
      const consumedAt = moment(consumption.consumedAt);
  
      if (consumedAt.isSame(today, 'day')) {
        // Verifica si el producto es unitBased
        const factor = consumption.unitBased
          ? consumption.quantity // Si es unitBased, toma la cantidad directamente
          : consumption.quantity / 100; // Si es en gramos, aplica regla de tres
  
        this.consumedNutrients.calories += consumption.calories * factor;
        this.consumedNutrients.fats += consumption.fats * factor;
        this.consumedNutrients.proteins += consumption.proteins * factor;
        this.consumedNutrients.carbs += consumption.carbs * factor;
        this.consumedNutrients.saturatedFats += consumption.saturatedFats * factor;
        this.consumedNutrients.sugars += consumption.sugars * factor;
      }
    });
  
    console.log('Nutrientes consumidos hoy:', this.consumedNutrients);
  }
  

  getUserIdFromToken(): number {
    if (typeof window !== 'undefined' && localStorage.getItem('token')) {
      const token = localStorage.getItem('token');
      if (token) {
        const decodedToken: any = JSON.parse(atob(token.split('.')[1]));
        return decodedToken.userId;
      }
    }
    return 0;
  }
  
}
