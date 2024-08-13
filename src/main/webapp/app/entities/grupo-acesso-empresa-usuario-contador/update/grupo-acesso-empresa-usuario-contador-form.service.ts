import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGrupoAcessoEmpresaUsuarioContador, NewGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGrupoAcessoEmpresaUsuarioContador for edit and NewGrupoAcessoEmpresaUsuarioContadorFormGroupInput for create.
 */
type GrupoAcessoEmpresaUsuarioContadorFormGroupInput =
  | IGrupoAcessoEmpresaUsuarioContador
  | PartialWithRequiredKeyOf<NewGrupoAcessoEmpresaUsuarioContador>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGrupoAcessoEmpresaUsuarioContador | NewGrupoAcessoEmpresaUsuarioContador> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

type GrupoAcessoEmpresaUsuarioContadorFormRawValue = FormValueOf<IGrupoAcessoEmpresaUsuarioContador>;

type NewGrupoAcessoEmpresaUsuarioContadorFormRawValue = FormValueOf<NewGrupoAcessoEmpresaUsuarioContador>;

type GrupoAcessoEmpresaUsuarioContadorFormDefaults = Pick<
  NewGrupoAcessoEmpresaUsuarioContador,
  'id' | 'dataExpiracao' | 'ilimitado' | 'desabilitar'
>;

type GrupoAcessoEmpresaUsuarioContadorFormGroupContent = {
  id: FormControl<GrupoAcessoEmpresaUsuarioContadorFormRawValue['id'] | NewGrupoAcessoEmpresaUsuarioContador['id']>;
  nome: FormControl<GrupoAcessoEmpresaUsuarioContadorFormRawValue['nome']>;
  dataExpiracao: FormControl<GrupoAcessoEmpresaUsuarioContadorFormRawValue['dataExpiracao']>;
  ilimitado: FormControl<GrupoAcessoEmpresaUsuarioContadorFormRawValue['ilimitado']>;
  desabilitar: FormControl<GrupoAcessoEmpresaUsuarioContadorFormRawValue['desabilitar']>;
  usuarioContador: FormControl<GrupoAcessoEmpresaUsuarioContadorFormRawValue['usuarioContador']>;
  permisao: FormControl<GrupoAcessoEmpresaUsuarioContadorFormRawValue['permisao']>;
  grupoAcessoEmpresa: FormControl<GrupoAcessoEmpresaUsuarioContadorFormRawValue['grupoAcessoEmpresa']>;
};

export type GrupoAcessoEmpresaUsuarioContadorFormGroup = FormGroup<GrupoAcessoEmpresaUsuarioContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoEmpresaUsuarioContadorFormService {
  createGrupoAcessoEmpresaUsuarioContadorFormGroup(
    grupoAcessoEmpresaUsuarioContador: GrupoAcessoEmpresaUsuarioContadorFormGroupInput = { id: null },
  ): GrupoAcessoEmpresaUsuarioContadorFormGroup {
    const grupoAcessoEmpresaUsuarioContadorRawValue =
      this.convertGrupoAcessoEmpresaUsuarioContadorToGrupoAcessoEmpresaUsuarioContadorRawValue({
        ...this.getFormDefaults(),
        ...grupoAcessoEmpresaUsuarioContador,
      });
    return new FormGroup<GrupoAcessoEmpresaUsuarioContadorFormGroupContent>({
      id: new FormControl(
        { value: grupoAcessoEmpresaUsuarioContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(grupoAcessoEmpresaUsuarioContadorRawValue.nome),
      dataExpiracao: new FormControl(grupoAcessoEmpresaUsuarioContadorRawValue.dataExpiracao),
      ilimitado: new FormControl(grupoAcessoEmpresaUsuarioContadorRawValue.ilimitado),
      desabilitar: new FormControl(grupoAcessoEmpresaUsuarioContadorRawValue.desabilitar),
      usuarioContador: new FormControl(grupoAcessoEmpresaUsuarioContadorRawValue.usuarioContador, {
        validators: [Validators.required],
      }),
      permisao: new FormControl(grupoAcessoEmpresaUsuarioContadorRawValue.permisao, {
        validators: [Validators.required],
      }),
      grupoAcessoEmpresa: new FormControl(grupoAcessoEmpresaUsuarioContadorRawValue.grupoAcessoEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getGrupoAcessoEmpresaUsuarioContador(
    form: GrupoAcessoEmpresaUsuarioContadorFormGroup,
  ): IGrupoAcessoEmpresaUsuarioContador | NewGrupoAcessoEmpresaUsuarioContador {
    return this.convertGrupoAcessoEmpresaUsuarioContadorRawValueToGrupoAcessoEmpresaUsuarioContador(
      form.getRawValue() as GrupoAcessoEmpresaUsuarioContadorFormRawValue | NewGrupoAcessoEmpresaUsuarioContadorFormRawValue,
    );
  }

  resetForm(
    form: GrupoAcessoEmpresaUsuarioContadorFormGroup,
    grupoAcessoEmpresaUsuarioContador: GrupoAcessoEmpresaUsuarioContadorFormGroupInput,
  ): void {
    const grupoAcessoEmpresaUsuarioContadorRawValue =
      this.convertGrupoAcessoEmpresaUsuarioContadorToGrupoAcessoEmpresaUsuarioContadorRawValue({
        ...this.getFormDefaults(),
        ...grupoAcessoEmpresaUsuarioContador,
      });
    form.reset(
      {
        ...grupoAcessoEmpresaUsuarioContadorRawValue,
        id: { value: grupoAcessoEmpresaUsuarioContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GrupoAcessoEmpresaUsuarioContadorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataExpiracao: currentTime,
      ilimitado: false,
      desabilitar: false,
    };
  }

  private convertGrupoAcessoEmpresaUsuarioContadorRawValueToGrupoAcessoEmpresaUsuarioContador(
    rawGrupoAcessoEmpresaUsuarioContador: GrupoAcessoEmpresaUsuarioContadorFormRawValue | NewGrupoAcessoEmpresaUsuarioContadorFormRawValue,
  ): IGrupoAcessoEmpresaUsuarioContador | NewGrupoAcessoEmpresaUsuarioContador {
    return {
      ...rawGrupoAcessoEmpresaUsuarioContador,
      dataExpiracao: dayjs(rawGrupoAcessoEmpresaUsuarioContador.dataExpiracao, DATE_TIME_FORMAT),
    };
  }

  private convertGrupoAcessoEmpresaUsuarioContadorToGrupoAcessoEmpresaUsuarioContadorRawValue(
    grupoAcessoEmpresaUsuarioContador:
      | IGrupoAcessoEmpresaUsuarioContador
      | (Partial<NewGrupoAcessoEmpresaUsuarioContador> & GrupoAcessoEmpresaUsuarioContadorFormDefaults),
  ): GrupoAcessoEmpresaUsuarioContadorFormRawValue | PartialWithRequiredKeyOf<NewGrupoAcessoEmpresaUsuarioContadorFormRawValue> {
    return {
      ...grupoAcessoEmpresaUsuarioContador,
      dataExpiracao: grupoAcessoEmpresaUsuarioContador.dataExpiracao
        ? grupoAcessoEmpresaUsuarioContador.dataExpiracao.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
