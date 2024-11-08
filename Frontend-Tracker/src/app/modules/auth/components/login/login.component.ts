import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.authService.login(email, password).subscribe(
        (response: any) => {
          console.log('Login exitoso', response);
          
          // Almacenar el token en el localStorage
          localStorage.setItem('token', response.token);

          // Redirigir al formulario de detalles si no tiene detalles completos
          if (!response.hasCompleteDetails) {
            this.router.navigate(['/welcome']); // Redirige al componente Welcome
          } else {
            this.router.navigate(['/']); // Redirige al dashboard o página principal
          }
        },
        (error) => {
          console.error('Error al iniciar sesión', error);
        }
      );
    }
  }
}
