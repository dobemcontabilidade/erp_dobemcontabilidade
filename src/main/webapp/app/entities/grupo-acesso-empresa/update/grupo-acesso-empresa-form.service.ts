import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGrupoAcessoEmpresa, NewGrupoAcessoEmpresa } from '../grupo-acesso-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGrupoAcessoEmpresa for edit and NewGrupoAcessoEmpresaFormGroupInput for create.
 */
type GrupoAcessoEmpresaFormGroupInput = IGrupoAcessoEmpresa | PartialWithRequiredKeyOf<NewGrupoAcessoEmpresa>;

type GrupoAcessoEmpresaFormDefaults = Pick<NewGrupoAcessoEmpresa, 'id'>;

type GrupoAcessoEmpresaFormGroupContent = {
  id: FormControl<IGrupoAcessoEmpresa['id'] | NewGrupoAcessoEmpresa['id']>;
  nome: FormControl<IGrupoAcessoEmpresa['nome']>;
  assinaturaEmpresa: FormControl<IGrupoAcessoEmpresa['assinaturaEmpresa']>;
};

export type GrupoAcessoEmpresaFormGroup = FormGroup<GrupoAcessoEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoEmpresaFormService {
  createGrupoAcessoEmpresaFormGroup(grupoAcessoEmpresa: GrupoAcessoEmpresaFormGroupInput = { id: null }): GrupoAcessoEmpresaFormGroup {
    const grupoAcessoEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...grupoAcessoEmpresa,
    };
    return new FormGroup<GrupoAcessoEmpresaFormGroupContent>({
      id: new FormControl(
        { value: grupoAcessoEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(grupoAcessoEmpresaRawValue.nome),
      assinaturaEmpresa: new FormControl(grupoAcessoEmpresaRawValue.assinaturaEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getGrupoAcessoEmpresa(form: GrupoAcessoEmpresaFormGroup): IGrupoAcessoEmpresa | NewGrupoAcessoEmpresa {
    return form.getRawValue() as IGrupoAcessoEmpresa | NewGrupoAcessoEmpresa;
  }

  resetForm(form: GrupoAcessoEmpresaFormGroup, grupoAcessoEmpresa: GrupoAcessoEmpresaFormGroupInput): void {
    const grupoAcessoEmpresaRawValue = { ...this.getFormDefaults(), ...grupoAcessoEmpresa };
    form.reset(
      {
        ...grupoAcessoEmpresaRawValue,
        id: { value: grupoAcessoEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GrupoAcessoEmpresaFormDefaults {
    return {
      id: null,
    };
  }
}
