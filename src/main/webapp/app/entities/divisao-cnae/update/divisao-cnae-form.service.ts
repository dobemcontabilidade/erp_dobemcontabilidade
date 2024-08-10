import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDivisaoCnae, NewDivisaoCnae } from '../divisao-cnae.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDivisaoCnae for edit and NewDivisaoCnaeFormGroupInput for create.
 */
type DivisaoCnaeFormGroupInput = IDivisaoCnae | PartialWithRequiredKeyOf<NewDivisaoCnae>;

type DivisaoCnaeFormDefaults = Pick<NewDivisaoCnae, 'id'>;

type DivisaoCnaeFormGroupContent = {
  id: FormControl<IDivisaoCnae['id'] | NewDivisaoCnae['id']>;
  codigo: FormControl<IDivisaoCnae['codigo']>;
  descricao: FormControl<IDivisaoCnae['descricao']>;
  secao: FormControl<IDivisaoCnae['secao']>;
};

export type DivisaoCnaeFormGroup = FormGroup<DivisaoCnaeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DivisaoCnaeFormService {
  createDivisaoCnaeFormGroup(divisaoCnae: DivisaoCnaeFormGroupInput = { id: null }): DivisaoCnaeFormGroup {
    const divisaoCnaeRawValue = {
      ...this.getFormDefaults(),
      ...divisaoCnae,
    };
    return new FormGroup<DivisaoCnaeFormGroupContent>({
      id: new FormControl(
        { value: divisaoCnaeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigo: new FormControl(divisaoCnaeRawValue.codigo, {
        validators: [Validators.maxLength(15)],
      }),
      descricao: new FormControl(divisaoCnaeRawValue.descricao, {
        validators: [Validators.required],
      }),
      secao: new FormControl(divisaoCnaeRawValue.secao, {
        validators: [Validators.required],
      }),
    });
  }

  getDivisaoCnae(form: DivisaoCnaeFormGroup): IDivisaoCnae | NewDivisaoCnae {
    return form.getRawValue() as IDivisaoCnae | NewDivisaoCnae;
  }

  resetForm(form: DivisaoCnaeFormGroup, divisaoCnae: DivisaoCnaeFormGroupInput): void {
    const divisaoCnaeRawValue = { ...this.getFormDefaults(), ...divisaoCnae };
    form.reset(
      {
        ...divisaoCnaeRawValue,
        id: { value: divisaoCnaeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DivisaoCnaeFormDefaults {
    return {
      id: null,
    };
  }
}
