import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGrupoAcessoUsuarioEmpresa, NewGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGrupoAcessoUsuarioEmpresa for edit and NewGrupoAcessoUsuarioEmpresaFormGroupInput for create.
 */
type GrupoAcessoUsuarioEmpresaFormGroupInput = IGrupoAcessoUsuarioEmpresa | PartialWithRequiredKeyOf<NewGrupoAcessoUsuarioEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGrupoAcessoUsuarioEmpresa | NewGrupoAcessoUsuarioEmpresa> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

type GrupoAcessoUsuarioEmpresaFormRawValue = FormValueOf<IGrupoAcessoUsuarioEmpresa>;

type NewGrupoAcessoUsuarioEmpresaFormRawValue = FormValueOf<NewGrupoAcessoUsuarioEmpresa>;

type GrupoAcessoUsuarioEmpresaFormDefaults = Pick<NewGrupoAcessoUsuarioEmpresa, 'id' | 'dataExpiracao' | 'ilimitado' | 'desabilitar'>;

type GrupoAcessoUsuarioEmpresaFormGroupContent = {
  id: FormControl<GrupoAcessoUsuarioEmpresaFormRawValue['id'] | NewGrupoAcessoUsuarioEmpresa['id']>;
  nome: FormControl<GrupoAcessoUsuarioEmpresaFormRawValue['nome']>;
  dataExpiracao: FormControl<GrupoAcessoUsuarioEmpresaFormRawValue['dataExpiracao']>;
  ilimitado: FormControl<GrupoAcessoUsuarioEmpresaFormRawValue['ilimitado']>;
  desabilitar: FormControl<GrupoAcessoUsuarioEmpresaFormRawValue['desabilitar']>;
  grupoAcessoEmpresa: FormControl<GrupoAcessoUsuarioEmpresaFormRawValue['grupoAcessoEmpresa']>;
  usuarioEmpresa: FormControl<GrupoAcessoUsuarioEmpresaFormRawValue['usuarioEmpresa']>;
};

export type GrupoAcessoUsuarioEmpresaFormGroup = FormGroup<GrupoAcessoUsuarioEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoUsuarioEmpresaFormService {
  createGrupoAcessoUsuarioEmpresaFormGroup(
    grupoAcessoUsuarioEmpresa: GrupoAcessoUsuarioEmpresaFormGroupInput = { id: null },
  ): GrupoAcessoUsuarioEmpresaFormGroup {
    const grupoAcessoUsuarioEmpresaRawValue = this.convertGrupoAcessoUsuarioEmpresaToGrupoAcessoUsuarioEmpresaRawValue({
      ...this.getFormDefaults(),
      ...grupoAcessoUsuarioEmpresa,
    });
    return new FormGroup<GrupoAcessoUsuarioEmpresaFormGroupContent>({
      id: new FormControl(
        { value: grupoAcessoUsuarioEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(grupoAcessoUsuarioEmpresaRawValue.nome),
      dataExpiracao: new FormControl(grupoAcessoUsuarioEmpresaRawValue.dataExpiracao),
      ilimitado: new FormControl(grupoAcessoUsuarioEmpresaRawValue.ilimitado),
      desabilitar: new FormControl(grupoAcessoUsuarioEmpresaRawValue.desabilitar),
      grupoAcessoEmpresa: new FormControl(grupoAcessoUsuarioEmpresaRawValue.grupoAcessoEmpresa, {
        validators: [Validators.required],
      }),
      usuarioEmpresa: new FormControl(grupoAcessoUsuarioEmpresaRawValue.usuarioEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getGrupoAcessoUsuarioEmpresa(form: GrupoAcessoUsuarioEmpresaFormGroup): IGrupoAcessoUsuarioEmpresa | NewGrupoAcessoUsuarioEmpresa {
    return this.convertGrupoAcessoUsuarioEmpresaRawValueToGrupoAcessoUsuarioEmpresa(
      form.getRawValue() as GrupoAcessoUsuarioEmpresaFormRawValue | NewGrupoAcessoUsuarioEmpresaFormRawValue,
    );
  }

  resetForm(form: GrupoAcessoUsuarioEmpresaFormGroup, grupoAcessoUsuarioEmpresa: GrupoAcessoUsuarioEmpresaFormGroupInput): void {
    const grupoAcessoUsuarioEmpresaRawValue = this.convertGrupoAcessoUsuarioEmpresaToGrupoAcessoUsuarioEmpresaRawValue({
      ...this.getFormDefaults(),
      ...grupoAcessoUsuarioEmpresa,
    });
    form.reset(
      {
        ...grupoAcessoUsuarioEmpresaRawValue,
        id: { value: grupoAcessoUsuarioEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GrupoAcessoUsuarioEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataExpiracao: currentTime,
      ilimitado: false,
      desabilitar: false,
    };
  }

  private convertGrupoAcessoUsuarioEmpresaRawValueToGrupoAcessoUsuarioEmpresa(
    rawGrupoAcessoUsuarioEmpresa: GrupoAcessoUsuarioEmpresaFormRawValue | NewGrupoAcessoUsuarioEmpresaFormRawValue,
  ): IGrupoAcessoUsuarioEmpresa | NewGrupoAcessoUsuarioEmpresa {
    return {
      ...rawGrupoAcessoUsuarioEmpresa,
      dataExpiracao: dayjs(rawGrupoAcessoUsuarioEmpresa.dataExpiracao, DATE_TIME_FORMAT),
    };
  }

  private convertGrupoAcessoUsuarioEmpresaToGrupoAcessoUsuarioEmpresaRawValue(
    grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa | (Partial<NewGrupoAcessoUsuarioEmpresa> & GrupoAcessoUsuarioEmpresaFormDefaults),
  ): GrupoAcessoUsuarioEmpresaFormRawValue | PartialWithRequiredKeyOf<NewGrupoAcessoUsuarioEmpresaFormRawValue> {
    return {
      ...grupoAcessoUsuarioEmpresa,
      dataExpiracao: grupoAcessoUsuarioEmpresa.dataExpiracao ? grupoAcessoUsuarioEmpresa.dataExpiracao.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
