import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDescontoPlanoContabil, NewDescontoPlanoContabil } from '../desconto-plano-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDescontoPlanoContabil for edit and NewDescontoPlanoContabilFormGroupInput for create.
 */
type DescontoPlanoContabilFormGroupInput = IDescontoPlanoContabil | PartialWithRequiredKeyOf<NewDescontoPlanoContabil>;

type DescontoPlanoContabilFormDefaults = Pick<NewDescontoPlanoContabil, 'id'>;

type DescontoPlanoContabilFormGroupContent = {
  id: FormControl<IDescontoPlanoContabil['id'] | NewDescontoPlanoContabil['id']>;
  percentual: FormControl<IDescontoPlanoContabil['percentual']>;
  periodoPagamento: FormControl<IDescontoPlanoContabil['periodoPagamento']>;
  planoContabil: FormControl<IDescontoPlanoContabil['planoContabil']>;
};

export type DescontoPlanoContabilFormGroup = FormGroup<DescontoPlanoContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DescontoPlanoContabilFormService {
  createDescontoPlanoContabilFormGroup(
    descontoPlanoContabil: DescontoPlanoContabilFormGroupInput = { id: null },
  ): DescontoPlanoContabilFormGroup {
    const descontoPlanoContabilRawValue = {
      ...this.getFormDefaults(),
      ...descontoPlanoContabil,
    };
    return new FormGroup<DescontoPlanoContabilFormGroupContent>({
      id: new FormControl(
        { value: descontoPlanoContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      percentual: new FormControl(descontoPlanoContabilRawValue.percentual, {
        validators: [Validators.required],
      }),
      periodoPagamento: new FormControl(descontoPlanoContabilRawValue.periodoPagamento, {
        validators: [Validators.required],
      }),
      planoContabil: new FormControl(descontoPlanoContabilRawValue.planoContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getDescontoPlanoContabil(form: DescontoPlanoContabilFormGroup): IDescontoPlanoContabil | NewDescontoPlanoContabil {
    return form.getRawValue() as IDescontoPlanoContabil | NewDescontoPlanoContabil;
  }

  resetForm(form: DescontoPlanoContabilFormGroup, descontoPlanoContabil: DescontoPlanoContabilFormGroupInput): void {
    const descontoPlanoContabilRawValue = { ...this.getFormDefaults(), ...descontoPlanoContabil };
    form.reset(
      {
        ...descontoPlanoContabilRawValue,
        id: { value: descontoPlanoContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DescontoPlanoContabilFormDefaults {
    return {
      id: null,
    };
  }
}
