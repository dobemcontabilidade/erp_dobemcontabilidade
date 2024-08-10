import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPerfilContadorAreaContabil, NewPerfilContadorAreaContabil } from '../perfil-contador-area-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerfilContadorAreaContabil for edit and NewPerfilContadorAreaContabilFormGroupInput for create.
 */
type PerfilContadorAreaContabilFormGroupInput = IPerfilContadorAreaContabil | PartialWithRequiredKeyOf<NewPerfilContadorAreaContabil>;

type PerfilContadorAreaContabilFormDefaults = Pick<NewPerfilContadorAreaContabil, 'id'>;

type PerfilContadorAreaContabilFormGroupContent = {
  id: FormControl<IPerfilContadorAreaContabil['id'] | NewPerfilContadorAreaContabil['id']>;
  quantidadeEmpresas: FormControl<IPerfilContadorAreaContabil['quantidadeEmpresas']>;
  percentualExperiencia: FormControl<IPerfilContadorAreaContabil['percentualExperiencia']>;
  areaContabil: FormControl<IPerfilContadorAreaContabil['areaContabil']>;
  perfilContador: FormControl<IPerfilContadorAreaContabil['perfilContador']>;
};

export type PerfilContadorAreaContabilFormGroup = FormGroup<PerfilContadorAreaContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PerfilContadorAreaContabilFormService {
  createPerfilContadorAreaContabilFormGroup(
    perfilContadorAreaContabil: PerfilContadorAreaContabilFormGroupInput = { id: null },
  ): PerfilContadorAreaContabilFormGroup {
    const perfilContadorAreaContabilRawValue = {
      ...this.getFormDefaults(),
      ...perfilContadorAreaContabil,
    };
    return new FormGroup<PerfilContadorAreaContabilFormGroupContent>({
      id: new FormControl(
        { value: perfilContadorAreaContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      quantidadeEmpresas: new FormControl(perfilContadorAreaContabilRawValue.quantidadeEmpresas),
      percentualExperiencia: new FormControl(perfilContadorAreaContabilRawValue.percentualExperiencia),
      areaContabil: new FormControl(perfilContadorAreaContabilRawValue.areaContabil, {
        validators: [Validators.required],
      }),
      perfilContador: new FormControl(perfilContadorAreaContabilRawValue.perfilContador, {
        validators: [Validators.required],
      }),
    });
  }

  getPerfilContadorAreaContabil(form: PerfilContadorAreaContabilFormGroup): IPerfilContadorAreaContabil | NewPerfilContadorAreaContabil {
    return form.getRawValue() as IPerfilContadorAreaContabil | NewPerfilContadorAreaContabil;
  }

  resetForm(form: PerfilContadorAreaContabilFormGroup, perfilContadorAreaContabil: PerfilContadorAreaContabilFormGroupInput): void {
    const perfilContadorAreaContabilRawValue = { ...this.getFormDefaults(), ...perfilContadorAreaContabil };
    form.reset(
      {
        ...perfilContadorAreaContabilRawValue,
        id: { value: perfilContadorAreaContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PerfilContadorAreaContabilFormDefaults {
    return {
      id: null,
    };
  }
}
