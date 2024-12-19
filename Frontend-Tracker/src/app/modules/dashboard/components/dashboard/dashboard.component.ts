import { Component, OnInit } from '@angular/core';
import { UserDetailsService } from '../../../../services/user-details.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  userDetails: any = null;
  imc: number = 0; // Índice de Masa Corporal
  weight: number = 0; // Peso (kg)
  bfp: number = 0; // Porcentaje de grasa corporal
  goal: string = ''; // Objetivo
  smmKg: number = 0; // Masa muscular esquelética en kg

  constructor(private userDetailsService: UserDetailsService,  private router: Router) {}

  ngOnInit(): void {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    this.userDetailsService.getCurrentUser().subscribe(
      (data) => {
        this.userDetails = data;
        console.log('Detalles del usuario:', this.userDetails);

        // Asignar propiedades para usar en el HTML
        this.weight = this.userDetails.weight;
        this.bfp = this.userDetails.bfp;
        this.goal = this.userDetails.goal;
        this.imc = this.calculateIMC(this.userDetails.weight, this.userDetails.height);
        this.smmKg = this.calculateSkeletalMass(this.userDetails.weight, this.userDetails.bfp);

        console.log('IMC calculado:', this.imc);
        console.log('SMM (kg) calculado:', this.smmKg);
      },
      (error) => {
        console.error('Error al cargar los datos del usuario:', error);
      }
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  /**
   * Calcula el Índice de Masa Corporal (IMC)
   * @param weight Peso en kg
   * @param height Altura en cm
   * @returns IMC calculado
   */
  calculateIMC(weight: number, height: number): number {
    return weight / Math.pow(height / 100, 2); // Altura convertida a metros
  }

  /**
   * Calcula la Masa Muscular Esquelética (SMM) en kg
   * @param weight Peso en kg
   * @param bfp Porcentaje de grasa corporal
   * @returns SMM en kg
   */
  calculateSkeletalMass(weight: number, bfp: number): number {
    const leanBodyMass = weight * (1 - bfp / 100); // Peso libre de grasa
    const skeletalMass = leanBodyMass * 0.60; // Aproximación del 60% del peso libre de grasa
    return skeletalMass;
  }

  /**
   * Determina el estado de salud basado en el valor y el tipo de métrica
   * @param value Valor a evaluar
   * @param type Tipo de métrica ('imc', 'bfp', 'smmKg')
   * @returns Clase CSS que representa el estado ('green', 'yellow', 'red')
   */
  getHealthStatus(value: number, type: string): string {
    if (type === 'imc') {
      if (value < 18.5) return 'red'; // Bajo peso
      if (value >= 18.5 && value <= 24.9) return 'green'; // Saludable
      return 'yellow'; // Sobrepeso
    } else if (type === 'bfp') {
      if (value < 14) return 'red'; // Bajo nivel de grasa
      if (value >= 14 && value <= 24) return 'green'; // Saludable
      return 'yellow'; // Exceso de grasa
    } else if (type === 'smmKg') {
      if (value < 30) return 'red'; // Masa muscular baja
      if (value >= 30 && value <= 40) return 'green'; // Masa muscular saludable
      return 'yellow'; // Masa muscular alta (por ejemplo, culturista)
    }
    return ''; // Default
  }
}
