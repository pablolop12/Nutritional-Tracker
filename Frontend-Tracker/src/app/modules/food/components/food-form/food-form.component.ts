import { Component } from '@angular/core';
import { FoodService } from '../../../../services/food.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-food-form',
  templateUrl: './food-form.component.html',
  styleUrls: ['./food-form.component.scss'] 
})
export class FoodFormComponent {
  foodForm: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private foodService: FoodService, private fb: FormBuilder, private router: Router) {
    this.foodForm = this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      caloriesPer100g: [0, [Validators.required, Validators.min(0)]],
      proteinsPer100g: [0, [Validators.required, Validators.min(0)]],
      fatsPer100g: [0, [Validators.required, Validators.min(0)]],
      carbsPer100g: [0, [Validators.required, Validators.min(0)]],
      saturatedFatsPer100g: [0, [Validators.required, Validators.min(0)]],
      sugarsPer100g: [0, [Validators.required, Validators.min(0)]],
      unitBased: [false],
      imageUrl: ['']
    });
  }

  // Getter que devuelve el texto de los labels
  get macroLabelSuffix(): string {
    return this.foodForm.get('unitBased')?.value ? '/ unidad' : '/ 100g';
  }

  onSubmit(): void {
    if (this.foodForm.valid) {
      this.foodService.createFood(this.foodForm.value).subscribe(
        (response) => {
          this.successMessage = 'Comida creada con éxito!';
          this.errorMessage = '';
          this.router.navigate(['/dashboard']);
          this.foodForm.reset();
        },
        (error) => {
          this.errorMessage = 'Error al crear la comida. Inténtalo de nuevo.';
          this.successMessage = '';
          console.error('Error al crear la comida', error);
        }
      );
    } else {
      this.errorMessage = 'Por favor, complete todos los campos obligatorios.';
      this.successMessage = '';
    }
  }
}
