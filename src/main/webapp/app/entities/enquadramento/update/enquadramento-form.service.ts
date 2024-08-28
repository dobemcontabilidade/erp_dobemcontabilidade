import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEnquadramento, NewEnquadramento } from '../enquadramento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEnquadramento for edit and NewEnquadramentoFormGroupInput for create.
 */
type EnquadramentoFormGroupInput = IEnquadramento | PartialWithRequiredKeyOf<NewEnquadramento>;

type EnquadramentoFormDefaults = Pick<NewEnquadramento, 'id'>;

type EnquadramentoFormGroupContent = {
  id: FormControl<IEnquadramento['id'] | NewEnquadramento['id']>;
  nome: FormControl<IEnquadramento['nome']>;
  sigla: FormControl<IEnquadramento['sigla']>;
  limiteInicial: FormControl<IEnquadramento['limiteInicial']>;
  limiteFinal: FormControl<IEnquadramento['limiteFinal']>;
  descricao: FormControl<IEnquadramento['descricao']>;
};

export type EnquadramentoFormGroup = FormGroup<EnquadramentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EnquadramentoFormService {
  createEnquadramentoFormGroup(enquadramento: EnquadramentoFormGroupInput = { id: null }): EnquadramentoFormGroup {
    const enquadramentoRawValue = {
      ...this.getFormDefaults(),
      ...enquadramento,
    };
    return new FormGroup<EnquadramentoFormGroupContent>({
      id: new FormControl(
        { value: enquadramentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(enquadramentoRawValue.nome),
      sigla: new FormControl(enquadramentoRawValue.sigla),
      limiteInicial: new FormControl(enquadramentoRawValue.limiteInicial),
      limiteFinal: new FormControl(enquadramentoRawValue.limiteFinal),
      descricao: new FormControl(enquadramentoRawValue.descricao),
    });
  }

  getEnquadramento(form: EnquadramentoFormGroup): IEnquadramento | NewEnquadramento {
    return form.getRawValue() as IEnquadramento | NewEnquadramento;
  }

  resetForm(form: EnquadramentoFormGroup, enquadramento: EnquadramentoFormGroupInput): void {
    const enquadramentoRawValue = { ...this.getFormDefaults(), ...enquadramento };
    form.reset(
      {
        ...enquadramentoRawValue,
        id: { value: enquadramentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EnquadramentoFormDefaults {
    return {
      id: null,
    };
  }
}
