import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPerfilAcesso, NewPerfilAcesso } from '../perfil-acesso.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerfilAcesso for edit and NewPerfilAcessoFormGroupInput for create.
 */
type PerfilAcessoFormGroupInput = IPerfilAcesso | PartialWithRequiredKeyOf<NewPerfilAcesso>;

type PerfilAcessoFormDefaults = Pick<NewPerfilAcesso, 'id'>;

type PerfilAcessoFormGroupContent = {
  id: FormControl<IPerfilAcesso['id'] | NewPerfilAcesso['id']>;
  nome: FormControl<IPerfilAcesso['nome']>;
  descricao: FormControl<IPerfilAcesso['descricao']>;
};

export type PerfilAcessoFormGroup = FormGroup<PerfilAcessoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PerfilAcessoFormService {
  createPerfilAcessoFormGroup(perfilAcesso: PerfilAcessoFormGroupInput = { id: null }): PerfilAcessoFormGroup {
    const perfilAcessoRawValue = {
      ...this.getFormDefaults(),
      ...perfilAcesso,
    };
    return new FormGroup<PerfilAcessoFormGroupContent>({
      id: new FormControl(
        { value: perfilAcessoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(perfilAcessoRawValue.nome),
      descricao: new FormControl(perfilAcessoRawValue.descricao),
    });
  }

  getPerfilAcesso(form: PerfilAcessoFormGroup): IPerfilAcesso | NewPerfilAcesso {
    return form.getRawValue() as IPerfilAcesso | NewPerfilAcesso;
  }

  resetForm(form: PerfilAcessoFormGroup, perfilAcesso: PerfilAcessoFormGroupInput): void {
    const perfilAcessoRawValue = { ...this.getFormDefaults(), ...perfilAcesso };
    form.reset(
      {
        ...perfilAcessoRawValue,
        id: { value: perfilAcessoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PerfilAcessoFormDefaults {
    return {
      id: null,
    };
  }
}
