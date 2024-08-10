import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnexoPessoa, NewAnexoPessoa } from '../anexo-pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoPessoa for edit and NewAnexoPessoaFormGroupInput for create.
 */
type AnexoPessoaFormGroupInput = IAnexoPessoa | PartialWithRequiredKeyOf<NewAnexoPessoa>;

type AnexoPessoaFormDefaults = Pick<NewAnexoPessoa, 'id'>;

type AnexoPessoaFormGroupContent = {
  id: FormControl<IAnexoPessoa['id'] | NewAnexoPessoa['id']>;
  urlArquivo: FormControl<IAnexoPessoa['urlArquivo']>;
  tipo: FormControl<IAnexoPessoa['tipo']>;
  descricao: FormControl<IAnexoPessoa['descricao']>;
  pessoa: FormControl<IAnexoPessoa['pessoa']>;
};

export type AnexoPessoaFormGroup = FormGroup<AnexoPessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoPessoaFormService {
  createAnexoPessoaFormGroup(anexoPessoa: AnexoPessoaFormGroupInput = { id: null }): AnexoPessoaFormGroup {
    const anexoPessoaRawValue = {
      ...this.getFormDefaults(),
      ...anexoPessoa,
    };
    return new FormGroup<AnexoPessoaFormGroupContent>({
      id: new FormControl(
        { value: anexoPessoaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      urlArquivo: new FormControl(anexoPessoaRawValue.urlArquivo, {
        validators: [Validators.required],
      }),
      tipo: new FormControl(anexoPessoaRawValue.tipo),
      descricao: new FormControl(anexoPessoaRawValue.descricao),
      pessoa: new FormControl(anexoPessoaRawValue.pessoa, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoPessoa(form: AnexoPessoaFormGroup): IAnexoPessoa | NewAnexoPessoa {
    return form.getRawValue() as IAnexoPessoa | NewAnexoPessoa;
  }

  resetForm(form: AnexoPessoaFormGroup, anexoPessoa: AnexoPessoaFormGroupInput): void {
    const anexoPessoaRawValue = { ...this.getFormDefaults(), ...anexoPessoa };
    form.reset(
      {
        ...anexoPessoaRawValue,
        id: { value: anexoPessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoPessoaFormDefaults {
    return {
      id: null,
    };
  }
}
