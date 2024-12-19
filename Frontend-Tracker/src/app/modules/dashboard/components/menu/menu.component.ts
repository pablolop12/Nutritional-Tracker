import { Component, OnInit, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FoodService } from '../../../../services/food.service';
import { ConsumptionService } from '../../../../services/consumption.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  @ViewChild('modal') modalRef!: ElementRef; // Referencia al modal en el HTML

  foodItems: any[] = [];
  filteredFoodItems: any[] = [];
  showModal: boolean = false;
  selectedFood: any = null;
  inputQuantity: number = 0;
  selectedFilter: string = '';
  searchQuery: string = ''; 
  isEditMode: boolean = false;

  constructor(
    private foodService: FoodService,
    private consumptionService: ConsumptionService,
    private router: Router,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.loadUserFoods();
  }

  loadUserFoods() {
    this.foodService.getFoodsByUser().subscribe(
      (response: any[]) => {
        this.foodItems = response;
        this.filteredFoodItems = response;
      },
      (error) => {
        console.error('Error al cargar las comidas del usuario', error);
      }
    );
  }

  toggleEditMode(): void {
    this.isEditMode = !this.isEditMode;
  }

  deleteFood(id: number, event: Event): void {
  event.stopPropagation(); // Detener propagación del clic
  if (confirm('¿Estás seguro de que deseas eliminar esta comida?')) {
    this.foodService.deleteFood(id).subscribe(
      () => {
        this.foodItems = this.foodItems.filter(food => food.id !== id);
        this.filterFoodsByType(this.selectedFilter);
      },
      (error) => {
        console.error('Error al eliminar la comida', error);
      }
    );
  }
}


  filterFoodsByType(type: string): void {
    this.selectedFilter = type;

    this.filteredFoodItems = this.foodItems.filter(item => {
      const matchesType = type === '' || item.type?.toLowerCase() === type.toLowerCase();
      const matchesName = item.name.toLowerCase().includes(this.searchQuery);
      return matchesType && matchesName;
    });
  }

  filterFoodsByName(event: Event): void {
    const query = (event.target as HTMLInputElement).value.toLowerCase();
    this.searchQuery = query;

    this.filteredFoodItems = this.foodItems.filter(item => {
      const matchesName = item.name.toLowerCase().includes(query);
      const matchesType = this.selectedFilter === '' || item.type?.toLowerCase() === this.selectedFilter.toLowerCase();
      return matchesName && matchesType;
    });
  }

  onFoodItemClick(item: any): void {
    this.selectedFood = { ...item };
    this.inputQuantity = 0;
    this.showModal = true;

    setTimeout(() => {
      const modalElement = this.modalRef.nativeElement;
      this.renderer.appendChild(document.body, modalElement);
    });
  }

  closeModal(): void {
    const modalElement = this.modalRef.nativeElement;
    this.renderer.removeChild(document.body, modalElement);
    this.showModal = false;
    this.selectedFood = null;
  }

  confirmConsumption(): void {
    if (!this.inputQuantity || this.inputQuantity <= 0) {
      alert('Por favor, ingresa una cantidad válida');
      return;
    }

    const consumptionData = {
      food: { id: this.selectedFood.id },
      quantity: this.inputQuantity,
      consumedAt: new Date().toISOString()
    };

    this.consumptionService.createConsumption(consumptionData).subscribe(
      () => {
        this.closeModal();
        this.router.navigateByUrl('/dashboard', { skipLocationChange: false }).then(() => {
          window.location.reload();
        });
      },
      (error) => {
        console.error('Error al registrar consumo', error);
        alert('Error al registrar el consumo');
      }
    );
  }
}
