import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnexoEmpresa, NewAnexoEmpresa } from '../anexo-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoEmpresa for edit and NewAnexoEmpresaFormGroupInput for create.
 */
type AnexoEmpresaFormGroupInput = IAnexoEmpresa | PartialWithRequiredKeyOf<NewAnexoEmpresa>;

type AnexoEmpresaFormDefaults = Pick<NewAnexoEmpresa, 'id'>;

type AnexoEmpresaFormGroupContent = {
  id: FormControl<IAnexoEmpresa['id'] | NewAnexoEmpresa['id']>;
  urlAnexo: FormControl<IAnexoEmpresa['urlAnexo']>;
  empresa: FormControl<IAnexoEmpresa['empresa']>;
  anexoRequeridoEmpresa: FormControl<IAnexoEmpresa['anexoRequeridoEmpresa']>;
};

export type AnexoEmpresaFormGroup = FormGroup<AnexoEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoEmpresaFormService {
  createAnexoEmpresaFormGroup(anexoEmpresa: AnexoEmpresaFormGroupInput = { id: null }): AnexoEmpresaFormGroup {
    const anexoEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...anexoEmpresa,
    };
    return new FormGroup<AnexoEmpresaFormGroupContent>({
      id: new FormControl(
        { value: anexoEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      urlAnexo: new FormControl(anexoEmpresaRawValue.urlAnexo, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(anexoEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
      anexoRequeridoEmpresa: new FormControl(anexoEmpresaRawValue.anexoRequeridoEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoEmpresa(form: AnexoEmpresaFormGroup): IAnexoEmpresa | NewAnexoEmpresa {
    return form.getRawValue() as IAnexoEmpresa | NewAnexoEmpresa;
  }

  resetForm(form: AnexoEmpresaFormGroup, anexoEmpresa: AnexoEmpresaFormGroupInput): void {
    const anexoEmpresaRawValue = { ...this.getFormDefaults(), ...anexoEmpresa };
    form.reset(
      {
        ...anexoEmpresaRawValue,
        id: { value: anexoEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoEmpresaFormDefaults {
    return {
      id: null,
    };
  }
}
