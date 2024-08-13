import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAreaContabilAssinaturaEmpresa, NewAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAreaContabilAssinaturaEmpresa for edit and NewAreaContabilAssinaturaEmpresaFormGroupInput for create.
 */
type AreaContabilAssinaturaEmpresaFormGroupInput =
  | IAreaContabilAssinaturaEmpresa
  | PartialWithRequiredKeyOf<NewAreaContabilAssinaturaEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAreaContabilAssinaturaEmpresa | NewAreaContabilAssinaturaEmpresa> = Omit<
  T,
  'dataAtribuicao' | 'dataRevogacao'
> & {
  dataAtribuicao?: string | null;
  dataRevogacao?: string | null;
};

type AreaContabilAssinaturaEmpresaFormRawValue = FormValueOf<IAreaContabilAssinaturaEmpresa>;

type NewAreaContabilAssinaturaEmpresaFormRawValue = FormValueOf<NewAreaContabilAssinaturaEmpresa>;

type AreaContabilAssinaturaEmpresaFormDefaults = Pick<
  NewAreaContabilAssinaturaEmpresa,
  'id' | 'ativo' | 'dataAtribuicao' | 'dataRevogacao'
>;

type AreaContabilAssinaturaEmpresaFormGroupContent = {
  id: FormControl<AreaContabilAssinaturaEmpresaFormRawValue['id'] | NewAreaContabilAssinaturaEmpresa['id']>;
  ativo: FormControl<AreaContabilAssinaturaEmpresaFormRawValue['ativo']>;
  dataAtribuicao: FormControl<AreaContabilAssinaturaEmpresaFormRawValue['dataAtribuicao']>;
  dataRevogacao: FormControl<AreaContabilAssinaturaEmpresaFormRawValue['dataRevogacao']>;
  areaContabil: FormControl<AreaContabilAssinaturaEmpresaFormRawValue['areaContabil']>;
  assinaturaEmpresa: FormControl<AreaContabilAssinaturaEmpresaFormRawValue['assinaturaEmpresa']>;
  contador: FormControl<AreaContabilAssinaturaEmpresaFormRawValue['contador']>;
};

export type AreaContabilAssinaturaEmpresaFormGroup = FormGroup<AreaContabilAssinaturaEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AreaContabilAssinaturaEmpresaFormService {
  createAreaContabilAssinaturaEmpresaFormGroup(
    areaContabilAssinaturaEmpresa: AreaContabilAssinaturaEmpresaFormGroupInput = { id: null },
  ): AreaContabilAssinaturaEmpresaFormGroup {
    const areaContabilAssinaturaEmpresaRawValue = this.convertAreaContabilAssinaturaEmpresaToAreaContabilAssinaturaEmpresaRawValue({
      ...this.getFormDefaults(),
      ...areaContabilAssinaturaEmpresa,
    });
    return new FormGroup<AreaContabilAssinaturaEmpresaFormGroupContent>({
      id: new FormControl(
        { value: areaContabilAssinaturaEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ativo: new FormControl(areaContabilAssinaturaEmpresaRawValue.ativo),
      dataAtribuicao: new FormControl(areaContabilAssinaturaEmpresaRawValue.dataAtribuicao),
      dataRevogacao: new FormControl(areaContabilAssinaturaEmpresaRawValue.dataRevogacao),
      areaContabil: new FormControl(areaContabilAssinaturaEmpresaRawValue.areaContabil, {
        validators: [Validators.required],
      }),
      assinaturaEmpresa: new FormControl(areaContabilAssinaturaEmpresaRawValue.assinaturaEmpresa, {
        validators: [Validators.required],
      }),
      contador: new FormControl(areaContabilAssinaturaEmpresaRawValue.contador, {
        validators: [Validators.required],
      }),
    });
  }

  getAreaContabilAssinaturaEmpresa(
    form: AreaContabilAssinaturaEmpresaFormGroup,
  ): IAreaContabilAssinaturaEmpresa | NewAreaContabilAssinaturaEmpresa {
    return this.convertAreaContabilAssinaturaEmpresaRawValueToAreaContabilAssinaturaEmpresa(
      form.getRawValue() as AreaContabilAssinaturaEmpresaFormRawValue | NewAreaContabilAssinaturaEmpresaFormRawValue,
    );
  }

  resetForm(
    form: AreaContabilAssinaturaEmpresaFormGroup,
    areaContabilAssinaturaEmpresa: AreaContabilAssinaturaEmpresaFormGroupInput,
  ): void {
    const areaContabilAssinaturaEmpresaRawValue = this.convertAreaContabilAssinaturaEmpresaToAreaContabilAssinaturaEmpresaRawValue({
      ...this.getFormDefaults(),
      ...areaContabilAssinaturaEmpresa,
    });
    form.reset(
      {
        ...areaContabilAssinaturaEmpresaRawValue,
        id: { value: areaContabilAssinaturaEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AreaContabilAssinaturaEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ativo: false,
      dataAtribuicao: currentTime,
      dataRevogacao: currentTime,
    };
  }

  private convertAreaContabilAssinaturaEmpresaRawValueToAreaContabilAssinaturaEmpresa(
    rawAreaContabilAssinaturaEmpresa: AreaContabilAssinaturaEmpresaFormRawValue | NewAreaContabilAssinaturaEmpresaFormRawValue,
  ): IAreaContabilAssinaturaEmpresa | NewAreaContabilAssinaturaEmpresa {
    return {
      ...rawAreaContabilAssinaturaEmpresa,
      dataAtribuicao: dayjs(rawAreaContabilAssinaturaEmpresa.dataAtribuicao, DATE_TIME_FORMAT),
      dataRevogacao: dayjs(rawAreaContabilAssinaturaEmpresa.dataRevogacao, DATE_TIME_FORMAT),
    };
  }

  private convertAreaContabilAssinaturaEmpresaToAreaContabilAssinaturaEmpresaRawValue(
    areaContabilAssinaturaEmpresa:
      | IAreaContabilAssinaturaEmpresa
      | (Partial<NewAreaContabilAssinaturaEmpresa> & AreaContabilAssinaturaEmpresaFormDefaults),
  ): AreaContabilAssinaturaEmpresaFormRawValue | PartialWithRequiredKeyOf<NewAreaContabilAssinaturaEmpresaFormRawValue> {
    return {
      ...areaContabilAssinaturaEmpresa,
      dataAtribuicao: areaContabilAssinaturaEmpresa.dataAtribuicao
        ? areaContabilAssinaturaEmpresa.dataAtribuicao.format(DATE_TIME_FORMAT)
        : undefined,
      dataRevogacao: areaContabilAssinaturaEmpresa.dataRevogacao
        ? areaContabilAssinaturaEmpresa.dataRevogacao.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
