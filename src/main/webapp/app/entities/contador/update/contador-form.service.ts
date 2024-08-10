import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContador, NewContador } from '../contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContador for edit and NewContadorFormGroupInput for create.
 */
type ContadorFormGroupInput = IContador | PartialWithRequiredKeyOf<NewContador>;

type ContadorFormDefaults = Pick<NewContador, 'id'>;

type ContadorFormGroupContent = {
  id: FormControl<IContador['id'] | NewContador['id']>;
  nome: FormControl<IContador['nome']>;
  crc: FormControl<IContador['crc']>;
  limiteEmpresas: FormControl<IContador['limiteEmpresas']>;
  limiteAreaContabils: FormControl<IContador['limiteAreaContabils']>;
  limiteFaturamento: FormControl<IContador['limiteFaturamento']>;
  limiteDepartamentos: FormControl<IContador['limiteDepartamentos']>;
  pessoa: FormControl<IContador['pessoa']>;
  perfilContador: FormControl<IContador['perfilContador']>;
};

export type ContadorFormGroup = FormGroup<ContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContadorFormService {
  createContadorFormGroup(contador: ContadorFormGroupInput = { id: null }): ContadorFormGroup {
    const contadorRawValue = {
      ...this.getFormDefaults(),
      ...contador,
    };
    return new FormGroup<ContadorFormGroupContent>({
      id: new FormControl(
        { value: contadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(contadorRawValue.nome, {
        validators: [Validators.maxLength(200)],
      }),
      crc: new FormControl(contadorRawValue.crc, {
        validators: [Validators.required],
      }),
      limiteEmpresas: new FormControl(contadorRawValue.limiteEmpresas),
      limiteAreaContabils: new FormControl(contadorRawValue.limiteAreaContabils),
      limiteFaturamento: new FormControl(contadorRawValue.limiteFaturamento),
      limiteDepartamentos: new FormControl(contadorRawValue.limiteDepartamentos),
      pessoa: new FormControl(contadorRawValue.pessoa, {
        validators: [Validators.required],
      }),
      perfilContador: new FormControl(contadorRawValue.perfilContador, {
        validators: [Validators.required],
      }),
    });
  }

  getContador(form: ContadorFormGroup): IContador | NewContador {
    return form.getRawValue() as IContador | NewContador;
  }

  resetForm(form: ContadorFormGroup, contador: ContadorFormGroupInput): void {
    const contadorRawValue = { ...this.getFormDefaults(), ...contador };
    form.reset(
      {
        ...contadorRawValue,
        id: { value: contadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContadorFormDefaults {
    return {
      id: null,
    };
  }
}
