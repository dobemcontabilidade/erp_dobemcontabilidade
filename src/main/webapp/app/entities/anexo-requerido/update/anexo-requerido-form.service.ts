import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnexoRequerido, NewAnexoRequerido } from '../anexo-requerido.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoRequerido for edit and NewAnexoRequeridoFormGroupInput for create.
 */
type AnexoRequeridoFormGroupInput = IAnexoRequerido | PartialWithRequiredKeyOf<NewAnexoRequerido>;

type AnexoRequeridoFormDefaults = Pick<NewAnexoRequerido, 'id'>;

type AnexoRequeridoFormGroupContent = {
  id: FormControl<IAnexoRequerido['id'] | NewAnexoRequerido['id']>;
  nome: FormControl<IAnexoRequerido['nome']>;
  tipo: FormControl<IAnexoRequerido['tipo']>;
  descricao: FormControl<IAnexoRequerido['descricao']>;
};

export type AnexoRequeridoFormGroup = FormGroup<AnexoRequeridoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoFormService {
  createAnexoRequeridoFormGroup(anexoRequerido: AnexoRequeridoFormGroupInput = { id: null }): AnexoRequeridoFormGroup {
    const anexoRequeridoRawValue = {
      ...this.getFormDefaults(),
      ...anexoRequerido,
    };
    return new FormGroup<AnexoRequeridoFormGroupContent>({
      id: new FormControl(
        { value: anexoRequeridoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(anexoRequeridoRawValue.nome, {
        validators: [Validators.required],
      }),
      tipo: new FormControl(anexoRequeridoRawValue.tipo),
      descricao: new FormControl(anexoRequeridoRawValue.descricao),
    });
  }

  getAnexoRequerido(form: AnexoRequeridoFormGroup): IAnexoRequerido | NewAnexoRequerido {
    return form.getRawValue() as IAnexoRequerido | NewAnexoRequerido;
  }

  resetForm(form: AnexoRequeridoFormGroup, anexoRequerido: AnexoRequeridoFormGroupInput): void {
    const anexoRequeridoRawValue = { ...this.getFormDefaults(), ...anexoRequerido };
    form.reset(
      {
        ...anexoRequeridoRawValue,
        id: { value: anexoRequeridoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoRequeridoFormDefaults {
    return {
      id: null,
    };
  }
}
