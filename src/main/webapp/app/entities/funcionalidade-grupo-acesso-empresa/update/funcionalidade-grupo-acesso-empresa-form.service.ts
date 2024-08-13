import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFuncionalidadeGrupoAcessoEmpresa, NewFuncionalidadeGrupoAcessoEmpresa } from '../funcionalidade-grupo-acesso-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuncionalidadeGrupoAcessoEmpresa for edit and NewFuncionalidadeGrupoAcessoEmpresaFormGroupInput for create.
 */
type FuncionalidadeGrupoAcessoEmpresaFormGroupInput =
  | IFuncionalidadeGrupoAcessoEmpresa
  | PartialWithRequiredKeyOf<NewFuncionalidadeGrupoAcessoEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFuncionalidadeGrupoAcessoEmpresa | NewFuncionalidadeGrupoAcessoEmpresa> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

type FuncionalidadeGrupoAcessoEmpresaFormRawValue = FormValueOf<IFuncionalidadeGrupoAcessoEmpresa>;

type NewFuncionalidadeGrupoAcessoEmpresaFormRawValue = FormValueOf<NewFuncionalidadeGrupoAcessoEmpresa>;

type FuncionalidadeGrupoAcessoEmpresaFormDefaults = Pick<
  NewFuncionalidadeGrupoAcessoEmpresa,
  'id' | 'dataExpiracao' | 'ilimitado' | 'desabilitar'
>;

type FuncionalidadeGrupoAcessoEmpresaFormGroupContent = {
  id: FormControl<FuncionalidadeGrupoAcessoEmpresaFormRawValue['id'] | NewFuncionalidadeGrupoAcessoEmpresa['id']>;
  ativa: FormControl<FuncionalidadeGrupoAcessoEmpresaFormRawValue['ativa']>;
  dataExpiracao: FormControl<FuncionalidadeGrupoAcessoEmpresaFormRawValue['dataExpiracao']>;
  ilimitado: FormControl<FuncionalidadeGrupoAcessoEmpresaFormRawValue['ilimitado']>;
  desabilitar: FormControl<FuncionalidadeGrupoAcessoEmpresaFormRawValue['desabilitar']>;
  funcionalidade: FormControl<FuncionalidadeGrupoAcessoEmpresaFormRawValue['funcionalidade']>;
  grupoAcessoEmpresa: FormControl<FuncionalidadeGrupoAcessoEmpresaFormRawValue['grupoAcessoEmpresa']>;
  permisao: FormControl<FuncionalidadeGrupoAcessoEmpresaFormRawValue['permisao']>;
};

export type FuncionalidadeGrupoAcessoEmpresaFormGroup = FormGroup<FuncionalidadeGrupoAcessoEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuncionalidadeGrupoAcessoEmpresaFormService {
  createFuncionalidadeGrupoAcessoEmpresaFormGroup(
    funcionalidadeGrupoAcessoEmpresa: FuncionalidadeGrupoAcessoEmpresaFormGroupInput = { id: null },
  ): FuncionalidadeGrupoAcessoEmpresaFormGroup {
    const funcionalidadeGrupoAcessoEmpresaRawValue = this.convertFuncionalidadeGrupoAcessoEmpresaToFuncionalidadeGrupoAcessoEmpresaRawValue(
      {
        ...this.getFormDefaults(),
        ...funcionalidadeGrupoAcessoEmpresa,
      },
    );
    return new FormGroup<FuncionalidadeGrupoAcessoEmpresaFormGroupContent>({
      id: new FormControl(
        { value: funcionalidadeGrupoAcessoEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ativa: new FormControl(funcionalidadeGrupoAcessoEmpresaRawValue.ativa),
      dataExpiracao: new FormControl(funcionalidadeGrupoAcessoEmpresaRawValue.dataExpiracao),
      ilimitado: new FormControl(funcionalidadeGrupoAcessoEmpresaRawValue.ilimitado),
      desabilitar: new FormControl(funcionalidadeGrupoAcessoEmpresaRawValue.desabilitar),
      funcionalidade: new FormControl(funcionalidadeGrupoAcessoEmpresaRawValue.funcionalidade, {
        validators: [Validators.required],
      }),
      grupoAcessoEmpresa: new FormControl(funcionalidadeGrupoAcessoEmpresaRawValue.grupoAcessoEmpresa, {
        validators: [Validators.required],
      }),
      permisao: new FormControl(funcionalidadeGrupoAcessoEmpresaRawValue.permisao, {
        validators: [Validators.required],
      }),
    });
  }

  getFuncionalidadeGrupoAcessoEmpresa(
    form: FuncionalidadeGrupoAcessoEmpresaFormGroup,
  ): IFuncionalidadeGrupoAcessoEmpresa | NewFuncionalidadeGrupoAcessoEmpresa {
    return this.convertFuncionalidadeGrupoAcessoEmpresaRawValueToFuncionalidadeGrupoAcessoEmpresa(
      form.getRawValue() as FuncionalidadeGrupoAcessoEmpresaFormRawValue | NewFuncionalidadeGrupoAcessoEmpresaFormRawValue,
    );
  }

  resetForm(
    form: FuncionalidadeGrupoAcessoEmpresaFormGroup,
    funcionalidadeGrupoAcessoEmpresa: FuncionalidadeGrupoAcessoEmpresaFormGroupInput,
  ): void {
    const funcionalidadeGrupoAcessoEmpresaRawValue = this.convertFuncionalidadeGrupoAcessoEmpresaToFuncionalidadeGrupoAcessoEmpresaRawValue(
      { ...this.getFormDefaults(), ...funcionalidadeGrupoAcessoEmpresa },
    );
    form.reset(
      {
        ...funcionalidadeGrupoAcessoEmpresaRawValue,
        id: { value: funcionalidadeGrupoAcessoEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuncionalidadeGrupoAcessoEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataExpiracao: currentTime,
      ilimitado: false,
      desabilitar: false,
    };
  }

  private convertFuncionalidadeGrupoAcessoEmpresaRawValueToFuncionalidadeGrupoAcessoEmpresa(
    rawFuncionalidadeGrupoAcessoEmpresa: FuncionalidadeGrupoAcessoEmpresaFormRawValue | NewFuncionalidadeGrupoAcessoEmpresaFormRawValue,
  ): IFuncionalidadeGrupoAcessoEmpresa | NewFuncionalidadeGrupoAcessoEmpresa {
    return {
      ...rawFuncionalidadeGrupoAcessoEmpresa,
      dataExpiracao: dayjs(rawFuncionalidadeGrupoAcessoEmpresa.dataExpiracao, DATE_TIME_FORMAT),
    };
  }

  private convertFuncionalidadeGrupoAcessoEmpresaToFuncionalidadeGrupoAcessoEmpresaRawValue(
    funcionalidadeGrupoAcessoEmpresa:
      | IFuncionalidadeGrupoAcessoEmpresa
      | (Partial<NewFuncionalidadeGrupoAcessoEmpresa> & FuncionalidadeGrupoAcessoEmpresaFormDefaults),
  ): FuncionalidadeGrupoAcessoEmpresaFormRawValue | PartialWithRequiredKeyOf<NewFuncionalidadeGrupoAcessoEmpresaFormRawValue> {
    return {
      ...funcionalidadeGrupoAcessoEmpresa,
      dataExpiracao: funcionalidadeGrupoAcessoEmpresa.dataExpiracao
        ? funcionalidadeGrupoAcessoEmpresa.dataExpiracao.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
