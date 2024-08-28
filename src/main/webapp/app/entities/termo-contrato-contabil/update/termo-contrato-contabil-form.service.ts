import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITermoContratoContabil, NewTermoContratoContabil } from '../termo-contrato-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITermoContratoContabil for edit and NewTermoContratoContabilFormGroupInput for create.
 */
type TermoContratoContabilFormGroupInput = ITermoContratoContabil | PartialWithRequiredKeyOf<NewTermoContratoContabil>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITermoContratoContabil | NewTermoContratoContabil> = Omit<T, 'dataCriacao'> & {
  dataCriacao?: string | null;
};

type TermoContratoContabilFormRawValue = FormValueOf<ITermoContratoContabil>;

type NewTermoContratoContabilFormRawValue = FormValueOf<NewTermoContratoContabil>;

type TermoContratoContabilFormDefaults = Pick<NewTermoContratoContabil, 'id' | 'disponivel' | 'dataCriacao'>;

type TermoContratoContabilFormGroupContent = {
  id: FormControl<TermoContratoContabilFormRawValue['id'] | NewTermoContratoContabil['id']>;
  titulo: FormControl<TermoContratoContabilFormRawValue['titulo']>;
  descricao: FormControl<TermoContratoContabilFormRawValue['descricao']>;
  urlDocumentoFonte: FormControl<TermoContratoContabilFormRawValue['urlDocumentoFonte']>;
  documento: FormControl<TermoContratoContabilFormRawValue['documento']>;
  disponivel: FormControl<TermoContratoContabilFormRawValue['disponivel']>;
  dataCriacao: FormControl<TermoContratoContabilFormRawValue['dataCriacao']>;
};

export type TermoContratoContabilFormGroup = FormGroup<TermoContratoContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TermoContratoContabilFormService {
  createTermoContratoContabilFormGroup(
    termoContratoContabil: TermoContratoContabilFormGroupInput = { id: null },
  ): TermoContratoContabilFormGroup {
    const termoContratoContabilRawValue = this.convertTermoContratoContabilToTermoContratoContabilRawValue({
      ...this.getFormDefaults(),
      ...termoContratoContabil,
    });
    return new FormGroup<TermoContratoContabilFormGroupContent>({
      id: new FormControl(
        { value: termoContratoContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titulo: new FormControl(termoContratoContabilRawValue.titulo),
      descricao: new FormControl(termoContratoContabilRawValue.descricao),
      urlDocumentoFonte: new FormControl(termoContratoContabilRawValue.urlDocumentoFonte),
      documento: new FormControl(termoContratoContabilRawValue.documento),
      disponivel: new FormControl(termoContratoContabilRawValue.disponivel),
      dataCriacao: new FormControl(termoContratoContabilRawValue.dataCriacao),
    });
  }

  getTermoContratoContabil(form: TermoContratoContabilFormGroup): ITermoContratoContabil | NewTermoContratoContabil {
    return this.convertTermoContratoContabilRawValueToTermoContratoContabil(
      form.getRawValue() as TermoContratoContabilFormRawValue | NewTermoContratoContabilFormRawValue,
    );
  }

  resetForm(form: TermoContratoContabilFormGroup, termoContratoContabil: TermoContratoContabilFormGroupInput): void {
    const termoContratoContabilRawValue = this.convertTermoContratoContabilToTermoContratoContabilRawValue({
      ...this.getFormDefaults(),
      ...termoContratoContabil,
    });
    form.reset(
      {
        ...termoContratoContabilRawValue,
        id: { value: termoContratoContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TermoContratoContabilFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      disponivel: false,
      dataCriacao: currentTime,
    };
  }

  private convertTermoContratoContabilRawValueToTermoContratoContabil(
    rawTermoContratoContabil: TermoContratoContabilFormRawValue | NewTermoContratoContabilFormRawValue,
  ): ITermoContratoContabil | NewTermoContratoContabil {
    return {
      ...rawTermoContratoContabil,
      dataCriacao: dayjs(rawTermoContratoContabil.dataCriacao, DATE_TIME_FORMAT),
    };
  }

  private convertTermoContratoContabilToTermoContratoContabilRawValue(
    termoContratoContabil: ITermoContratoContabil | (Partial<NewTermoContratoContabil> & TermoContratoContabilFormDefaults),
  ): TermoContratoContabilFormRawValue | PartialWithRequiredKeyOf<NewTermoContratoContabilFormRawValue> {
    return {
      ...termoContratoContabil,
      dataCriacao: termoContratoContabil.dataCriacao ? termoContratoContabil.dataCriacao.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
