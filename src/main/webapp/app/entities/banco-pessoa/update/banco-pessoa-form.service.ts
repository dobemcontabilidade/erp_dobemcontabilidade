import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBancoPessoa, NewBancoPessoa } from '../banco-pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBancoPessoa for edit and NewBancoPessoaFormGroupInput for create.
 */
type BancoPessoaFormGroupInput = IBancoPessoa | PartialWithRequiredKeyOf<NewBancoPessoa>;

type BancoPessoaFormDefaults = Pick<NewBancoPessoa, 'id' | 'principal'>;

type BancoPessoaFormGroupContent = {
  id: FormControl<IBancoPessoa['id'] | NewBancoPessoa['id']>;
  agencia: FormControl<IBancoPessoa['agencia']>;
  conta: FormControl<IBancoPessoa['conta']>;
  tipoConta: FormControl<IBancoPessoa['tipoConta']>;
  principal: FormControl<IBancoPessoa['principal']>;
  pessoa: FormControl<IBancoPessoa['pessoa']>;
  banco: FormControl<IBancoPessoa['banco']>;
};

export type BancoPessoaFormGroup = FormGroup<BancoPessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BancoPessoaFormService {
  createBancoPessoaFormGroup(bancoPessoa: BancoPessoaFormGroupInput = { id: null }): BancoPessoaFormGroup {
    const bancoPessoaRawValue = {
      ...this.getFormDefaults(),
      ...bancoPessoa,
    };
    return new FormGroup<BancoPessoaFormGroupContent>({
      id: new FormControl(
        { value: bancoPessoaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      agencia: new FormControl(bancoPessoaRawValue.agencia, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      conta: new FormControl(bancoPessoaRawValue.conta, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      tipoConta: new FormControl(bancoPessoaRawValue.tipoConta),
      principal: new FormControl(bancoPessoaRawValue.principal),
      pessoa: new FormControl(bancoPessoaRawValue.pessoa, {
        validators: [Validators.required],
      }),
      banco: new FormControl(bancoPessoaRawValue.banco, {
        validators: [Validators.required],
      }),
    });
  }

  getBancoPessoa(form: BancoPessoaFormGroup): IBancoPessoa | NewBancoPessoa {
    return form.getRawValue() as IBancoPessoa | NewBancoPessoa;
  }

  resetForm(form: BancoPessoaFormGroup, bancoPessoa: BancoPessoaFormGroupInput): void {
    const bancoPessoaRawValue = { ...this.getFormDefaults(), ...bancoPessoa };
    form.reset(
      {
        ...bancoPessoaRawValue,
        id: { value: bancoPessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BancoPessoaFormDefaults {
    return {
      id: null,
      principal: false,
    };
  }
}
