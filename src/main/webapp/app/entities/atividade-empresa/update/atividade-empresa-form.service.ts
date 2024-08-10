import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAtividadeEmpresa, NewAtividadeEmpresa } from '../atividade-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAtividadeEmpresa for edit and NewAtividadeEmpresaFormGroupInput for create.
 */
type AtividadeEmpresaFormGroupInput = IAtividadeEmpresa | PartialWithRequiredKeyOf<NewAtividadeEmpresa>;

type AtividadeEmpresaFormDefaults = Pick<NewAtividadeEmpresa, 'id' | 'principal'>;

type AtividadeEmpresaFormGroupContent = {
  id: FormControl<IAtividadeEmpresa['id'] | NewAtividadeEmpresa['id']>;
  principal: FormControl<IAtividadeEmpresa['principal']>;
  ordem: FormControl<IAtividadeEmpresa['ordem']>;
  descricaoAtividade: FormControl<IAtividadeEmpresa['descricaoAtividade']>;
  empresa: FormControl<IAtividadeEmpresa['empresa']>;
};

export type AtividadeEmpresaFormGroup = FormGroup<AtividadeEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AtividadeEmpresaFormService {
  createAtividadeEmpresaFormGroup(atividadeEmpresa: AtividadeEmpresaFormGroupInput = { id: null }): AtividadeEmpresaFormGroup {
    const atividadeEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...atividadeEmpresa,
    };
    return new FormGroup<AtividadeEmpresaFormGroupContent>({
      id: new FormControl(
        { value: atividadeEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      principal: new FormControl(atividadeEmpresaRawValue.principal),
      ordem: new FormControl(atividadeEmpresaRawValue.ordem),
      descricaoAtividade: new FormControl(atividadeEmpresaRawValue.descricaoAtividade),
      empresa: new FormControl(atividadeEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getAtividadeEmpresa(form: AtividadeEmpresaFormGroup): IAtividadeEmpresa | NewAtividadeEmpresa {
    return form.getRawValue() as IAtividadeEmpresa | NewAtividadeEmpresa;
  }

  resetForm(form: AtividadeEmpresaFormGroup, atividadeEmpresa: AtividadeEmpresaFormGroupInput): void {
    const atividadeEmpresaRawValue = { ...this.getFormDefaults(), ...atividadeEmpresa };
    form.reset(
      {
        ...atividadeEmpresaRawValue,
        id: { value: atividadeEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AtividadeEmpresaFormDefaults {
    return {
      id: null,
      principal: false,
    };
  }
}
