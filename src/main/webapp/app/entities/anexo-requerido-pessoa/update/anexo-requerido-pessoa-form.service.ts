import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnexoRequeridoPessoa, NewAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoRequeridoPessoa for edit and NewAnexoRequeridoPessoaFormGroupInput for create.
 */
type AnexoRequeridoPessoaFormGroupInput = IAnexoRequeridoPessoa | PartialWithRequiredKeyOf<NewAnexoRequeridoPessoa>;

type AnexoRequeridoPessoaFormDefaults = Pick<NewAnexoRequeridoPessoa, 'id' | 'obrigatorio'>;

type AnexoRequeridoPessoaFormGroupContent = {
  id: FormControl<IAnexoRequeridoPessoa['id'] | NewAnexoRequeridoPessoa['id']>;
  obrigatorio: FormControl<IAnexoRequeridoPessoa['obrigatorio']>;
  tipo: FormControl<IAnexoRequeridoPessoa['tipo']>;
  anexoPessoa: FormControl<IAnexoRequeridoPessoa['anexoPessoa']>;
  anexoRequerido: FormControl<IAnexoRequeridoPessoa['anexoRequerido']>;
};

export type AnexoRequeridoPessoaFormGroup = FormGroup<AnexoRequeridoPessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoPessoaFormService {
  createAnexoRequeridoPessoaFormGroup(
    anexoRequeridoPessoa: AnexoRequeridoPessoaFormGroupInput = { id: null },
  ): AnexoRequeridoPessoaFormGroup {
    const anexoRequeridoPessoaRawValue = {
      ...this.getFormDefaults(),
      ...anexoRequeridoPessoa,
    };
    return new FormGroup<AnexoRequeridoPessoaFormGroupContent>({
      id: new FormControl(
        { value: anexoRequeridoPessoaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      obrigatorio: new FormControl(anexoRequeridoPessoaRawValue.obrigatorio),
      tipo: new FormControl(anexoRequeridoPessoaRawValue.tipo),
      anexoPessoa: new FormControl(anexoRequeridoPessoaRawValue.anexoPessoa, {
        validators: [Validators.required],
      }),
      anexoRequerido: new FormControl(anexoRequeridoPessoaRawValue.anexoRequerido, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoRequeridoPessoa(form: AnexoRequeridoPessoaFormGroup): IAnexoRequeridoPessoa | NewAnexoRequeridoPessoa {
    return form.getRawValue() as IAnexoRequeridoPessoa | NewAnexoRequeridoPessoa;
  }

  resetForm(form: AnexoRequeridoPessoaFormGroup, anexoRequeridoPessoa: AnexoRequeridoPessoaFormGroupInput): void {
    const anexoRequeridoPessoaRawValue = { ...this.getFormDefaults(), ...anexoRequeridoPessoa };
    form.reset(
      {
        ...anexoRequeridoPessoaRawValue,
        id: { value: anexoRequeridoPessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoRequeridoPessoaFormDefaults {
    return {
      id: null,
      obrigatorio: false,
    };
  }
}
