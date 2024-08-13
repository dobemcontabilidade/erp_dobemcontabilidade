import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEnderecoEmpresa, NewEnderecoEmpresa } from '../endereco-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEnderecoEmpresa for edit and NewEnderecoEmpresaFormGroupInput for create.
 */
type EnderecoEmpresaFormGroupInput = IEnderecoEmpresa | PartialWithRequiredKeyOf<NewEnderecoEmpresa>;

type EnderecoEmpresaFormDefaults = Pick<NewEnderecoEmpresa, 'id' | 'principal' | 'filial' | 'enderecoFiscal'>;

type EnderecoEmpresaFormGroupContent = {
  id: FormControl<IEnderecoEmpresa['id'] | NewEnderecoEmpresa['id']>;
  logradouro: FormControl<IEnderecoEmpresa['logradouro']>;
  numero: FormControl<IEnderecoEmpresa['numero']>;
  complemento: FormControl<IEnderecoEmpresa['complemento']>;
  bairro: FormControl<IEnderecoEmpresa['bairro']>;
  cep: FormControl<IEnderecoEmpresa['cep']>;
  principal: FormControl<IEnderecoEmpresa['principal']>;
  filial: FormControl<IEnderecoEmpresa['filial']>;
  enderecoFiscal: FormControl<IEnderecoEmpresa['enderecoFiscal']>;
  empresa: FormControl<IEnderecoEmpresa['empresa']>;
  cidade: FormControl<IEnderecoEmpresa['cidade']>;
};

export type EnderecoEmpresaFormGroup = FormGroup<EnderecoEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EnderecoEmpresaFormService {
  createEnderecoEmpresaFormGroup(enderecoEmpresa: EnderecoEmpresaFormGroupInput = { id: null }): EnderecoEmpresaFormGroup {
    const enderecoEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...enderecoEmpresa,
    };
    return new FormGroup<EnderecoEmpresaFormGroupContent>({
      id: new FormControl(
        { value: enderecoEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      logradouro: new FormControl(enderecoEmpresaRawValue.logradouro),
      numero: new FormControl(enderecoEmpresaRawValue.numero, {
        validators: [Validators.maxLength(5)],
      }),
      complemento: new FormControl(enderecoEmpresaRawValue.complemento, {
        validators: [Validators.maxLength(100)],
      }),
      bairro: new FormControl(enderecoEmpresaRawValue.bairro),
      cep: new FormControl(enderecoEmpresaRawValue.cep, {
        validators: [Validators.maxLength(9)],
      }),
      principal: new FormControl(enderecoEmpresaRawValue.principal),
      filial: new FormControl(enderecoEmpresaRawValue.filial),
      enderecoFiscal: new FormControl(enderecoEmpresaRawValue.enderecoFiscal),
      empresa: new FormControl(enderecoEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
      cidade: new FormControl(enderecoEmpresaRawValue.cidade, {
        validators: [Validators.required],
      }),
    });
  }

  getEnderecoEmpresa(form: EnderecoEmpresaFormGroup): IEnderecoEmpresa | NewEnderecoEmpresa {
    return form.getRawValue() as IEnderecoEmpresa | NewEnderecoEmpresa;
  }

  resetForm(form: EnderecoEmpresaFormGroup, enderecoEmpresa: EnderecoEmpresaFormGroupInput): void {
    const enderecoEmpresaRawValue = { ...this.getFormDefaults(), ...enderecoEmpresa };
    form.reset(
      {
        ...enderecoEmpresaRawValue,
        id: { value: enderecoEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EnderecoEmpresaFormDefaults {
    return {
      id: null,
      principal: false,
      filial: false,
      enderecoFiscal: false,
    };
  }
}
