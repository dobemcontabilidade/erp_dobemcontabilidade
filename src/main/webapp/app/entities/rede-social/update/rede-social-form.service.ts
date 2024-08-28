import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRedeSocial, NewRedeSocial } from '../rede-social.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRedeSocial for edit and NewRedeSocialFormGroupInput for create.
 */
type RedeSocialFormGroupInput = IRedeSocial | PartialWithRequiredKeyOf<NewRedeSocial>;

type RedeSocialFormDefaults = Pick<NewRedeSocial, 'id'>;

type RedeSocialFormGroupContent = {
  id: FormControl<IRedeSocial['id'] | NewRedeSocial['id']>;
  nome: FormControl<IRedeSocial['nome']>;
  descricao: FormControl<IRedeSocial['descricao']>;
  url: FormControl<IRedeSocial['url']>;
  logo: FormControl<IRedeSocial['logo']>;
};

export type RedeSocialFormGroup = FormGroup<RedeSocialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RedeSocialFormService {
  createRedeSocialFormGroup(redeSocial: RedeSocialFormGroupInput = { id: null }): RedeSocialFormGroup {
    const redeSocialRawValue = {
      ...this.getFormDefaults(),
      ...redeSocial,
    };
    return new FormGroup<RedeSocialFormGroupContent>({
      id: new FormControl(
        { value: redeSocialRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(redeSocialRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(redeSocialRawValue.descricao),
      url: new FormControl(redeSocialRawValue.url),
      logo: new FormControl(redeSocialRawValue.logo),
    });
  }

  getRedeSocial(form: RedeSocialFormGroup): IRedeSocial | NewRedeSocial {
    return form.getRawValue() as IRedeSocial | NewRedeSocial;
  }

  resetForm(form: RedeSocialFormGroup, redeSocial: RedeSocialFormGroupInput): void {
    const redeSocialRawValue = { ...this.getFormDefaults(), ...redeSocial };
    form.reset(
      {
        ...redeSocialRawValue,
        id: { value: redeSocialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RedeSocialFormDefaults {
    return {
      id: null,
    };
  }
}
