import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRedeSocialEmpresa, NewRedeSocialEmpresa } from '../rede-social-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRedeSocialEmpresa for edit and NewRedeSocialEmpresaFormGroupInput for create.
 */
type RedeSocialEmpresaFormGroupInput = IRedeSocialEmpresa | PartialWithRequiredKeyOf<NewRedeSocialEmpresa>;

type RedeSocialEmpresaFormDefaults = Pick<NewRedeSocialEmpresa, 'id'>;

type RedeSocialEmpresaFormGroupContent = {
  id: FormControl<IRedeSocialEmpresa['id'] | NewRedeSocialEmpresa['id']>;
  perfil: FormControl<IRedeSocialEmpresa['perfil']>;
  urlPerfil: FormControl<IRedeSocialEmpresa['urlPerfil']>;
  redeSocial: FormControl<IRedeSocialEmpresa['redeSocial']>;
  pessoajuridica: FormControl<IRedeSocialEmpresa['pessoajuridica']>;
};

export type RedeSocialEmpresaFormGroup = FormGroup<RedeSocialEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RedeSocialEmpresaFormService {
  createRedeSocialEmpresaFormGroup(redeSocialEmpresa: RedeSocialEmpresaFormGroupInput = { id: null }): RedeSocialEmpresaFormGroup {
    const redeSocialEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...redeSocialEmpresa,
    };
    return new FormGroup<RedeSocialEmpresaFormGroupContent>({
      id: new FormControl(
        { value: redeSocialEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      perfil: new FormControl(redeSocialEmpresaRawValue.perfil, {
        validators: [Validators.required],
      }),
      urlPerfil: new FormControl(redeSocialEmpresaRawValue.urlPerfil),
      redeSocial: new FormControl(redeSocialEmpresaRawValue.redeSocial, {
        validators: [Validators.required],
      }),
      pessoajuridica: new FormControl(redeSocialEmpresaRawValue.pessoajuridica, {
        validators: [Validators.required],
      }),
    });
  }

  getRedeSocialEmpresa(form: RedeSocialEmpresaFormGroup): IRedeSocialEmpresa | NewRedeSocialEmpresa {
    return form.getRawValue() as IRedeSocialEmpresa | NewRedeSocialEmpresa;
  }

  resetForm(form: RedeSocialEmpresaFormGroup, redeSocialEmpresa: RedeSocialEmpresaFormGroupInput): void {
    const redeSocialEmpresaRawValue = { ...this.getFormDefaults(), ...redeSocialEmpresa };
    form.reset(
      {
        ...redeSocialEmpresaRawValue,
        id: { value: redeSocialEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RedeSocialEmpresaFormDefaults {
    return {
      id: null,
    };
  }
}
