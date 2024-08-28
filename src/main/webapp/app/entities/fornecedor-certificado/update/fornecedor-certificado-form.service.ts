import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFornecedorCertificado, NewFornecedorCertificado } from '../fornecedor-certificado.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFornecedorCertificado for edit and NewFornecedorCertificadoFormGroupInput for create.
 */
type FornecedorCertificadoFormGroupInput = IFornecedorCertificado | PartialWithRequiredKeyOf<NewFornecedorCertificado>;

type FornecedorCertificadoFormDefaults = Pick<NewFornecedorCertificado, 'id'>;

type FornecedorCertificadoFormGroupContent = {
  id: FormControl<IFornecedorCertificado['id'] | NewFornecedorCertificado['id']>;
  razaoSocial: FormControl<IFornecedorCertificado['razaoSocial']>;
  sigla: FormControl<IFornecedorCertificado['sigla']>;
  descricao: FormControl<IFornecedorCertificado['descricao']>;
};

export type FornecedorCertificadoFormGroup = FormGroup<FornecedorCertificadoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FornecedorCertificadoFormService {
  createFornecedorCertificadoFormGroup(
    fornecedorCertificado: FornecedorCertificadoFormGroupInput = { id: null },
  ): FornecedorCertificadoFormGroup {
    const fornecedorCertificadoRawValue = {
      ...this.getFormDefaults(),
      ...fornecedorCertificado,
    };
    return new FormGroup<FornecedorCertificadoFormGroupContent>({
      id: new FormControl(
        { value: fornecedorCertificadoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      razaoSocial: new FormControl(fornecedorCertificadoRawValue.razaoSocial, {
        validators: [Validators.required],
      }),
      sigla: new FormControl(fornecedorCertificadoRawValue.sigla, {
        validators: [Validators.maxLength(20)],
      }),
      descricao: new FormControl(fornecedorCertificadoRawValue.descricao),
    });
  }

  getFornecedorCertificado(form: FornecedorCertificadoFormGroup): IFornecedorCertificado | NewFornecedorCertificado {
    return form.getRawValue() as IFornecedorCertificado | NewFornecedorCertificado;
  }

  resetForm(form: FornecedorCertificadoFormGroup, fornecedorCertificado: FornecedorCertificadoFormGroupInput): void {
    const fornecedorCertificadoRawValue = { ...this.getFormDefaults(), ...fornecedorCertificado };
    form.reset(
      {
        ...fornecedorCertificadoRawValue,
        id: { value: fornecedorCertificadoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FornecedorCertificadoFormDefaults {
    return {
      id: null,
    };
  }
}
