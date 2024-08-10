import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISocio, NewSocio } from '../socio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocio for edit and NewSocioFormGroupInput for create.
 */
type SocioFormGroupInput = ISocio | PartialWithRequiredKeyOf<NewSocio>;

type SocioFormDefaults = Pick<NewSocio, 'id' | 'prolabore' | 'adminstrador' | 'distribuicaoLucro' | 'responsavelReceita'>;

type SocioFormGroupContent = {
  id: FormControl<ISocio['id'] | NewSocio['id']>;
  nome: FormControl<ISocio['nome']>;
  prolabore: FormControl<ISocio['prolabore']>;
  percentualSociedade: FormControl<ISocio['percentualSociedade']>;
  adminstrador: FormControl<ISocio['adminstrador']>;
  distribuicaoLucro: FormControl<ISocio['distribuicaoLucro']>;
  responsavelReceita: FormControl<ISocio['responsavelReceita']>;
  percentualDistribuicaoLucro: FormControl<ISocio['percentualDistribuicaoLucro']>;
  funcaoSocio: FormControl<ISocio['funcaoSocio']>;
  pessoa: FormControl<ISocio['pessoa']>;
  empresa: FormControl<ISocio['empresa']>;
};

export type SocioFormGroup = FormGroup<SocioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocioFormService {
  createSocioFormGroup(socio: SocioFormGroupInput = { id: null }): SocioFormGroup {
    const socioRawValue = {
      ...this.getFormDefaults(),
      ...socio,
    };
    return new FormGroup<SocioFormGroupContent>({
      id: new FormControl(
        { value: socioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(socioRawValue.nome, {
        validators: [Validators.maxLength(200)],
      }),
      prolabore: new FormControl(socioRawValue.prolabore),
      percentualSociedade: new FormControl(socioRawValue.percentualSociedade),
      adminstrador: new FormControl(socioRawValue.adminstrador, {
        validators: [Validators.required],
      }),
      distribuicaoLucro: new FormControl(socioRawValue.distribuicaoLucro),
      responsavelReceita: new FormControl(socioRawValue.responsavelReceita),
      percentualDistribuicaoLucro: new FormControl(socioRawValue.percentualDistribuicaoLucro),
      funcaoSocio: new FormControl(socioRawValue.funcaoSocio, {
        validators: [Validators.required],
      }),
      pessoa: new FormControl(socioRawValue.pessoa, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(socioRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getSocio(form: SocioFormGroup): ISocio | NewSocio {
    return form.getRawValue() as ISocio | NewSocio;
  }

  resetForm(form: SocioFormGroup, socio: SocioFormGroupInput): void {
    const socioRawValue = { ...this.getFormDefaults(), ...socio };
    form.reset(
      {
        ...socioRawValue,
        id: { value: socioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SocioFormDefaults {
    return {
      id: null,
      prolabore: false,
      adminstrador: false,
      distribuicaoLucro: false,
      responsavelReceita: false,
    };
  }
}
