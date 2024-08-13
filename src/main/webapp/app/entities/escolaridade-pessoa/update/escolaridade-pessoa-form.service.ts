import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEscolaridadePessoa, NewEscolaridadePessoa } from '../escolaridade-pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEscolaridadePessoa for edit and NewEscolaridadePessoaFormGroupInput for create.
 */
type EscolaridadePessoaFormGroupInput = IEscolaridadePessoa | PartialWithRequiredKeyOf<NewEscolaridadePessoa>;

type EscolaridadePessoaFormDefaults = Pick<NewEscolaridadePessoa, 'id'>;

type EscolaridadePessoaFormGroupContent = {
  id: FormControl<IEscolaridadePessoa['id'] | NewEscolaridadePessoa['id']>;
  nomeInstituicao: FormControl<IEscolaridadePessoa['nomeInstituicao']>;
  anoConclusao: FormControl<IEscolaridadePessoa['anoConclusao']>;
  urlComprovanteEscolaridade: FormControl<IEscolaridadePessoa['urlComprovanteEscolaridade']>;
  pessoa: FormControl<IEscolaridadePessoa['pessoa']>;
  escolaridade: FormControl<IEscolaridadePessoa['escolaridade']>;
};

export type EscolaridadePessoaFormGroup = FormGroup<EscolaridadePessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EscolaridadePessoaFormService {
  createEscolaridadePessoaFormGroup(escolaridadePessoa: EscolaridadePessoaFormGroupInput = { id: null }): EscolaridadePessoaFormGroup {
    const escolaridadePessoaRawValue = {
      ...this.getFormDefaults(),
      ...escolaridadePessoa,
    };
    return new FormGroup<EscolaridadePessoaFormGroupContent>({
      id: new FormControl(
        { value: escolaridadePessoaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomeInstituicao: new FormControl(escolaridadePessoaRawValue.nomeInstituicao, {
        validators: [Validators.required],
      }),
      anoConclusao: new FormControl(escolaridadePessoaRawValue.anoConclusao),
      urlComprovanteEscolaridade: new FormControl(escolaridadePessoaRawValue.urlComprovanteEscolaridade),
      pessoa: new FormControl(escolaridadePessoaRawValue.pessoa, {
        validators: [Validators.required],
      }),
      escolaridade: new FormControl(escolaridadePessoaRawValue.escolaridade, {
        validators: [Validators.required],
      }),
    });
  }

  getEscolaridadePessoa(form: EscolaridadePessoaFormGroup): IEscolaridadePessoa | NewEscolaridadePessoa {
    return form.getRawValue() as IEscolaridadePessoa | NewEscolaridadePessoa;
  }

  resetForm(form: EscolaridadePessoaFormGroup, escolaridadePessoa: EscolaridadePessoaFormGroupInput): void {
    const escolaridadePessoaRawValue = { ...this.getFormDefaults(), ...escolaridadePessoa };
    form.reset(
      {
        ...escolaridadePessoaRawValue,
        id: { value: escolaridadePessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EscolaridadePessoaFormDefaults {
    return {
      id: null,
    };
  }
}
