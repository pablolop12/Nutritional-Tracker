import { Component, OnInit } from '@angular/core';
import { FoodService } from '../../../../services/food.service';
import { ConsumptionService } from '../../../../services/consumption.service'; // Importa el nuevo servicio
import Swal from 'sweetalert2'; // Importa SweetAlert

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  menuOpen: boolean = false;
  foodItems: any[] = [];

  constructor(private foodService: FoodService, private consumptionService: ConsumptionService) {}

  ngOnInit(): void {
    this.loadUserFoods();
  }



  loadUserFoods() {
    this.foodService.getFoodsByUser().subscribe(
      (response: any[]) => {
        this.foodItems = response;
        console.log('Comidas cargadas:', this.foodItems);
      },
      (error) => {
        console.error('Error al cargar las comidas del usuario', error);
      }
    );
  }

  onFoodItemClick(foodItem: any) {
    Swal.fire({
      title: 'Ingresa la cantidad en gramos',
      input: 'number',
      inputAttributes: {
        min: '1',
        step: '1'
      },
      showCancelButton: true,
      confirmButtonText: 'Registrar consumo',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        const quantity = result.value;
        this.createConsumption(foodItem, quantity);
      }
    });
  }

  createConsumption(foodItem: any, quantity: number) {
    const consumption = {
      food: { id: foodItem.id },
      quantity: quantity
    };

    this.consumptionService.createConsumption(consumption).subscribe(
      response => {
        Swal.fire('¡Consumo registrado!', 'El consumo ha sido registrado exitosamente.', 'success');
      },
      error => {
        Swal.fire('Error', 'Hubo un error al registrar el consumo.', 'error');
      }
    );
  }
}
