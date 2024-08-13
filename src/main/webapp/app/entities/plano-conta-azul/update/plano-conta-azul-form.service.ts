import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlanoContaAzul, NewPlanoContaAzul } from '../plano-conta-azul.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanoContaAzul for edit and NewPlanoContaAzulFormGroupInput for create.
 */
type PlanoContaAzulFormGroupInput = IPlanoContaAzul | PartialWithRequiredKeyOf<NewPlanoContaAzul>;

type PlanoContaAzulFormDefaults = Pick<NewPlanoContaAzul, 'id' | 'suporte'>;

type PlanoContaAzulFormGroupContent = {
  id: FormControl<IPlanoContaAzul['id'] | NewPlanoContaAzul['id']>;
  nome: FormControl<IPlanoContaAzul['nome']>;
  valorBase: FormControl<IPlanoContaAzul['valorBase']>;
  usuarios: FormControl<IPlanoContaAzul['usuarios']>;
  boletos: FormControl<IPlanoContaAzul['boletos']>;
  notaFiscalProduto: FormControl<IPlanoContaAzul['notaFiscalProduto']>;
  notaFiscalServico: FormControl<IPlanoContaAzul['notaFiscalServico']>;
  notaFiscalCe: FormControl<IPlanoContaAzul['notaFiscalCe']>;
  suporte: FormControl<IPlanoContaAzul['suporte']>;
  situacao: FormControl<IPlanoContaAzul['situacao']>;
};

export type PlanoContaAzulFormGroup = FormGroup<PlanoContaAzulFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanoContaAzulFormService {
  createPlanoContaAzulFormGroup(planoContaAzul: PlanoContaAzulFormGroupInput = { id: null }): PlanoContaAzulFormGroup {
    const planoContaAzulRawValue = {
      ...this.getFormDefaults(),
      ...planoContaAzul,
    };
    return new FormGroup<PlanoContaAzulFormGroupContent>({
      id: new FormControl(
        { value: planoContaAzulRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(planoContaAzulRawValue.nome),
      valorBase: new FormControl(planoContaAzulRawValue.valorBase),
      usuarios: new FormControl(planoContaAzulRawValue.usuarios),
      boletos: new FormControl(planoContaAzulRawValue.boletos),
      notaFiscalProduto: new FormControl(planoContaAzulRawValue.notaFiscalProduto),
      notaFiscalServico: new FormControl(planoContaAzulRawValue.notaFiscalServico),
      notaFiscalCe: new FormControl(planoContaAzulRawValue.notaFiscalCe),
      suporte: new FormControl(planoContaAzulRawValue.suporte),
      situacao: new FormControl(planoContaAzulRawValue.situacao),
    });
  }

  getPlanoContaAzul(form: PlanoContaAzulFormGroup): IPlanoContaAzul | NewPlanoContaAzul {
    return form.getRawValue() as IPlanoContaAzul | NewPlanoContaAzul;
  }

  resetForm(form: PlanoContaAzulFormGroup, planoContaAzul: PlanoContaAzulFormGroupInput): void {
    const planoContaAzulRawValue = { ...this.getFormDefaults(), ...planoContaAzul };
    form.reset(
      {
        ...planoContaAzulRawValue,
        id: { value: planoContaAzulRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlanoContaAzulFormDefaults {
    return {
      id: null,
      suporte: false,
    };
  }
}
