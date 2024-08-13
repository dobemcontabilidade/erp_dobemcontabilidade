import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITributacao, NewTributacao } from '../tributacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITributacao for edit and NewTributacaoFormGroupInput for create.
 */
type TributacaoFormGroupInput = ITributacao | PartialWithRequiredKeyOf<NewTributacao>;

type TributacaoFormDefaults = Pick<NewTributacao, 'id'>;

type TributacaoFormGroupContent = {
  id: FormControl<ITributacao['id'] | NewTributacao['id']>;
  nome: FormControl<ITributacao['nome']>;
  descricao: FormControl<ITributacao['descricao']>;
  situacao: FormControl<ITributacao['situacao']>;
};

export type TributacaoFormGroup = FormGroup<TributacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TributacaoFormService {
  createTributacaoFormGroup(tributacao: TributacaoFormGroupInput = { id: null }): TributacaoFormGroup {
    const tributacaoRawValue = {
      ...this.getFormDefaults(),
      ...tributacao,
    };
    return new FormGroup<TributacaoFormGroupContent>({
      id: new FormControl(
        { value: tributacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(tributacaoRawValue.nome, {
        validators: [Validators.maxLength(200)],
      }),
      descricao: new FormControl(tributacaoRawValue.descricao),
      situacao: new FormControl(tributacaoRawValue.situacao),
    });
  }

  getTributacao(form: TributacaoFormGroup): ITributacao | NewTributacao {
    return form.getRawValue() as ITributacao | NewTributacao;
  }

  resetForm(form: TributacaoFormGroup, tributacao: TributacaoFormGroupInput): void {
    const tributacaoRawValue = { ...this.getFormDefaults(), ...tributacao };
    form.reset(
      {
        ...tributacaoRawValue,
        id: { value: tributacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TributacaoFormDefaults {
    return {
      id: null,
    };
  }
}
