import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITermoAdesaoContador, NewTermoAdesaoContador } from '../termo-adesao-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITermoAdesaoContador for edit and NewTermoAdesaoContadorFormGroupInput for create.
 */
type TermoAdesaoContadorFormGroupInput = ITermoAdesaoContador | PartialWithRequiredKeyOf<NewTermoAdesaoContador>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITermoAdesaoContador | NewTermoAdesaoContador> = Omit<T, 'dataAdesao'> & {
  dataAdesao?: string | null;
};

type TermoAdesaoContadorFormRawValue = FormValueOf<ITermoAdesaoContador>;

type NewTermoAdesaoContadorFormRawValue = FormValueOf<NewTermoAdesaoContador>;

type TermoAdesaoContadorFormDefaults = Pick<NewTermoAdesaoContador, 'id' | 'dataAdesao' | 'checked'>;

type TermoAdesaoContadorFormGroupContent = {
  id: FormControl<TermoAdesaoContadorFormRawValue['id'] | NewTermoAdesaoContador['id']>;
  dataAdesao: FormControl<TermoAdesaoContadorFormRawValue['dataAdesao']>;
  checked: FormControl<TermoAdesaoContadorFormRawValue['checked']>;
  urlDoc: FormControl<TermoAdesaoContadorFormRawValue['urlDoc']>;
  contador: FormControl<TermoAdesaoContadorFormRawValue['contador']>;
  termoDeAdesao: FormControl<TermoAdesaoContadorFormRawValue['termoDeAdesao']>;
};

export type TermoAdesaoContadorFormGroup = FormGroup<TermoAdesaoContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TermoAdesaoContadorFormService {
  createTermoAdesaoContadorFormGroup(termoAdesaoContador: TermoAdesaoContadorFormGroupInput = { id: null }): TermoAdesaoContadorFormGroup {
    const termoAdesaoContadorRawValue = this.convertTermoAdesaoContadorToTermoAdesaoContadorRawValue({
      ...this.getFormDefaults(),
      ...termoAdesaoContador,
    });
    return new FormGroup<TermoAdesaoContadorFormGroupContent>({
      id: new FormControl(
        { value: termoAdesaoContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataAdesao: new FormControl(termoAdesaoContadorRawValue.dataAdesao),
      checked: new FormControl(termoAdesaoContadorRawValue.checked),
      urlDoc: new FormControl(termoAdesaoContadorRawValue.urlDoc),
      contador: new FormControl(termoAdesaoContadorRawValue.contador, {
        validators: [Validators.required],
      }),
      termoDeAdesao: new FormControl(termoAdesaoContadorRawValue.termoDeAdesao, {
        validators: [Validators.required],
      }),
    });
  }

  getTermoAdesaoContador(form: TermoAdesaoContadorFormGroup): ITermoAdesaoContador | NewTermoAdesaoContador {
    return this.convertTermoAdesaoContadorRawValueToTermoAdesaoContador(
      form.getRawValue() as TermoAdesaoContadorFormRawValue | NewTermoAdesaoContadorFormRawValue,
    );
  }

  resetForm(form: TermoAdesaoContadorFormGroup, termoAdesaoContador: TermoAdesaoContadorFormGroupInput): void {
    const termoAdesaoContadorRawValue = this.convertTermoAdesaoContadorToTermoAdesaoContadorRawValue({
      ...this.getFormDefaults(),
      ...termoAdesaoContador,
    });
    form.reset(
      {
        ...termoAdesaoContadorRawValue,
        id: { value: termoAdesaoContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TermoAdesaoContadorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAdesao: currentTime,
      checked: false,
    };
  }

  private convertTermoAdesaoContadorRawValueToTermoAdesaoContador(
    rawTermoAdesaoContador: TermoAdesaoContadorFormRawValue | NewTermoAdesaoContadorFormRawValue,
  ): ITermoAdesaoContador | NewTermoAdesaoContador {
    return {
      ...rawTermoAdesaoContador,
      dataAdesao: dayjs(rawTermoAdesaoContador.dataAdesao, DATE_TIME_FORMAT),
    };
  }

  private convertTermoAdesaoContadorToTermoAdesaoContadorRawValue(
    termoAdesaoContador: ITermoAdesaoContador | (Partial<NewTermoAdesaoContador> & TermoAdesaoContadorFormDefaults),
  ): TermoAdesaoContadorFormRawValue | PartialWithRequiredKeyOf<NewTermoAdesaoContadorFormRawValue> {
    return {
      ...termoAdesaoContador,
      dataAdesao: termoAdesaoContador.dataAdesao ? termoAdesaoContador.dataAdesao.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
