import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NutritionService } from '../../../../services/nutrition.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {
  welcomeForm: FormGroup;
  userId: number | null = null;
  currentStep: number = 1; // Paso inicial

  constructor(
    private fb: FormBuilder,
    private nutritionService: NutritionService,
    private router: Router,
    private authService: AuthService
  ) {
    this.welcomeForm = this.fb.group({
      height: ['', [Validators.required, Validators.min(1)]],
      weight: ['', [Validators.required, Validators.min(1)]],
      birthDate: ['', [Validators.required]],
      bodyFat: ['', [Validators.required, Validators.min(0)]],
      sexo: ['', [Validators.required]],
      activityLevel: ['', [Validators.required]],
      goal: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    const token = localStorage.getItem('token');

    if (token) {
      try {
        const decodedToken: any = jwtDecode(token);
        this.userId = decodedToken.userId;
      } catch (error) {
        console.error('Error decoding JWT token:', error);
      }
    }

    if (!this.userId) {
      this.authService.getUserDetails().subscribe(
        (response) => {
          this.userId = response.id;
        },
        (error) => {
          console.error('Error getting user details', error);
        }
      );
    }
  }

  // Métodos para cambiar de paso
  nextStep(): void {
    if (this.currentStep < 3) {
      this.currentStep++;
    }
  }

  prevStep(): void {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  onSubmit(): void {
    
    if (this.welcomeForm.valid && this.userId !== null) {
      const payload = {
        ...this.welcomeForm.value,
        user: {
          id: this.userId
        }
      };

      this.nutritionService.calculateMacros(payload).subscribe(
        (response) => {
          console.log('User details saved successfully', response);
          this.router.navigate(['/dashboard']);
        },
        (error) => {
          console.error('Error saving user details', error);
        }
      );
    } else {
      console.log('Form is invalid or user ID is not available:', this.getFormValidationErrors());
    }
  }

  getFormValidationErrors() {
    const errors: any = [];
    Object.keys(this.welcomeForm.controls).forEach(key => {
      const controlErrors = this.welcomeForm.get(key)?.errors;
      if (controlErrors) {
        Object.keys(controlErrors).forEach(keyError => {
          errors.push({
            control: key,
            error: keyError,
            value: controlErrors[keyError]
          });
        });
      }
    });
    return errors;
  }
}
