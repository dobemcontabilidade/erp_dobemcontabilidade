import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFuncionalidadeGrupoAcessoPadrao, NewFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuncionalidadeGrupoAcessoPadrao for edit and NewFuncionalidadeGrupoAcessoPadraoFormGroupInput for create.
 */
type FuncionalidadeGrupoAcessoPadraoFormGroupInput =
  | IFuncionalidadeGrupoAcessoPadrao
  | PartialWithRequiredKeyOf<NewFuncionalidadeGrupoAcessoPadrao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFuncionalidadeGrupoAcessoPadrao | NewFuncionalidadeGrupoAcessoPadrao> = Omit<
  T,
  'dataExpiracao' | 'dataAtribuicao'
> & {
  dataExpiracao?: string | null;
  dataAtribuicao?: string | null;
};

type FuncionalidadeGrupoAcessoPadraoFormRawValue = FormValueOf<IFuncionalidadeGrupoAcessoPadrao>;

type NewFuncionalidadeGrupoAcessoPadraoFormRawValue = FormValueOf<NewFuncionalidadeGrupoAcessoPadrao>;

type FuncionalidadeGrupoAcessoPadraoFormDefaults = Pick<
  NewFuncionalidadeGrupoAcessoPadrao,
  'id' | 'autorizado' | 'dataExpiracao' | 'dataAtribuicao'
>;

type FuncionalidadeGrupoAcessoPadraoFormGroupContent = {
  id: FormControl<FuncionalidadeGrupoAcessoPadraoFormRawValue['id'] | NewFuncionalidadeGrupoAcessoPadrao['id']>;
  autorizado: FormControl<FuncionalidadeGrupoAcessoPadraoFormRawValue['autorizado']>;
  dataExpiracao: FormControl<FuncionalidadeGrupoAcessoPadraoFormRawValue['dataExpiracao']>;
  dataAtribuicao: FormControl<FuncionalidadeGrupoAcessoPadraoFormRawValue['dataAtribuicao']>;
  funcionalidade: FormControl<FuncionalidadeGrupoAcessoPadraoFormRawValue['funcionalidade']>;
  grupoAcessoPadrao: FormControl<FuncionalidadeGrupoAcessoPadraoFormRawValue['grupoAcessoPadrao']>;
  permisao: FormControl<FuncionalidadeGrupoAcessoPadraoFormRawValue['permisao']>;
};

export type FuncionalidadeGrupoAcessoPadraoFormGroup = FormGroup<FuncionalidadeGrupoAcessoPadraoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuncionalidadeGrupoAcessoPadraoFormService {
  createFuncionalidadeGrupoAcessoPadraoFormGroup(
    funcionalidadeGrupoAcessoPadrao: FuncionalidadeGrupoAcessoPadraoFormGroupInput = { id: null },
  ): FuncionalidadeGrupoAcessoPadraoFormGroup {
    const funcionalidadeGrupoAcessoPadraoRawValue = this.convertFuncionalidadeGrupoAcessoPadraoToFuncionalidadeGrupoAcessoPadraoRawValue({
      ...this.getFormDefaults(),
      ...funcionalidadeGrupoAcessoPadrao,
    });
    return new FormGroup<FuncionalidadeGrupoAcessoPadraoFormGroupContent>({
      id: new FormControl(
        { value: funcionalidadeGrupoAcessoPadraoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      autorizado: new FormControl(funcionalidadeGrupoAcessoPadraoRawValue.autorizado),
      dataExpiracao: new FormControl(funcionalidadeGrupoAcessoPadraoRawValue.dataExpiracao),
      dataAtribuicao: new FormControl(funcionalidadeGrupoAcessoPadraoRawValue.dataAtribuicao),
      funcionalidade: new FormControl(funcionalidadeGrupoAcessoPadraoRawValue.funcionalidade, {
        validators: [Validators.required],
      }),
      grupoAcessoPadrao: new FormControl(funcionalidadeGrupoAcessoPadraoRawValue.grupoAcessoPadrao, {
        validators: [Validators.required],
      }),
      permisao: new FormControl(funcionalidadeGrupoAcessoPadraoRawValue.permisao, {
        validators: [Validators.required],
      }),
    });
  }

  getFuncionalidadeGrupoAcessoPadrao(
    form: FuncionalidadeGrupoAcessoPadraoFormGroup,
  ): IFuncionalidadeGrupoAcessoPadrao | NewFuncionalidadeGrupoAcessoPadrao {
    return this.convertFuncionalidadeGrupoAcessoPadraoRawValueToFuncionalidadeGrupoAcessoPadrao(
      form.getRawValue() as FuncionalidadeGrupoAcessoPadraoFormRawValue | NewFuncionalidadeGrupoAcessoPadraoFormRawValue,
    );
  }

  resetForm(
    form: FuncionalidadeGrupoAcessoPadraoFormGroup,
    funcionalidadeGrupoAcessoPadrao: FuncionalidadeGrupoAcessoPadraoFormGroupInput,
  ): void {
    const funcionalidadeGrupoAcessoPadraoRawValue = this.convertFuncionalidadeGrupoAcessoPadraoToFuncionalidadeGrupoAcessoPadraoRawValue({
      ...this.getFormDefaults(),
      ...funcionalidadeGrupoAcessoPadrao,
    });
    form.reset(
      {
        ...funcionalidadeGrupoAcessoPadraoRawValue,
        id: { value: funcionalidadeGrupoAcessoPadraoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuncionalidadeGrupoAcessoPadraoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      autorizado: false,
      dataExpiracao: currentTime,
      dataAtribuicao: currentTime,
    };
  }

  private convertFuncionalidadeGrupoAcessoPadraoRawValueToFuncionalidadeGrupoAcessoPadrao(
    rawFuncionalidadeGrupoAcessoPadrao: FuncionalidadeGrupoAcessoPadraoFormRawValue | NewFuncionalidadeGrupoAcessoPadraoFormRawValue,
  ): IFuncionalidadeGrupoAcessoPadrao | NewFuncionalidadeGrupoAcessoPadrao {
    return {
      ...rawFuncionalidadeGrupoAcessoPadrao,
      dataExpiracao: dayjs(rawFuncionalidadeGrupoAcessoPadrao.dataExpiracao, DATE_TIME_FORMAT),
      dataAtribuicao: dayjs(rawFuncionalidadeGrupoAcessoPadrao.dataAtribuicao, DATE_TIME_FORMAT),
    };
  }

  private convertFuncionalidadeGrupoAcessoPadraoToFuncionalidadeGrupoAcessoPadraoRawValue(
    funcionalidadeGrupoAcessoPadrao:
      | IFuncionalidadeGrupoAcessoPadrao
      | (Partial<NewFuncionalidadeGrupoAcessoPadrao> & FuncionalidadeGrupoAcessoPadraoFormDefaults),
  ): FuncionalidadeGrupoAcessoPadraoFormRawValue | PartialWithRequiredKeyOf<NewFuncionalidadeGrupoAcessoPadraoFormRawValue> {
    return {
      ...funcionalidadeGrupoAcessoPadrao,
      dataExpiracao: funcionalidadeGrupoAcessoPadrao.dataExpiracao
        ? funcionalidadeGrupoAcessoPadrao.dataExpiracao.format(DATE_TIME_FORMAT)
        : undefined,
      dataAtribuicao: funcionalidadeGrupoAcessoPadrao.dataAtribuicao
        ? funcionalidadeGrupoAcessoPadrao.dataAtribuicao.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
