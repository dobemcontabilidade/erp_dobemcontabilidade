import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAreaContabilContador, NewAreaContabilContador } from '../area-contabil-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAreaContabilContador for edit and NewAreaContabilContadorFormGroupInput for create.
 */
type AreaContabilContadorFormGroupInput = IAreaContabilContador | PartialWithRequiredKeyOf<NewAreaContabilContador>;

type AreaContabilContadorFormDefaults = Pick<NewAreaContabilContador, 'id' | 'ativo'>;

type AreaContabilContadorFormGroupContent = {
  id: FormControl<IAreaContabilContador['id'] | NewAreaContabilContador['id']>;
  percentualExperiencia: FormControl<IAreaContabilContador['percentualExperiencia']>;
  descricaoExperiencia: FormControl<IAreaContabilContador['descricaoExperiencia']>;
  ativo: FormControl<IAreaContabilContador['ativo']>;
  contador: FormControl<IAreaContabilContador['contador']>;
  areaContabil: FormControl<IAreaContabilContador['areaContabil']>;
};

export type AreaContabilContadorFormGroup = FormGroup<AreaContabilContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AreaContabilContadorFormService {
  createAreaContabilContadorFormGroup(
    areaContabilContador: AreaContabilContadorFormGroupInput = { id: null },
  ): AreaContabilContadorFormGroup {
    const areaContabilContadorRawValue = {
      ...this.getFormDefaults(),
      ...areaContabilContador,
    };
    return new FormGroup<AreaContabilContadorFormGroupContent>({
      id: new FormControl(
        { value: areaContabilContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      percentualExperiencia: new FormControl(areaContabilContadorRawValue.percentualExperiencia),
      descricaoExperiencia: new FormControl(areaContabilContadorRawValue.descricaoExperiencia),
      ativo: new FormControl(areaContabilContadorRawValue.ativo),
      contador: new FormControl(areaContabilContadorRawValue.contador, {
        validators: [Validators.required],
      }),
      areaContabil: new FormControl(areaContabilContadorRawValue.areaContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getAreaContabilContador(form: AreaContabilContadorFormGroup): IAreaContabilContador | NewAreaContabilContador {
    return form.getRawValue() as IAreaContabilContador | NewAreaContabilContador;
  }

  resetForm(form: AreaContabilContadorFormGroup, areaContabilContador: AreaContabilContadorFormGroupInput): void {
    const areaContabilContadorRawValue = { ...this.getFormDefaults(), ...areaContabilContador };
    form.reset(
      {
        ...areaContabilContadorRawValue,
        id: { value: areaContabilContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AreaContabilContadorFormDefaults {
    return {
      id: null,
      ativo: false,
    };
  }
}
