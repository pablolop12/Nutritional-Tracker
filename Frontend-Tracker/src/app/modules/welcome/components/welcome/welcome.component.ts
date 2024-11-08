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
  userId: number | null = null; // Initialize userId as null

  constructor(
    private fb: FormBuilder,
    private nutritionService: NutritionService,
    private router: Router,
    private authService: AuthService // Inject the authentication service
  ) {
    this.welcomeForm = this.fb.group({
      height: ['', [Validators.required, Validators.min(1)]],
      weight: ['', [Validators.required, Validators.min(1)]],
      birthDate: ['', [Validators.required]], // This field should be of type 'date'
      bodyFat: ['', [Validators.required, Validators.min(0)]],
      sexo: ['', [Validators.required]],
      activityLevel: ['', [Validators.required]],
      goal: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    // Try to get the userId directly from the JWT token
    const token = localStorage.getItem('token'); // Ensure to use the correct name of the token in your localStorage

    if (token) {
      try {
        const decodedToken: any = jwtDecode(token); // Decode the token using the correct function
        this.userId = decodedToken.userId; // Extract the userId from the decoded token
      } catch (error) {
        console.error('Error decoding JWT token:', error);
      }
    }

    // If unable to get userId from the token, get user details
    if (!this.userId) {
      this.authService.getUserDetails().subscribe(
        (response) => {
          this.userId = response.id; // Retrieve the logged-in user's ID
        },
        (error) => {
          console.error('Error getting user details', error);
        }
      );
    }
  }

  onSubmit(): void {
    if (this.welcomeForm.valid && this.userId !== null) {
      console.log('Valid form, sending data:', this.welcomeForm.value);

      // Modify the payload to include the user id
      const payload = {
        ...this.welcomeForm.value,
        user: {
          id: this.userId // Use the recovered user ID
        }
      };

      this.nutritionService.calculateMacros(payload).subscribe(
        (response) => {
          console.log('User details saved successfully', response);
          this.router.navigate(['/dashboard']); // Redirect to the dashboard or home page
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
