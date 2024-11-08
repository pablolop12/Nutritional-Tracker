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
        this.consumedNutrients.calories += (consumption.calories / 100) * consumption.quantity;
        this.consumedNutrients.fats += (consumption.fats / 100) * consumption.quantity;
        this.consumedNutrients.proteins += (consumption.proteins / 100) * consumption.quantity;
        this.consumedNutrients.carbs += (consumption.carbs / 100) * consumption.quantity;
        this.consumedNutrients.saturatedFats += (consumption.saturatedFats / 100) * consumption.quantity;
        this.consumedNutrients.sugars += (consumption.sugars / 100) * consumption.quantity;
      }
    });

    console.log('Nutrientes consumidos hoy:', this.consumedNutrients);
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
