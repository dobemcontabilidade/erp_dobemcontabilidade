import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITarefaRecorrenteEmpresaModelo, NewTarefaRecorrenteEmpresaModelo } from '../tarefa-recorrente-empresa-modelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarefaRecorrenteEmpresaModelo for edit and NewTarefaRecorrenteEmpresaModeloFormGroupInput for create.
 */
type TarefaRecorrenteEmpresaModeloFormGroupInput =
  | ITarefaRecorrenteEmpresaModelo
  | PartialWithRequiredKeyOf<NewTarefaRecorrenteEmpresaModelo>;

type TarefaRecorrenteEmpresaModeloFormDefaults = Pick<NewTarefaRecorrenteEmpresaModelo, 'id'>;

type TarefaRecorrenteEmpresaModeloFormGroupContent = {
  id: FormControl<ITarefaRecorrenteEmpresaModelo['id'] | NewTarefaRecorrenteEmpresaModelo['id']>;
  diaAdmin: FormControl<ITarefaRecorrenteEmpresaModelo['diaAdmin']>;
  mesLegal: FormControl<ITarefaRecorrenteEmpresaModelo['mesLegal']>;
  recorrencia: FormControl<ITarefaRecorrenteEmpresaModelo['recorrencia']>;
  servicoContabilEmpresaModelo: FormControl<ITarefaRecorrenteEmpresaModelo['servicoContabilEmpresaModelo']>;
};

export type TarefaRecorrenteEmpresaModeloFormGroup = FormGroup<TarefaRecorrenteEmpresaModeloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TarefaRecorrenteEmpresaModeloFormService {
  createTarefaRecorrenteEmpresaModeloFormGroup(
    tarefaRecorrenteEmpresaModelo: TarefaRecorrenteEmpresaModeloFormGroupInput = { id: null },
  ): TarefaRecorrenteEmpresaModeloFormGroup {
    const tarefaRecorrenteEmpresaModeloRawValue = {
      ...this.getFormDefaults(),
      ...tarefaRecorrenteEmpresaModelo,
    };
    return new FormGroup<TarefaRecorrenteEmpresaModeloFormGroupContent>({
      id: new FormControl(
        { value: tarefaRecorrenteEmpresaModeloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      diaAdmin: new FormControl(tarefaRecorrenteEmpresaModeloRawValue.diaAdmin),
      mesLegal: new FormControl(tarefaRecorrenteEmpresaModeloRawValue.mesLegal),
      recorrencia: new FormControl(tarefaRecorrenteEmpresaModeloRawValue.recorrencia),
      servicoContabilEmpresaModelo: new FormControl(tarefaRecorrenteEmpresaModeloRawValue.servicoContabilEmpresaModelo, {
        validators: [Validators.required],
      }),
    });
  }

  getTarefaRecorrenteEmpresaModelo(
    form: TarefaRecorrenteEmpresaModeloFormGroup,
  ): ITarefaRecorrenteEmpresaModelo | NewTarefaRecorrenteEmpresaModelo {
    return form.getRawValue() as ITarefaRecorrenteEmpresaModelo | NewTarefaRecorrenteEmpresaModelo;
  }

  resetForm(
    form: TarefaRecorrenteEmpresaModeloFormGroup,
    tarefaRecorrenteEmpresaModelo: TarefaRecorrenteEmpresaModeloFormGroupInput,
  ): void {
    const tarefaRecorrenteEmpresaModeloRawValue = { ...this.getFormDefaults(), ...tarefaRecorrenteEmpresaModelo };
    form.reset(
      {
        ...tarefaRecorrenteEmpresaModeloRawValue,
        id: { value: tarefaRecorrenteEmpresaModeloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TarefaRecorrenteEmpresaModeloFormDefaults {
    return {
      id: null,
    };
  }
}
