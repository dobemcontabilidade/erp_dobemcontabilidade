import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPerfilContadorDepartamento, NewPerfilContadorDepartamento } from '../perfil-contador-departamento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerfilContadorDepartamento for edit and NewPerfilContadorDepartamentoFormGroupInput for create.
 */
type PerfilContadorDepartamentoFormGroupInput = IPerfilContadorDepartamento | PartialWithRequiredKeyOf<NewPerfilContadorDepartamento>;

type PerfilContadorDepartamentoFormDefaults = Pick<NewPerfilContadorDepartamento, 'id'>;

type PerfilContadorDepartamentoFormGroupContent = {
  id: FormControl<IPerfilContadorDepartamento['id'] | NewPerfilContadorDepartamento['id']>;
  quantidadeEmpresas: FormControl<IPerfilContadorDepartamento['quantidadeEmpresas']>;
  percentualExperiencia: FormControl<IPerfilContadorDepartamento['percentualExperiencia']>;
  departamento: FormControl<IPerfilContadorDepartamento['departamento']>;
  perfilContador: FormControl<IPerfilContadorDepartamento['perfilContador']>;
};

export type PerfilContadorDepartamentoFormGroup = FormGroup<PerfilContadorDepartamentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PerfilContadorDepartamentoFormService {
  createPerfilContadorDepartamentoFormGroup(
    perfilContadorDepartamento: PerfilContadorDepartamentoFormGroupInput = { id: null },
  ): PerfilContadorDepartamentoFormGroup {
    const perfilContadorDepartamentoRawValue = {
      ...this.getFormDefaults(),
      ...perfilContadorDepartamento,
    };
    return new FormGroup<PerfilContadorDepartamentoFormGroupContent>({
      id: new FormControl(
        { value: perfilContadorDepartamentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      quantidadeEmpresas: new FormControl(perfilContadorDepartamentoRawValue.quantidadeEmpresas),
      percentualExperiencia: new FormControl(perfilContadorDepartamentoRawValue.percentualExperiencia),
      departamento: new FormControl(perfilContadorDepartamentoRawValue.departamento, {
        validators: [Validators.required],
      }),
      perfilContador: new FormControl(perfilContadorDepartamentoRawValue.perfilContador, {
        validators: [Validators.required],
      }),
    });
  }

  getPerfilContadorDepartamento(form: PerfilContadorDepartamentoFormGroup): IPerfilContadorDepartamento | NewPerfilContadorDepartamento {
    return form.getRawValue() as IPerfilContadorDepartamento | NewPerfilContadorDepartamento;
  }

  resetForm(form: PerfilContadorDepartamentoFormGroup, perfilContadorDepartamento: PerfilContadorDepartamentoFormGroupInput): void {
    const perfilContadorDepartamentoRawValue = { ...this.getFormDefaults(), ...perfilContadorDepartamento };
    form.reset(
      {
        ...perfilContadorDepartamentoRawValue,
        id: { value: perfilContadorDepartamentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PerfilContadorDepartamentoFormDefaults {
    return {
      id: null,
    };
  }
}
