import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IImposto, NewImposto } from '../imposto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImposto for edit and NewImpostoFormGroupInput for create.
 */
type ImpostoFormGroupInput = IImposto | PartialWithRequiredKeyOf<NewImposto>;

type ImpostoFormDefaults = Pick<NewImposto, 'id'>;

type ImpostoFormGroupContent = {
  id: FormControl<IImposto['id'] | NewImposto['id']>;
  nome: FormControl<IImposto['nome']>;
  sigla: FormControl<IImposto['sigla']>;
  descricao: FormControl<IImposto['descricao']>;
  esfera: FormControl<IImposto['esfera']>;
};

export type ImpostoFormGroup = FormGroup<ImpostoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImpostoFormService {
  createImpostoFormGroup(imposto: ImpostoFormGroupInput = { id: null }): ImpostoFormGroup {
    const impostoRawValue = {
      ...this.getFormDefaults(),
      ...imposto,
    };
    return new FormGroup<ImpostoFormGroupContent>({
      id: new FormControl(
        { value: impostoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(impostoRawValue.nome),
      sigla: new FormControl(impostoRawValue.sigla),
      descricao: new FormControl(impostoRawValue.descricao),
      esfera: new FormControl(impostoRawValue.esfera, {
        validators: [Validators.required],
      }),
    });
  }

  getImposto(form: ImpostoFormGroup): IImposto | NewImposto {
    return form.getRawValue() as IImposto | NewImposto;
  }

  resetForm(form: ImpostoFormGroup, imposto: ImpostoFormGroupInput): void {
    const impostoRawValue = { ...this.getFormDefaults(), ...imposto };
    form.reset(
      {
        ...impostoRawValue,
        id: { value: impostoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ImpostoFormDefaults {
    return {
      id: null,
    };
  }
}
