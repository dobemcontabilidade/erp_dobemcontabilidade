import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAdministrador, NewAdministrador } from '../administrador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAdministrador for edit and NewAdministradorFormGroupInput for create.
 */
type AdministradorFormGroupInput = IAdministrador | PartialWithRequiredKeyOf<NewAdministrador>;

type AdministradorFormDefaults = Pick<NewAdministrador, 'id'>;

type AdministradorFormGroupContent = {
  id: FormControl<IAdministrador['id'] | NewAdministrador['id']>;
  nome: FormControl<IAdministrador['nome']>;
  sobreNome: FormControl<IAdministrador['sobreNome']>;
  funcao: FormControl<IAdministrador['funcao']>;
  pessoa: FormControl<IAdministrador['pessoa']>;
};

export type AdministradorFormGroup = FormGroup<AdministradorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AdministradorFormService {
  createAdministradorFormGroup(administrador: AdministradorFormGroupInput = { id: null }): AdministradorFormGroup {
    const administradorRawValue = {
      ...this.getFormDefaults(),
      ...administrador,
    };
    return new FormGroup<AdministradorFormGroupContent>({
      id: new FormControl(
        { value: administradorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(administradorRawValue.nome, {
        validators: [Validators.maxLength(200)],
      }),
      sobreNome: new FormControl(administradorRawValue.sobreNome, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      funcao: new FormControl(administradorRawValue.funcao),
      pessoa: new FormControl(administradorRawValue.pessoa, {
        validators: [Validators.required],
      }),
    });
  }

  getAdministrador(form: AdministradorFormGroup): IAdministrador | NewAdministrador {
    return form.getRawValue() as IAdministrador | NewAdministrador;
  }

  resetForm(form: AdministradorFormGroup, administrador: AdministradorFormGroupInput): void {
    const administradorRawValue = { ...this.getFormDefaults(), ...administrador };
    form.reset(
      {
        ...administradorRawValue,
        id: { value: administradorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AdministradorFormDefaults {
    return {
      id: null,
    };
  }
}
