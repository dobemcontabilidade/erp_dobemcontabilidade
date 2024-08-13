import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPerfilRedeSocial, NewPerfilRedeSocial } from '../perfil-rede-social.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerfilRedeSocial for edit and NewPerfilRedeSocialFormGroupInput for create.
 */
type PerfilRedeSocialFormGroupInput = IPerfilRedeSocial | PartialWithRequiredKeyOf<NewPerfilRedeSocial>;

type PerfilRedeSocialFormDefaults = Pick<NewPerfilRedeSocial, 'id'>;

type PerfilRedeSocialFormGroupContent = {
  id: FormControl<IPerfilRedeSocial['id'] | NewPerfilRedeSocial['id']>;
  rede: FormControl<IPerfilRedeSocial['rede']>;
  urlPerfil: FormControl<IPerfilRedeSocial['urlPerfil']>;
  tipoRede: FormControl<IPerfilRedeSocial['tipoRede']>;
};

export type PerfilRedeSocialFormGroup = FormGroup<PerfilRedeSocialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PerfilRedeSocialFormService {
  createPerfilRedeSocialFormGroup(perfilRedeSocial: PerfilRedeSocialFormGroupInput = { id: null }): PerfilRedeSocialFormGroup {
    const perfilRedeSocialRawValue = {
      ...this.getFormDefaults(),
      ...perfilRedeSocial,
    };
    return new FormGroup<PerfilRedeSocialFormGroupContent>({
      id: new FormControl(
        { value: perfilRedeSocialRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      rede: new FormControl(perfilRedeSocialRawValue.rede, {
        validators: [Validators.required],
      }),
      urlPerfil: new FormControl(perfilRedeSocialRawValue.urlPerfil, {
        validators: [Validators.required],
      }),
      tipoRede: new FormControl(perfilRedeSocialRawValue.tipoRede),
    });
  }

  getPerfilRedeSocial(form: PerfilRedeSocialFormGroup): IPerfilRedeSocial | NewPerfilRedeSocial {
    return form.getRawValue() as IPerfilRedeSocial | NewPerfilRedeSocial;
  }

  resetForm(form: PerfilRedeSocialFormGroup, perfilRedeSocial: PerfilRedeSocialFormGroupInput): void {
    const perfilRedeSocialRawValue = { ...this.getFormDefaults(), ...perfilRedeSocial };
    form.reset(
      {
        ...perfilRedeSocialRawValue,
        id: { value: perfilRedeSocialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PerfilRedeSocialFormDefaults {
    return {
      id: null,
    };
  }
}
