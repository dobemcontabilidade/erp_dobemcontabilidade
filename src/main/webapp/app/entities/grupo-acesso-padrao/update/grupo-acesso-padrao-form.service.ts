import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGrupoAcessoPadrao, NewGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGrupoAcessoPadrao for edit and NewGrupoAcessoPadraoFormGroupInput for create.
 */
type GrupoAcessoPadraoFormGroupInput = IGrupoAcessoPadrao | PartialWithRequiredKeyOf<NewGrupoAcessoPadrao>;

type GrupoAcessoPadraoFormDefaults = Pick<NewGrupoAcessoPadrao, 'id'>;

type GrupoAcessoPadraoFormGroupContent = {
  id: FormControl<IGrupoAcessoPadrao['id'] | NewGrupoAcessoPadrao['id']>;
  nome: FormControl<IGrupoAcessoPadrao['nome']>;
};

export type GrupoAcessoPadraoFormGroup = FormGroup<GrupoAcessoPadraoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoPadraoFormService {
  createGrupoAcessoPadraoFormGroup(grupoAcessoPadrao: GrupoAcessoPadraoFormGroupInput = { id: null }): GrupoAcessoPadraoFormGroup {
    const grupoAcessoPadraoRawValue = {
      ...this.getFormDefaults(),
      ...grupoAcessoPadrao,
    };
    return new FormGroup<GrupoAcessoPadraoFormGroupContent>({
      id: new FormControl(
        { value: grupoAcessoPadraoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(grupoAcessoPadraoRawValue.nome),
    });
  }

  getGrupoAcessoPadrao(form: GrupoAcessoPadraoFormGroup): IGrupoAcessoPadrao | NewGrupoAcessoPadrao {
    return form.getRawValue() as IGrupoAcessoPadrao | NewGrupoAcessoPadrao;
  }

  resetForm(form: GrupoAcessoPadraoFormGroup, grupoAcessoPadrao: GrupoAcessoPadraoFormGroupInput): void {
    const grupoAcessoPadraoRawValue = { ...this.getFormDefaults(), ...grupoAcessoPadrao };
    form.reset(
      {
        ...grupoAcessoPadraoRawValue,
        id: { value: grupoAcessoPadraoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GrupoAcessoPadraoFormDefaults {
    return {
      id: null,
    };
  }
}
