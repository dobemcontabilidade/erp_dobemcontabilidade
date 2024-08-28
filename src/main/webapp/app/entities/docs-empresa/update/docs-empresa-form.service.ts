import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocsEmpresa, NewDocsEmpresa } from '../docs-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocsEmpresa for edit and NewDocsEmpresaFormGroupInput for create.
 */
type DocsEmpresaFormGroupInput = IDocsEmpresa | PartialWithRequiredKeyOf<NewDocsEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocsEmpresa | NewDocsEmpresa> = Omit<T, 'dataEmissao' | 'dataEncerramento'> & {
  dataEmissao?: string | null;
  dataEncerramento?: string | null;
};

type DocsEmpresaFormRawValue = FormValueOf<IDocsEmpresa>;

type NewDocsEmpresaFormRawValue = FormValueOf<NewDocsEmpresa>;

type DocsEmpresaFormDefaults = Pick<NewDocsEmpresa, 'id' | 'dataEmissao' | 'dataEncerramento'>;

type DocsEmpresaFormGroupContent = {
  id: FormControl<DocsEmpresaFormRawValue['id'] | NewDocsEmpresa['id']>;
  documento: FormControl<DocsEmpresaFormRawValue['documento']>;
  descricao: FormControl<DocsEmpresaFormRawValue['descricao']>;
  url: FormControl<DocsEmpresaFormRawValue['url']>;
  dataEmissao: FormControl<DocsEmpresaFormRawValue['dataEmissao']>;
  dataEncerramento: FormControl<DocsEmpresaFormRawValue['dataEncerramento']>;
  orgaoEmissor: FormControl<DocsEmpresaFormRawValue['orgaoEmissor']>;
  pessoaJuridica: FormControl<DocsEmpresaFormRawValue['pessoaJuridica']>;
};

export type DocsEmpresaFormGroup = FormGroup<DocsEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocsEmpresaFormService {
  createDocsEmpresaFormGroup(docsEmpresa: DocsEmpresaFormGroupInput = { id: null }): DocsEmpresaFormGroup {
    const docsEmpresaRawValue = this.convertDocsEmpresaToDocsEmpresaRawValue({
      ...this.getFormDefaults(),
      ...docsEmpresa,
    });
    return new FormGroup<DocsEmpresaFormGroupContent>({
      id: new FormControl(
        { value: docsEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      documento: new FormControl(docsEmpresaRawValue.documento, {
        validators: [Validators.maxLength(300)],
      }),
      descricao: new FormControl(docsEmpresaRawValue.descricao),
      url: new FormControl(docsEmpresaRawValue.url),
      dataEmissao: new FormControl(docsEmpresaRawValue.dataEmissao),
      dataEncerramento: new FormControl(docsEmpresaRawValue.dataEncerramento),
      orgaoEmissor: new FormControl(docsEmpresaRawValue.orgaoEmissor),
      pessoaJuridica: new FormControl(docsEmpresaRawValue.pessoaJuridica, {
        validators: [Validators.required],
      }),
    });
  }

  getDocsEmpresa(form: DocsEmpresaFormGroup): IDocsEmpresa | NewDocsEmpresa {
    return this.convertDocsEmpresaRawValueToDocsEmpresa(form.getRawValue() as DocsEmpresaFormRawValue | NewDocsEmpresaFormRawValue);
  }

  resetForm(form: DocsEmpresaFormGroup, docsEmpresa: DocsEmpresaFormGroupInput): void {
    const docsEmpresaRawValue = this.convertDocsEmpresaToDocsEmpresaRawValue({ ...this.getFormDefaults(), ...docsEmpresa });
    form.reset(
      {
        ...docsEmpresaRawValue,
        id: { value: docsEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DocsEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataEmissao: currentTime,
      dataEncerramento: currentTime,
    };
  }

  private convertDocsEmpresaRawValueToDocsEmpresa(
    rawDocsEmpresa: DocsEmpresaFormRawValue | NewDocsEmpresaFormRawValue,
  ): IDocsEmpresa | NewDocsEmpresa {
    return {
      ...rawDocsEmpresa,
      dataEmissao: dayjs(rawDocsEmpresa.dataEmissao, DATE_TIME_FORMAT),
      dataEncerramento: dayjs(rawDocsEmpresa.dataEncerramento, DATE_TIME_FORMAT),
    };
  }

  private convertDocsEmpresaToDocsEmpresaRawValue(
    docsEmpresa: IDocsEmpresa | (Partial<NewDocsEmpresa> & DocsEmpresaFormDefaults),
  ): DocsEmpresaFormRawValue | PartialWithRequiredKeyOf<NewDocsEmpresaFormRawValue> {
    return {
      ...docsEmpresa,
      dataEmissao: docsEmpresa.dataEmissao ? docsEmpresa.dataEmissao.format(DATE_TIME_FORMAT) : undefined,
      dataEncerramento: docsEmpresa.dataEncerramento ? docsEmpresa.dataEncerramento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
