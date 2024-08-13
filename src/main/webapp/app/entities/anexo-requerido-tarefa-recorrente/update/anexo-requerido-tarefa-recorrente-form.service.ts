import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnexoRequeridoTarefaRecorrente, NewAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoRequeridoTarefaRecorrente for edit and NewAnexoRequeridoTarefaRecorrenteFormGroupInput for create.
 */
type AnexoRequeridoTarefaRecorrenteFormGroupInput =
  | IAnexoRequeridoTarefaRecorrente
  | PartialWithRequiredKeyOf<NewAnexoRequeridoTarefaRecorrente>;

type AnexoRequeridoTarefaRecorrenteFormDefaults = Pick<NewAnexoRequeridoTarefaRecorrente, 'id' | 'obrigatorio'>;

type AnexoRequeridoTarefaRecorrenteFormGroupContent = {
  id: FormControl<IAnexoRequeridoTarefaRecorrente['id'] | NewAnexoRequeridoTarefaRecorrente['id']>;
  obrigatorio: FormControl<IAnexoRequeridoTarefaRecorrente['obrigatorio']>;
  anexoRequerido: FormControl<IAnexoRequeridoTarefaRecorrente['anexoRequerido']>;
  tarefaRecorrente: FormControl<IAnexoRequeridoTarefaRecorrente['tarefaRecorrente']>;
};

export type AnexoRequeridoTarefaRecorrenteFormGroup = FormGroup<AnexoRequeridoTarefaRecorrenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoTarefaRecorrenteFormService {
  createAnexoRequeridoTarefaRecorrenteFormGroup(
    anexoRequeridoTarefaRecorrente: AnexoRequeridoTarefaRecorrenteFormGroupInput = { id: null },
  ): AnexoRequeridoTarefaRecorrenteFormGroup {
    const anexoRequeridoTarefaRecorrenteRawValue = {
      ...this.getFormDefaults(),
      ...anexoRequeridoTarefaRecorrente,
    };
    return new FormGroup<AnexoRequeridoTarefaRecorrenteFormGroupContent>({
      id: new FormControl(
        { value: anexoRequeridoTarefaRecorrenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      obrigatorio: new FormControl(anexoRequeridoTarefaRecorrenteRawValue.obrigatorio),
      anexoRequerido: new FormControl(anexoRequeridoTarefaRecorrenteRawValue.anexoRequerido, {
        validators: [Validators.required],
      }),
      tarefaRecorrente: new FormControl(anexoRequeridoTarefaRecorrenteRawValue.tarefaRecorrente, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoRequeridoTarefaRecorrente(
    form: AnexoRequeridoTarefaRecorrenteFormGroup,
  ): IAnexoRequeridoTarefaRecorrente | NewAnexoRequeridoTarefaRecorrente {
    return form.getRawValue() as IAnexoRequeridoTarefaRecorrente | NewAnexoRequeridoTarefaRecorrente;
  }

  resetForm(
    form: AnexoRequeridoTarefaRecorrenteFormGroup,
    anexoRequeridoTarefaRecorrente: AnexoRequeridoTarefaRecorrenteFormGroupInput,
  ): void {
    const anexoRequeridoTarefaRecorrenteRawValue = { ...this.getFormDefaults(), ...anexoRequeridoTarefaRecorrente };
    form.reset(
      {
        ...anexoRequeridoTarefaRecorrenteRawValue,
        id: { value: anexoRequeridoTarefaRecorrenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoRequeridoTarefaRecorrenteFormDefaults {
    return {
      id: null,
      obrigatorio: false,
    };
  }
}
