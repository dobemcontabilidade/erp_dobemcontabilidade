import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICidade, NewCidade } from '../cidade.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICidade for edit and NewCidadeFormGroupInput for create.
 */
type CidadeFormGroupInput = ICidade | PartialWithRequiredKeyOf<NewCidade>;

type CidadeFormDefaults = Pick<NewCidade, 'id' | 'contratacao' | 'abertura'>;

type CidadeFormGroupContent = {
  id: FormControl<ICidade['id'] | NewCidade['id']>;
  nome: FormControl<ICidade['nome']>;
  contratacao: FormControl<ICidade['contratacao']>;
  abertura: FormControl<ICidade['abertura']>;
  estado: FormControl<ICidade['estado']>;
};

export type CidadeFormGroup = FormGroup<CidadeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CidadeFormService {
  createCidadeFormGroup(cidade: CidadeFormGroupInput = { id: null }): CidadeFormGroup {
    const cidadeRawValue = {
      ...this.getFormDefaults(),
      ...cidade,
    };
    return new FormGroup<CidadeFormGroupContent>({
      id: new FormControl(
        { value: cidadeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(cidadeRawValue.nome, {
        validators: [Validators.required],
      }),
      contratacao: new FormControl(cidadeRawValue.contratacao),
      abertura: new FormControl(cidadeRawValue.abertura),
      estado: new FormControl(cidadeRawValue.estado, {
        validators: [Validators.required],
      }),
    });
  }

  getCidade(form: CidadeFormGroup): ICidade | NewCidade {
    return form.getRawValue() as ICidade | NewCidade;
  }

  resetForm(form: CidadeFormGroup, cidade: CidadeFormGroupInput): void {
    const cidadeRawValue = { ...this.getFormDefaults(), ...cidade };
    form.reset(
      {
        ...cidadeRawValue,
        id: { value: cidadeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CidadeFormDefaults {
    return {
      id: null,
      contratacao: false,
      abertura: false,
    };
  }
}
