import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IImpostoEmpresa, NewImpostoEmpresa } from '../imposto-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImpostoEmpresa for edit and NewImpostoEmpresaFormGroupInput for create.
 */
type ImpostoEmpresaFormGroupInput = IImpostoEmpresa | PartialWithRequiredKeyOf<NewImpostoEmpresa>;

type ImpostoEmpresaFormDefaults = Pick<NewImpostoEmpresa, 'id'>;

type ImpostoEmpresaFormGroupContent = {
  id: FormControl<IImpostoEmpresa['id'] | NewImpostoEmpresa['id']>;
  diaVencimento: FormControl<IImpostoEmpresa['diaVencimento']>;
  empresa: FormControl<IImpostoEmpresa['empresa']>;
  imposto: FormControl<IImpostoEmpresa['imposto']>;
};

export type ImpostoEmpresaFormGroup = FormGroup<ImpostoEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImpostoEmpresaFormService {
  createImpostoEmpresaFormGroup(impostoEmpresa: ImpostoEmpresaFormGroupInput = { id: null }): ImpostoEmpresaFormGroup {
    const impostoEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...impostoEmpresa,
    };
    return new FormGroup<ImpostoEmpresaFormGroupContent>({
      id: new FormControl(
        { value: impostoEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      diaVencimento: new FormControl(impostoEmpresaRawValue.diaVencimento),
      empresa: new FormControl(impostoEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
      imposto: new FormControl(impostoEmpresaRawValue.imposto, {
        validators: [Validators.required],
      }),
    });
  }

  getImpostoEmpresa(form: ImpostoEmpresaFormGroup): IImpostoEmpresa | NewImpostoEmpresa {
    return form.getRawValue() as IImpostoEmpresa | NewImpostoEmpresa;
  }

  resetForm(form: ImpostoEmpresaFormGroup, impostoEmpresa: ImpostoEmpresaFormGroupInput): void {
    const impostoEmpresaRawValue = { ...this.getFormDefaults(), ...impostoEmpresa };
    form.reset(
      {
        ...impostoEmpresaRawValue,
        id: { value: impostoEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ImpostoEmpresaFormDefaults {
    return {
      id: null,
    };
  }
}
