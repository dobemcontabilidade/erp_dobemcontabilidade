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
  crc: FormControl<IContador['crc']>;
  limiteEmpresas: FormControl<IContador['limiteEmpresas']>;
  limiteDepartamentos: FormControl<IContador['limiteDepartamentos']>;
  limiteFaturamento: FormControl<IContador['limiteFaturamento']>;
  situacaoContador: FormControl<IContador['situacaoContador']>;
  pessoa: FormControl<IContador['pessoa']>;
  usuarioContador: FormControl<IContador['usuarioContador']>;
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
      crc: new FormControl(contadorRawValue.crc),
      limiteEmpresas: new FormControl(contadorRawValue.limiteEmpresas),
      limiteDepartamentos: new FormControl(contadorRawValue.limiteDepartamentos),
      limiteFaturamento: new FormControl(contadorRawValue.limiteFaturamento),
      situacaoContador: new FormControl(contadorRawValue.situacaoContador),
      pessoa: new FormControl(contadorRawValue.pessoa, {
        validators: [Validators.required],
      }),
      usuarioContador: new FormControl(contadorRawValue.usuarioContador, {
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
