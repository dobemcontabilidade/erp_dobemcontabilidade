import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITermoContratoContabil, NewTermoContratoContabil } from '../termo-contrato-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITermoContratoContabil for edit and NewTermoContratoContabilFormGroupInput for create.
 */
type TermoContratoContabilFormGroupInput = ITermoContratoContabil | PartialWithRequiredKeyOf<NewTermoContratoContabil>;

type TermoContratoContabilFormDefaults = Pick<NewTermoContratoContabil, 'id'>;

type TermoContratoContabilFormGroupContent = {
  id: FormControl<ITermoContratoContabil['id'] | NewTermoContratoContabil['id']>;
  documento: FormControl<ITermoContratoContabil['documento']>;
  descricao: FormControl<ITermoContratoContabil['descricao']>;
  titulo: FormControl<ITermoContratoContabil['titulo']>;
  planoContabil: FormControl<ITermoContratoContabil['planoContabil']>;
};

export type TermoContratoContabilFormGroup = FormGroup<TermoContratoContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TermoContratoContabilFormService {
  createTermoContratoContabilFormGroup(
    termoContratoContabil: TermoContratoContabilFormGroupInput = { id: null },
  ): TermoContratoContabilFormGroup {
    const termoContratoContabilRawValue = {
      ...this.getFormDefaults(),
      ...termoContratoContabil,
    };
    return new FormGroup<TermoContratoContabilFormGroupContent>({
      id: new FormControl(
        { value: termoContratoContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      documento: new FormControl(termoContratoContabilRawValue.documento),
      descricao: new FormControl(termoContratoContabilRawValue.descricao),
      titulo: new FormControl(termoContratoContabilRawValue.titulo),
      planoContabil: new FormControl(termoContratoContabilRawValue.planoContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getTermoContratoContabil(form: TermoContratoContabilFormGroup): ITermoContratoContabil | NewTermoContratoContabil {
    return form.getRawValue() as ITermoContratoContabil | NewTermoContratoContabil;
  }

  resetForm(form: TermoContratoContabilFormGroup, termoContratoContabil: TermoContratoContabilFormGroupInput): void {
    const termoContratoContabilRawValue = { ...this.getFormDefaults(), ...termoContratoContabil };
    form.reset(
      {
        ...termoContratoContabilRawValue,
        id: { value: termoContratoContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TermoContratoContabilFormDefaults {
    return {
      id: null,
    };
  }
}
