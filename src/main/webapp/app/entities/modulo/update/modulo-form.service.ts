import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IModulo, NewModulo } from '../modulo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IModulo for edit and NewModuloFormGroupInput for create.
 */
type ModuloFormGroupInput = IModulo | PartialWithRequiredKeyOf<NewModulo>;

type ModuloFormDefaults = Pick<NewModulo, 'id'>;

type ModuloFormGroupContent = {
  id: FormControl<IModulo['id'] | NewModulo['id']>;
  nome: FormControl<IModulo['nome']>;
  descricao: FormControl<IModulo['descricao']>;
  sistema: FormControl<IModulo['sistema']>;
};

export type ModuloFormGroup = FormGroup<ModuloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ModuloFormService {
  createModuloFormGroup(modulo: ModuloFormGroupInput = { id: null }): ModuloFormGroup {
    const moduloRawValue = {
      ...this.getFormDefaults(),
      ...modulo,
    };
    return new FormGroup<ModuloFormGroupContent>({
      id: new FormControl(
        { value: moduloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(moduloRawValue.nome),
      descricao: new FormControl(moduloRawValue.descricao),
      sistema: new FormControl(moduloRawValue.sistema, {
        validators: [Validators.required],
      }),
    });
  }

  getModulo(form: ModuloFormGroup): IModulo | NewModulo {
    return form.getRawValue() as IModulo | NewModulo;
  }

  resetForm(form: ModuloFormGroup, modulo: ModuloFormGroupInput): void {
    const moduloRawValue = { ...this.getFormDefaults(), ...modulo };
    form.reset(
      {
        ...moduloRawValue,
        id: { value: moduloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ModuloFormDefaults {
    return {
      id: null,
    };
  }
}
