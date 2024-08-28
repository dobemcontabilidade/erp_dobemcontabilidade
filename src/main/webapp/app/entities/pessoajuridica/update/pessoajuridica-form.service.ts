import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPessoajuridica, NewPessoajuridica } from '../pessoajuridica.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPessoajuridica for edit and NewPessoajuridicaFormGroupInput for create.
 */
type PessoajuridicaFormGroupInput = IPessoajuridica | PartialWithRequiredKeyOf<NewPessoajuridica>;

type PessoajuridicaFormDefaults = Pick<NewPessoajuridica, 'id'>;

type PessoajuridicaFormGroupContent = {
  id: FormControl<IPessoajuridica['id'] | NewPessoajuridica['id']>;
  razaoSocial: FormControl<IPessoajuridica['razaoSocial']>;
  nomeFantasia: FormControl<IPessoajuridica['nomeFantasia']>;
  cnpj: FormControl<IPessoajuridica['cnpj']>;
};

export type PessoajuridicaFormGroup = FormGroup<PessoajuridicaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PessoajuridicaFormService {
  createPessoajuridicaFormGroup(pessoajuridica: PessoajuridicaFormGroupInput = { id: null }): PessoajuridicaFormGroup {
    const pessoajuridicaRawValue = {
      ...this.getFormDefaults(),
      ...pessoajuridica,
    };
    return new FormGroup<PessoajuridicaFormGroupContent>({
      id: new FormControl(
        { value: pessoajuridicaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      razaoSocial: new FormControl(pessoajuridicaRawValue.razaoSocial, {
        validators: [Validators.required],
      }),
      nomeFantasia: new FormControl(pessoajuridicaRawValue.nomeFantasia, {
        validators: [Validators.required],
      }),
      cnpj: new FormControl(pessoajuridicaRawValue.cnpj, {
        validators: [Validators.maxLength(20)],
      }),
    });
  }

  getPessoajuridica(form: PessoajuridicaFormGroup): IPessoajuridica | NewPessoajuridica {
    return form.getRawValue() as IPessoajuridica | NewPessoajuridica;
  }

  resetForm(form: PessoajuridicaFormGroup, pessoajuridica: PessoajuridicaFormGroupInput): void {
    const pessoajuridicaRawValue = { ...this.getFormDefaults(), ...pessoajuridica };
    form.reset(
      {
        ...pessoajuridicaRawValue,
        id: { value: pessoajuridicaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PessoajuridicaFormDefaults {
    return {
      id: null,
    };
  }
}
