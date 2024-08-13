import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAreaContabil, NewAreaContabil } from '../area-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAreaContabil for edit and NewAreaContabilFormGroupInput for create.
 */
type AreaContabilFormGroupInput = IAreaContabil | PartialWithRequiredKeyOf<NewAreaContabil>;

type AreaContabilFormDefaults = Pick<NewAreaContabil, 'id'>;

type AreaContabilFormGroupContent = {
  id: FormControl<IAreaContabil['id'] | NewAreaContabil['id']>;
  nome: FormControl<IAreaContabil['nome']>;
  descricao: FormControl<IAreaContabil['descricao']>;
  percentual: FormControl<IAreaContabil['percentual']>;
};

export type AreaContabilFormGroup = FormGroup<AreaContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AreaContabilFormService {
  createAreaContabilFormGroup(areaContabil: AreaContabilFormGroupInput = { id: null }): AreaContabilFormGroup {
    const areaContabilRawValue = {
      ...this.getFormDefaults(),
      ...areaContabil,
    };
    return new FormGroup<AreaContabilFormGroupContent>({
      id: new FormControl(
        { value: areaContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(areaContabilRawValue.nome),
      descricao: new FormControl(areaContabilRawValue.descricao, {
        validators: [Validators.maxLength(200)],
      }),
      percentual: new FormControl(areaContabilRawValue.percentual),
    });
  }

  getAreaContabil(form: AreaContabilFormGroup): IAreaContabil | NewAreaContabil {
    return form.getRawValue() as IAreaContabil | NewAreaContabil;
  }

  resetForm(form: AreaContabilFormGroup, areaContabil: AreaContabilFormGroupInput): void {
    const areaContabilRawValue = { ...this.getFormDefaults(), ...areaContabil };
    form.reset(
      {
        ...areaContabilRawValue,
        id: { value: areaContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AreaContabilFormDefaults {
    return {
      id: null,
    };
  }
}
