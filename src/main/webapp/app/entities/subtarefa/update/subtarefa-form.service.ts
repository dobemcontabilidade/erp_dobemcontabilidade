import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISubtarefa, NewSubtarefa } from '../subtarefa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISubtarefa for edit and NewSubtarefaFormGroupInput for create.
 */
type SubtarefaFormGroupInput = ISubtarefa | PartialWithRequiredKeyOf<NewSubtarefa>;

type SubtarefaFormDefaults = Pick<NewSubtarefa, 'id'>;

type SubtarefaFormGroupContent = {
  id: FormControl<ISubtarefa['id'] | NewSubtarefa['id']>;
  ordem: FormControl<ISubtarefa['ordem']>;
  item: FormControl<ISubtarefa['item']>;
  descricao: FormControl<ISubtarefa['descricao']>;
  tarefa: FormControl<ISubtarefa['tarefa']>;
};

export type SubtarefaFormGroup = FormGroup<SubtarefaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SubtarefaFormService {
  createSubtarefaFormGroup(subtarefa: SubtarefaFormGroupInput = { id: null }): SubtarefaFormGroup {
    const subtarefaRawValue = {
      ...this.getFormDefaults(),
      ...subtarefa,
    };
    return new FormGroup<SubtarefaFormGroupContent>({
      id: new FormControl(
        { value: subtarefaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ordem: new FormControl(subtarefaRawValue.ordem),
      item: new FormControl(subtarefaRawValue.item),
      descricao: new FormControl(subtarefaRawValue.descricao),
      tarefa: new FormControl(subtarefaRawValue.tarefa, {
        validators: [Validators.required],
      }),
    });
  }

  getSubtarefa(form: SubtarefaFormGroup): ISubtarefa | NewSubtarefa {
    return form.getRawValue() as ISubtarefa | NewSubtarefa;
  }

  resetForm(form: SubtarefaFormGroup, subtarefa: SubtarefaFormGroupInput): void {
    const subtarefaRawValue = { ...this.getFormDefaults(), ...subtarefa };
    form.reset(
      {
        ...subtarefaRawValue,
        id: { value: subtarefaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SubtarefaFormDefaults {
    return {
      id: null,
    };
  }
}
