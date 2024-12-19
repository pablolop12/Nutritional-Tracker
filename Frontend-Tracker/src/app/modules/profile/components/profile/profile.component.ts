import { Component, OnInit } from '@angular/core';
import { UserDetailsService } from '../../../../services/user-details.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: any = null; // Datos del usuario
  editableField: string | null = null; // Campo en edición
  tempValue: any = null; // Valor temporal de edición

  // Opciones predefinidas
  activityLevelOptions = [
    { value: 'UNO_A_DOS_DIAS_SEMANALES', label: '1 - 2 días semanales' },
    { value: 'TRES_A_CINCO_DIAS_SEMANALES', label: '3 - 5 días semanales' },
    { value: 'SIETE_DIAS_SEMANALES', label: '6 - 7 días semanales' }
  ];
  goalOptions = [
    { value: 'DEFINICION', label: 'Definición' },
    { value: 'GANANCIA_DE_MASA_MUSCULAR', label: 'Ganancia de masa muscular' },
    { value: 'PERDIDA_DE_PESO', label: 'Pérdida de peso' },
    { value: 'MANTENIMIENTO', label: 'Mantenimiento' }
  ];

  // Nombres de campos
  fieldNames: { [key: string]: string } = {
    activityLevel: 'Nivel de Actividad',
    goal: 'Objetivo',
    calories: 'Calorías',
    proteins: 'Proteínas (g)',
    carbs: 'Carbohidratos (g)',
    fats: 'Grasas (g)',
    saturatedFats: 'Grasas Saturadas (g)',
    sugars: 'Azúcares (g)'
  };

  constructor(private userDetailsService: UserDetailsService) {}

  ngOnInit(): void {
    this.loadUserProfile();
  }

  loadUserProfile(): void {
    this.userDetailsService.getCurrentUser().subscribe(
      (data) => {
        this.user = { 
          ...data,
          activityLevelFormatted: this.formatActivityLevel(data.activityLevel),
          goalFormatted: this.formatGoal(data.goal),
          birthdateFormatted: this.formatDate(data.birthdate)
        };
      },
      (error) => console.error('Error al cargar el perfil:', error)
    );
  }

  startEdit(field: string): void {
    this.editableField = field;
    this.tempValue = this.user[field];
  }

  cancelEdit(): void {
    this.editableField = null;
    this.tempValue = null;
  }

  resetMacros(): void {
    this.userDetailsService.updateUserDetails(this.user, true).subscribe(
      (data) => {
        this.user = data;
        alert('Macros objetivo restablecidos a los valores ideales.');
        this.reloadPage();
      },
      (error) => {
        console.error('Error al restablecer los macros:', error);
      }
    );
  }

  saveEdit(): void {
    if (!this.editableField) {
      console.error('No hay campo en edición.');
      return;
    }

    if (!this.validateField(this.editableField, this.tempValue)) {
      alert('Por favor, introduce un valor válido.');
      return;
    }

    const fieldMapping: { [key: string]: string } = {
      bfp: 'bodyFat',
      activityLevel: 'activityLevel',
      birthdateFormatted: 'birthDate',
      sex: 'sexo',
      goal: 'goal',
      weight: 'weight',
      height: 'height',
      calories: 'calories',
      proteins: 'proteins',
      carbs: 'carbs',
      fats: 'fats',
      saturatedFats: 'saturatedFats',
      sugars: 'sugars'
    };

    const mappedField = fieldMapping[String(this.editableField)] || this.editableField;

    const payload = { [mappedField]: this.tempValue };

    console.log('Payload enviado:', payload);

    this.userDetailsService.updateUserField(payload).subscribe(
      (response) => {
        console.log('Respuesta del backend:', response);
        this.user = { ...this.user, ...response };
        this.editableField = null;
        this.tempValue = null;
        alert('Campo actualizado correctamente.');
        this.reloadPage(); // Recargar la página después de guardar
      },
      (error) => {
        console.error('Error al actualizar el campo:', error);
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }

  validateField(field: string | null, value: any): boolean {
    if (!field) return false;

    switch (field) {
      case 'calories':
      case 'proteins':
      case 'carbs':
      case 'fats':
      case 'saturatedFats':
      case 'sugars':
      case 'weight':
      case 'height':
        return value > 0;
      default:
        return true;
    }
  }

  getFieldOptions(field: string): any[] {
    if (field === 'activityLevel') return this.activityLevelOptions;
    if (field === 'goal') return this.goalOptions;
    return [];
  }

  formatField(field: string, value: any): string {
    if (field === 'activityLevel') return this.formatActivityLevel(value);
    if (field === 'goal') return this.formatGoal(value);
    return value;
  }

  formatActivityLevel(activityLevel: string): string {
    const levels: Record<string, string> = {
      UNO_A_DOS_DIAS_SEMANALES: '1 - 2 días semanales',
      TRES_A_CINCO_DIAS_SEMANALES: '3 - 5 días semanales',
      SIETE_DIAS_SEMANALES: '6 - 7 días semanales'
    };
    return levels[activityLevel] || activityLevel;
  }

  formatGoal(goal: string): string {
    const goals: Record<string, string> = {
      DEFINICION: 'Definición',
      GANANCIA_DE_MASA_MUSCULAR: 'Ganancia de masa muscular',
      PERDIDA_DE_PESO: 'Pérdida de peso',
      MANTENIMIENTO: 'Mantenimiento'
    };
    return goals[goal] || goal;
  }

  formatDate(date: string): string {
    if (!date) return 'No especificado';
    const [year, month, day] = date.split('-');
    return `${day}/${month}/${year}`;
  }
}
