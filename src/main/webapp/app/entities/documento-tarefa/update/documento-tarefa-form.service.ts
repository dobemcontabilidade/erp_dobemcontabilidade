import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDocumentoTarefa, NewDocumentoTarefa } from '../documento-tarefa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocumentoTarefa for edit and NewDocumentoTarefaFormGroupInput for create.
 */
type DocumentoTarefaFormGroupInput = IDocumentoTarefa | PartialWithRequiredKeyOf<NewDocumentoTarefa>;

type DocumentoTarefaFormDefaults = Pick<NewDocumentoTarefa, 'id'>;

type DocumentoTarefaFormGroupContent = {
  id: FormControl<IDocumentoTarefa['id'] | NewDocumentoTarefa['id']>;
  nome: FormControl<IDocumentoTarefa['nome']>;
  tarefa: FormControl<IDocumentoTarefa['tarefa']>;
};

export type DocumentoTarefaFormGroup = FormGroup<DocumentoTarefaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentoTarefaFormService {
  createDocumentoTarefaFormGroup(documentoTarefa: DocumentoTarefaFormGroupInput = { id: null }): DocumentoTarefaFormGroup {
    const documentoTarefaRawValue = {
      ...this.getFormDefaults(),
      ...documentoTarefa,
    };
    return new FormGroup<DocumentoTarefaFormGroupContent>({
      id: new FormControl(
        { value: documentoTarefaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(documentoTarefaRawValue.nome),
      tarefa: new FormControl(documentoTarefaRawValue.tarefa, {
        validators: [Validators.required],
      }),
    });
  }

  getDocumentoTarefa(form: DocumentoTarefaFormGroup): IDocumentoTarefa | NewDocumentoTarefa {
    return form.getRawValue() as IDocumentoTarefa | NewDocumentoTarefa;
  }

  resetForm(form: DocumentoTarefaFormGroup, documentoTarefa: DocumentoTarefaFormGroupInput): void {
    const documentoTarefaRawValue = { ...this.getFormDefaults(), ...documentoTarefa };
    form.reset(
      {
        ...documentoTarefaRawValue,
        id: { value: documentoTarefaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DocumentoTarefaFormDefaults {
    return {
      id: null,
    };
  }
}
