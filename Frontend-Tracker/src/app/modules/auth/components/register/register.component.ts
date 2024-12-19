import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup; // Declaración de formulario de registro
  toastMessage: string | null = null; //Mensaje del Toast
  toastTitle: string = 'Notificación'; // Título del toast
  toastTimestamp: string = 'Hace un momento'; // Marca de tiempo
  toastIcon: string | null = null; // URL del icono si lo necesitas


  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      confirmPassword: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      this.showToast('Por favor, completa todos los campos correctamente.', 'Registro incompleto', 'Justo ahora', 'fa-solid fa-square-xmark me-1 text-danger');
      return;
    }

    const formValue = this.registerForm.value;
    if (formValue.password !== formValue.confirmPassword) {
      this.showToast('Las contraseñas no coinciden.', 'Error de credenciales', 'Justo ahora', 'fa-solid fa-square-xmark me-1 text-danger');
      return;
    }

    this.authService.register(formValue).subscribe({
      next: () => {
        this.showToast('Has creado tu cuenta exitosamente. Redirigiendo a la pantalla de inicio de sesión', 'Usuario registrado', 'Justo ahora', 'fa-solid fa-square-check me-1 text-success');
        setTimeout(() => {
          this.router.navigate(['/auth/login']);
        }, 3000); // Redirige tras 3 segundos
      },
      error: (err) => {
        this.showToast('Ocurrió un error al registrar el usuario. Inténtalo de nuevo más tarde.', 'Error', 'Justo ahora', 'fa-solid fa-square-xmark me-1 text-danger');
        console.error(err);
      },
    });
  }



  showToast(message: string, title: string, timestamp: string, icon: string ) {
    this.toastMessage = message;
    this.toastTitle = title;
    this.toastTimestamp = timestamp;
    this.toastIcon = icon;

    // Borra el toast después de 5 segundos
    setTimeout(() => {
      this.clearToast();
    }, 5000);
  }

  clearToast() {
    this.toastMessage = null;
  }
}
