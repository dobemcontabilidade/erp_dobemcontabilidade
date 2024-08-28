import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEnderecoPessoa, NewEnderecoPessoa } from '../endereco-pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEnderecoPessoa for edit and NewEnderecoPessoaFormGroupInput for create.
 */
type EnderecoPessoaFormGroupInput = IEnderecoPessoa | PartialWithRequiredKeyOf<NewEnderecoPessoa>;

type EnderecoPessoaFormDefaults = Pick<NewEnderecoPessoa, 'id' | 'principal'>;

type EnderecoPessoaFormGroupContent = {
  id: FormControl<IEnderecoPessoa['id'] | NewEnderecoPessoa['id']>;
  logradouro: FormControl<IEnderecoPessoa['logradouro']>;
  numero: FormControl<IEnderecoPessoa['numero']>;
  complemento: FormControl<IEnderecoPessoa['complemento']>;
  bairro: FormControl<IEnderecoPessoa['bairro']>;
  cep: FormControl<IEnderecoPessoa['cep']>;
  principal: FormControl<IEnderecoPessoa['principal']>;
  pessoa: FormControl<IEnderecoPessoa['pessoa']>;
  cidade: FormControl<IEnderecoPessoa['cidade']>;
};

export type EnderecoPessoaFormGroup = FormGroup<EnderecoPessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EnderecoPessoaFormService {
  createEnderecoPessoaFormGroup(enderecoPessoa: EnderecoPessoaFormGroupInput = { id: null }): EnderecoPessoaFormGroup {
    const enderecoPessoaRawValue = {
      ...this.getFormDefaults(),
      ...enderecoPessoa,
    };
    return new FormGroup<EnderecoPessoaFormGroupContent>({
      id: new FormControl(
        { value: enderecoPessoaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      logradouro: new FormControl(enderecoPessoaRawValue.logradouro),
      numero: new FormControl(enderecoPessoaRawValue.numero),
      complemento: new FormControl(enderecoPessoaRawValue.complemento),
      bairro: new FormControl(enderecoPessoaRawValue.bairro),
      cep: new FormControl(enderecoPessoaRawValue.cep),
      principal: new FormControl(enderecoPessoaRawValue.principal),
      pessoa: new FormControl(enderecoPessoaRawValue.pessoa, {
        validators: [Validators.required],
      }),
      cidade: new FormControl(enderecoPessoaRawValue.cidade, {
        validators: [Validators.required],
      }),
    });
  }

  getEnderecoPessoa(form: EnderecoPessoaFormGroup): IEnderecoPessoa | NewEnderecoPessoa {
    return form.getRawValue() as IEnderecoPessoa | NewEnderecoPessoa;
  }

  resetForm(form: EnderecoPessoaFormGroup, enderecoPessoa: EnderecoPessoaFormGroupInput): void {
    const enderecoPessoaRawValue = { ...this.getFormDefaults(), ...enderecoPessoa };
    form.reset(
      {
        ...enderecoPessoaRawValue,
        id: { value: enderecoPessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EnderecoPessoaFormDefaults {
    return {
      id: null,
      principal: false,
    };
  }
}
