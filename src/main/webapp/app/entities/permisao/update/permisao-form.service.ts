import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPermisao, NewPermisao } from '../permisao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPermisao for edit and NewPermisaoFormGroupInput for create.
 */
type PermisaoFormGroupInput = IPermisao | PartialWithRequiredKeyOf<NewPermisao>;

type PermisaoFormDefaults = Pick<NewPermisao, 'id'>;

type PermisaoFormGroupContent = {
  id: FormControl<IPermisao['id'] | NewPermisao['id']>;
  nome: FormControl<IPermisao['nome']>;
  descricao: FormControl<IPermisao['descricao']>;
  label: FormControl<IPermisao['label']>;
};

export type PermisaoFormGroup = FormGroup<PermisaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PermisaoFormService {
  createPermisaoFormGroup(permisao: PermisaoFormGroupInput = { id: null }): PermisaoFormGroup {
    const permisaoRawValue = {
      ...this.getFormDefaults(),
      ...permisao,
    };
    return new FormGroup<PermisaoFormGroupContent>({
      id: new FormControl(
        { value: permisaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(permisaoRawValue.nome),
      descricao: new FormControl(permisaoRawValue.descricao),
      label: new FormControl(permisaoRawValue.label),
    });
  }

  getPermisao(form: PermisaoFormGroup): IPermisao | NewPermisao {
    return form.getRawValue() as IPermisao | NewPermisao;
  }

  resetForm(form: PermisaoFormGroup, permisao: PermisaoFormGroupInput): void {
    const permisaoRawValue = { ...this.getFormDefaults(), ...permisao };
    form.reset(
      {
        ...permisaoRawValue,
        id: { value: permisaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PermisaoFormDefaults {
    return {
      id: null,
    };
  }
}
