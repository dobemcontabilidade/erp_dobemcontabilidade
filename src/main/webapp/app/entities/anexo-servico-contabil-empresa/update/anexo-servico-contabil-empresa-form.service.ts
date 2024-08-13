import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAnexoServicoContabilEmpresa, NewAnexoServicoContabilEmpresa } from '../anexo-servico-contabil-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoServicoContabilEmpresa for edit and NewAnexoServicoContabilEmpresaFormGroupInput for create.
 */
type AnexoServicoContabilEmpresaFormGroupInput = IAnexoServicoContabilEmpresa | PartialWithRequiredKeyOf<NewAnexoServicoContabilEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAnexoServicoContabilEmpresa | NewAnexoServicoContabilEmpresa> = Omit<T, 'dataHoraUpload'> & {
  dataHoraUpload?: string | null;
};

type AnexoServicoContabilEmpresaFormRawValue = FormValueOf<IAnexoServicoContabilEmpresa>;

type NewAnexoServicoContabilEmpresaFormRawValue = FormValueOf<NewAnexoServicoContabilEmpresa>;

type AnexoServicoContabilEmpresaFormDefaults = Pick<NewAnexoServicoContabilEmpresa, 'id' | 'dataHoraUpload'>;

type AnexoServicoContabilEmpresaFormGroupContent = {
  id: FormControl<AnexoServicoContabilEmpresaFormRawValue['id'] | NewAnexoServicoContabilEmpresa['id']>;
  link: FormControl<AnexoServicoContabilEmpresaFormRawValue['link']>;
  dataHoraUpload: FormControl<AnexoServicoContabilEmpresaFormRawValue['dataHoraUpload']>;
  anexoRequerido: FormControl<AnexoServicoContabilEmpresaFormRawValue['anexoRequerido']>;
  servicoContabilAssinaturaEmpresa: FormControl<AnexoServicoContabilEmpresaFormRawValue['servicoContabilAssinaturaEmpresa']>;
};

export type AnexoServicoContabilEmpresaFormGroup = FormGroup<AnexoServicoContabilEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoServicoContabilEmpresaFormService {
  createAnexoServicoContabilEmpresaFormGroup(
    anexoServicoContabilEmpresa: AnexoServicoContabilEmpresaFormGroupInput = { id: null },
  ): AnexoServicoContabilEmpresaFormGroup {
    const anexoServicoContabilEmpresaRawValue = this.convertAnexoServicoContabilEmpresaToAnexoServicoContabilEmpresaRawValue({
      ...this.getFormDefaults(),
      ...anexoServicoContabilEmpresa,
    });
    return new FormGroup<AnexoServicoContabilEmpresaFormGroupContent>({
      id: new FormControl(
        { value: anexoServicoContabilEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      link: new FormControl(anexoServicoContabilEmpresaRawValue.link),
      dataHoraUpload: new FormControl(anexoServicoContabilEmpresaRawValue.dataHoraUpload),
      anexoRequerido: new FormControl(anexoServicoContabilEmpresaRawValue.anexoRequerido, {
        validators: [Validators.required],
      }),
      servicoContabilAssinaturaEmpresa: new FormControl(anexoServicoContabilEmpresaRawValue.servicoContabilAssinaturaEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoServicoContabilEmpresa(
    form: AnexoServicoContabilEmpresaFormGroup,
  ): IAnexoServicoContabilEmpresa | NewAnexoServicoContabilEmpresa {
    return this.convertAnexoServicoContabilEmpresaRawValueToAnexoServicoContabilEmpresa(
      form.getRawValue() as AnexoServicoContabilEmpresaFormRawValue | NewAnexoServicoContabilEmpresaFormRawValue,
    );
  }

  resetForm(form: AnexoServicoContabilEmpresaFormGroup, anexoServicoContabilEmpresa: AnexoServicoContabilEmpresaFormGroupInput): void {
    const anexoServicoContabilEmpresaRawValue = this.convertAnexoServicoContabilEmpresaToAnexoServicoContabilEmpresaRawValue({
      ...this.getFormDefaults(),
      ...anexoServicoContabilEmpresa,
    });
    form.reset(
      {
        ...anexoServicoContabilEmpresaRawValue,
        id: { value: anexoServicoContabilEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoServicoContabilEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataHoraUpload: currentTime,
    };
  }

  private convertAnexoServicoContabilEmpresaRawValueToAnexoServicoContabilEmpresa(
    rawAnexoServicoContabilEmpresa: AnexoServicoContabilEmpresaFormRawValue | NewAnexoServicoContabilEmpresaFormRawValue,
  ): IAnexoServicoContabilEmpresa | NewAnexoServicoContabilEmpresa {
    return {
      ...rawAnexoServicoContabilEmpresa,
      dataHoraUpload: dayjs(rawAnexoServicoContabilEmpresa.dataHoraUpload, DATE_TIME_FORMAT),
    };
  }

  private convertAnexoServicoContabilEmpresaToAnexoServicoContabilEmpresaRawValue(
    anexoServicoContabilEmpresa:
      | IAnexoServicoContabilEmpresa
      | (Partial<NewAnexoServicoContabilEmpresa> & AnexoServicoContabilEmpresaFormDefaults),
  ): AnexoServicoContabilEmpresaFormRawValue | PartialWithRequiredKeyOf<NewAnexoServicoContabilEmpresaFormRawValue> {
    return {
      ...anexoServicoContabilEmpresa,
      dataHoraUpload: anexoServicoContabilEmpresa.dataHoraUpload
        ? anexoServicoContabilEmpresa.dataHoraUpload.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
