import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAtorAvaliado, NewAtorAvaliado } from '../ator-avaliado.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAtorAvaliado for edit and NewAtorAvaliadoFormGroupInput for create.
 */
type AtorAvaliadoFormGroupInput = IAtorAvaliado | PartialWithRequiredKeyOf<NewAtorAvaliado>;

type AtorAvaliadoFormDefaults = Pick<NewAtorAvaliado, 'id' | 'ativo'>;

type AtorAvaliadoFormGroupContent = {
  id: FormControl<IAtorAvaliado['id'] | NewAtorAvaliado['id']>;
  nome: FormControl<IAtorAvaliado['nome']>;
  descricao: FormControl<IAtorAvaliado['descricao']>;
  ativo: FormControl<IAtorAvaliado['ativo']>;
};

export type AtorAvaliadoFormGroup = FormGroup<AtorAvaliadoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AtorAvaliadoFormService {
  createAtorAvaliadoFormGroup(atorAvaliado: AtorAvaliadoFormGroupInput = { id: null }): AtorAvaliadoFormGroup {
    const atorAvaliadoRawValue = {
      ...this.getFormDefaults(),
      ...atorAvaliado,
    };
    return new FormGroup<AtorAvaliadoFormGroupContent>({
      id: new FormControl(
        { value: atorAvaliadoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(atorAvaliadoRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(atorAvaliadoRawValue.descricao),
      ativo: new FormControl(atorAvaliadoRawValue.ativo),
    });
  }

  getAtorAvaliado(form: AtorAvaliadoFormGroup): IAtorAvaliado | NewAtorAvaliado {
    return form.getRawValue() as IAtorAvaliado | NewAtorAvaliado;
  }

  resetForm(form: AtorAvaliadoFormGroup, atorAvaliado: AtorAvaliadoFormGroupInput): void {
    const atorAvaliadoRawValue = { ...this.getFormDefaults(), ...atorAvaliado };
    form.reset(
      {
        ...atorAvaliadoRawValue,
        id: { value: atorAvaliadoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AtorAvaliadoFormDefaults {
    return {
      id: null,
      ativo: false,
    };
  }
}
