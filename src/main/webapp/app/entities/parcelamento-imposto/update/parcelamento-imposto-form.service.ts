import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IParcelamentoImposto, NewParcelamentoImposto } from '../parcelamento-imposto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParcelamentoImposto for edit and NewParcelamentoImpostoFormGroupInput for create.
 */
type ParcelamentoImpostoFormGroupInput = IParcelamentoImposto | PartialWithRequiredKeyOf<NewParcelamentoImposto>;

type ParcelamentoImpostoFormDefaults = Pick<NewParcelamentoImposto, 'id'>;

type ParcelamentoImpostoFormGroupContent = {
  id: FormControl<IParcelamentoImposto['id'] | NewParcelamentoImposto['id']>;
  diaVencimento: FormControl<IParcelamentoImposto['diaVencimento']>;
  numeroParcelas: FormControl<IParcelamentoImposto['numeroParcelas']>;
  urlArquivoNegociacao: FormControl<IParcelamentoImposto['urlArquivoNegociacao']>;
  numeroParcelasPagas: FormControl<IParcelamentoImposto['numeroParcelasPagas']>;
  numeroParcelasRegatantes: FormControl<IParcelamentoImposto['numeroParcelasRegatantes']>;
  situacaoSolicitacaoParcelamentoEnum: FormControl<IParcelamentoImposto['situacaoSolicitacaoParcelamentoEnum']>;
  imposto: FormControl<IParcelamentoImposto['imposto']>;
  empresa: FormControl<IParcelamentoImposto['empresa']>;
};

export type ParcelamentoImpostoFormGroup = FormGroup<ParcelamentoImpostoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParcelamentoImpostoFormService {
  createParcelamentoImpostoFormGroup(parcelamentoImposto: ParcelamentoImpostoFormGroupInput = { id: null }): ParcelamentoImpostoFormGroup {
    const parcelamentoImpostoRawValue = {
      ...this.getFormDefaults(),
      ...parcelamentoImposto,
    };
    return new FormGroup<ParcelamentoImpostoFormGroupContent>({
      id: new FormControl(
        { value: parcelamentoImpostoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      diaVencimento: new FormControl(parcelamentoImpostoRawValue.diaVencimento),
      numeroParcelas: new FormControl(parcelamentoImpostoRawValue.numeroParcelas),
      urlArquivoNegociacao: new FormControl(parcelamentoImpostoRawValue.urlArquivoNegociacao),
      numeroParcelasPagas: new FormControl(parcelamentoImpostoRawValue.numeroParcelasPagas),
      numeroParcelasRegatantes: new FormControl(parcelamentoImpostoRawValue.numeroParcelasRegatantes),
      situacaoSolicitacaoParcelamentoEnum: new FormControl(parcelamentoImpostoRawValue.situacaoSolicitacaoParcelamentoEnum),
      imposto: new FormControl(parcelamentoImpostoRawValue.imposto, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(parcelamentoImpostoRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getParcelamentoImposto(form: ParcelamentoImpostoFormGroup): IParcelamentoImposto | NewParcelamentoImposto {
    return form.getRawValue() as IParcelamentoImposto | NewParcelamentoImposto;
  }

  resetForm(form: ParcelamentoImpostoFormGroup, parcelamentoImposto: ParcelamentoImpostoFormGroupInput): void {
    const parcelamentoImpostoRawValue = { ...this.getFormDefaults(), ...parcelamentoImposto };
    form.reset(
      {
        ...parcelamentoImpostoRawValue,
        id: { value: parcelamentoImpostoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ParcelamentoImpostoFormDefaults {
    return {
      id: null,
    };
  }
}
