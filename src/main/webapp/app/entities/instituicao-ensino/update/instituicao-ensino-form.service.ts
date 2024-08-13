import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInstituicaoEnsino, NewInstituicaoEnsino } from '../instituicao-ensino.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInstituicaoEnsino for edit and NewInstituicaoEnsinoFormGroupInput for create.
 */
type InstituicaoEnsinoFormGroupInput = IInstituicaoEnsino | PartialWithRequiredKeyOf<NewInstituicaoEnsino>;

type InstituicaoEnsinoFormDefaults = Pick<NewInstituicaoEnsino, 'id' | 'principal'>;

type InstituicaoEnsinoFormGroupContent = {
  id: FormControl<IInstituicaoEnsino['id'] | NewInstituicaoEnsino['id']>;
  nome: FormControl<IInstituicaoEnsino['nome']>;
  cnpj: FormControl<IInstituicaoEnsino['cnpj']>;
  logradouro: FormControl<IInstituicaoEnsino['logradouro']>;
  numero: FormControl<IInstituicaoEnsino['numero']>;
  complemento: FormControl<IInstituicaoEnsino['complemento']>;
  bairro: FormControl<IInstituicaoEnsino['bairro']>;
  cep: FormControl<IInstituicaoEnsino['cep']>;
  principal: FormControl<IInstituicaoEnsino['principal']>;
  cidade: FormControl<IInstituicaoEnsino['cidade']>;
};

export type InstituicaoEnsinoFormGroup = FormGroup<InstituicaoEnsinoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InstituicaoEnsinoFormService {
  createInstituicaoEnsinoFormGroup(instituicaoEnsino: InstituicaoEnsinoFormGroupInput = { id: null }): InstituicaoEnsinoFormGroup {
    const instituicaoEnsinoRawValue = {
      ...this.getFormDefaults(),
      ...instituicaoEnsino,
    };
    return new FormGroup<InstituicaoEnsinoFormGroupContent>({
      id: new FormControl(
        { value: instituicaoEnsinoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(instituicaoEnsinoRawValue.nome, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      cnpj: new FormControl(instituicaoEnsinoRawValue.cnpj),
      logradouro: new FormControl(instituicaoEnsinoRawValue.logradouro),
      numero: new FormControl(instituicaoEnsinoRawValue.numero, {
        validators: [Validators.maxLength(5)],
      }),
      complemento: new FormControl(instituicaoEnsinoRawValue.complemento, {
        validators: [Validators.maxLength(100)],
      }),
      bairro: new FormControl(instituicaoEnsinoRawValue.bairro),
      cep: new FormControl(instituicaoEnsinoRawValue.cep, {
        validators: [Validators.maxLength(9)],
      }),
      principal: new FormControl(instituicaoEnsinoRawValue.principal),
      cidade: new FormControl(instituicaoEnsinoRawValue.cidade, {
        validators: [Validators.required],
      }),
    });
  }

  getInstituicaoEnsino(form: InstituicaoEnsinoFormGroup): IInstituicaoEnsino | NewInstituicaoEnsino {
    return form.getRawValue() as IInstituicaoEnsino | NewInstituicaoEnsino;
  }

  resetForm(form: InstituicaoEnsinoFormGroup, instituicaoEnsino: InstituicaoEnsinoFormGroupInput): void {
    const instituicaoEnsinoRawValue = { ...this.getFormDefaults(), ...instituicaoEnsino };
    form.reset(
      {
        ...instituicaoEnsinoRawValue,
        id: { value: instituicaoEnsinoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): InstituicaoEnsinoFormDefaults {
    return {
      id: null,
      principal: false,
    };
  }
}
