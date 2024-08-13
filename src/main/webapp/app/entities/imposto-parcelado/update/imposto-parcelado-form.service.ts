import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IImpostoParcelado, NewImpostoParcelado } from '../imposto-parcelado.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImpostoParcelado for edit and NewImpostoParceladoFormGroupInput for create.
 */
type ImpostoParceladoFormGroupInput = IImpostoParcelado | PartialWithRequiredKeyOf<NewImpostoParcelado>;

type ImpostoParceladoFormDefaults = Pick<NewImpostoParcelado, 'id'>;

type ImpostoParceladoFormGroupContent = {
  id: FormControl<IImpostoParcelado['id'] | NewImpostoParcelado['id']>;
  diasAtraso: FormControl<IImpostoParcelado['diasAtraso']>;
  parcelamentoImposto: FormControl<IImpostoParcelado['parcelamentoImposto']>;
  impostoAPagarEmpresa: FormControl<IImpostoParcelado['impostoAPagarEmpresa']>;
};

export type ImpostoParceladoFormGroup = FormGroup<ImpostoParceladoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImpostoParceladoFormService {
  createImpostoParceladoFormGroup(impostoParcelado: ImpostoParceladoFormGroupInput = { id: null }): ImpostoParceladoFormGroup {
    const impostoParceladoRawValue = {
      ...this.getFormDefaults(),
      ...impostoParcelado,
    };
    return new FormGroup<ImpostoParceladoFormGroupContent>({
      id: new FormControl(
        { value: impostoParceladoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      diasAtraso: new FormControl(impostoParceladoRawValue.diasAtraso),
      parcelamentoImposto: new FormControl(impostoParceladoRawValue.parcelamentoImposto, {
        validators: [Validators.required],
      }),
      impostoAPagarEmpresa: new FormControl(impostoParceladoRawValue.impostoAPagarEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getImpostoParcelado(form: ImpostoParceladoFormGroup): IImpostoParcelado | NewImpostoParcelado {
    return form.getRawValue() as IImpostoParcelado | NewImpostoParcelado;
  }

  resetForm(form: ImpostoParceladoFormGroup, impostoParcelado: ImpostoParceladoFormGroupInput): void {
    const impostoParceladoRawValue = { ...this.getFormDefaults(), ...impostoParcelado };
    form.reset(
      {
        ...impostoParceladoRawValue,
        id: { value: impostoParceladoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ImpostoParceladoFormDefaults {
    return {
      id: null,
    };
  }
}
