import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAdicionalTributacao, NewAdicionalTributacao } from '../adicional-tributacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAdicionalTributacao for edit and NewAdicionalTributacaoFormGroupInput for create.
 */
type AdicionalTributacaoFormGroupInput = IAdicionalTributacao | PartialWithRequiredKeyOf<NewAdicionalTributacao>;

type AdicionalTributacaoFormDefaults = Pick<NewAdicionalTributacao, 'id'>;

type AdicionalTributacaoFormGroupContent = {
  id: FormControl<IAdicionalTributacao['id'] | NewAdicionalTributacao['id']>;
  valor: FormControl<IAdicionalTributacao['valor']>;
  tributacao: FormControl<IAdicionalTributacao['tributacao']>;
  planoAssinaturaContabil: FormControl<IAdicionalTributacao['planoAssinaturaContabil']>;
};

export type AdicionalTributacaoFormGroup = FormGroup<AdicionalTributacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AdicionalTributacaoFormService {
  createAdicionalTributacaoFormGroup(adicionalTributacao: AdicionalTributacaoFormGroupInput = { id: null }): AdicionalTributacaoFormGroup {
    const adicionalTributacaoRawValue = {
      ...this.getFormDefaults(),
      ...adicionalTributacao,
    };
    return new FormGroup<AdicionalTributacaoFormGroupContent>({
      id: new FormControl(
        { value: adicionalTributacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valor: new FormControl(adicionalTributacaoRawValue.valor, {
        validators: [Validators.required],
      }),
      tributacao: new FormControl(adicionalTributacaoRawValue.tributacao, {
        validators: [Validators.required],
      }),
      planoAssinaturaContabil: new FormControl(adicionalTributacaoRawValue.planoAssinaturaContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getAdicionalTributacao(form: AdicionalTributacaoFormGroup): IAdicionalTributacao | NewAdicionalTributacao {
    return form.getRawValue() as IAdicionalTributacao | NewAdicionalTributacao;
  }

  resetForm(form: AdicionalTributacaoFormGroup, adicionalTributacao: AdicionalTributacaoFormGroupInput): void {
    const adicionalTributacaoRawValue = { ...this.getFormDefaults(), ...adicionalTributacao };
    form.reset(
      {
        ...adicionalTributacaoRawValue,
        id: { value: adicionalTributacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AdicionalTributacaoFormDefaults {
    return {
      id: null,
    };
  }
}
