import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnexoRequeridoServicoContabil, NewAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoRequeridoServicoContabil for edit and NewAnexoRequeridoServicoContabilFormGroupInput for create.
 */
type AnexoRequeridoServicoContabilFormGroupInput =
  | IAnexoRequeridoServicoContabil
  | PartialWithRequiredKeyOf<NewAnexoRequeridoServicoContabil>;

type AnexoRequeridoServicoContabilFormDefaults = Pick<NewAnexoRequeridoServicoContabil, 'id' | 'obrigatorio'>;

type AnexoRequeridoServicoContabilFormGroupContent = {
  id: FormControl<IAnexoRequeridoServicoContabil['id'] | NewAnexoRequeridoServicoContabil['id']>;
  obrigatorio: FormControl<IAnexoRequeridoServicoContabil['obrigatorio']>;
  servicoContabil: FormControl<IAnexoRequeridoServicoContabil['servicoContabil']>;
  anexoRequerido: FormControl<IAnexoRequeridoServicoContabil['anexoRequerido']>;
};

export type AnexoRequeridoServicoContabilFormGroup = FormGroup<AnexoRequeridoServicoContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoServicoContabilFormService {
  createAnexoRequeridoServicoContabilFormGroup(
    anexoRequeridoServicoContabil: AnexoRequeridoServicoContabilFormGroupInput = { id: null },
  ): AnexoRequeridoServicoContabilFormGroup {
    const anexoRequeridoServicoContabilRawValue = {
      ...this.getFormDefaults(),
      ...anexoRequeridoServicoContabil,
    };
    return new FormGroup<AnexoRequeridoServicoContabilFormGroupContent>({
      id: new FormControl(
        { value: anexoRequeridoServicoContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      obrigatorio: new FormControl(anexoRequeridoServicoContabilRawValue.obrigatorio),
      servicoContabil: new FormControl(anexoRequeridoServicoContabilRawValue.servicoContabil, {
        validators: [Validators.required],
      }),
      anexoRequerido: new FormControl(anexoRequeridoServicoContabilRawValue.anexoRequerido, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoRequeridoServicoContabil(
    form: AnexoRequeridoServicoContabilFormGroup,
  ): IAnexoRequeridoServicoContabil | NewAnexoRequeridoServicoContabil {
    return form.getRawValue() as IAnexoRequeridoServicoContabil | NewAnexoRequeridoServicoContabil;
  }

  resetForm(
    form: AnexoRequeridoServicoContabilFormGroup,
    anexoRequeridoServicoContabil: AnexoRequeridoServicoContabilFormGroupInput,
  ): void {
    const anexoRequeridoServicoContabilRawValue = { ...this.getFormDefaults(), ...anexoRequeridoServicoContabil };
    form.reset(
      {
        ...anexoRequeridoServicoContabilRawValue,
        id: { value: anexoRequeridoServicoContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoRequeridoServicoContabilFormDefaults {
    return {
      id: null,
      obrigatorio: false,
    };
  }
}
