import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAreaContabilEmpresa, NewAreaContabilEmpresa } from '../area-contabil-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAreaContabilEmpresa for edit and NewAreaContabilEmpresaFormGroupInput for create.
 */
type AreaContabilEmpresaFormGroupInput = IAreaContabilEmpresa | PartialWithRequiredKeyOf<NewAreaContabilEmpresa>;

type AreaContabilEmpresaFormDefaults = Pick<NewAreaContabilEmpresa, 'id'>;

type AreaContabilEmpresaFormGroupContent = {
  id: FormControl<IAreaContabilEmpresa['id'] | NewAreaContabilEmpresa['id']>;
  pontuacao: FormControl<IAreaContabilEmpresa['pontuacao']>;
  depoimento: FormControl<IAreaContabilEmpresa['depoimento']>;
  reclamacao: FormControl<IAreaContabilEmpresa['reclamacao']>;
  contador: FormControl<IAreaContabilEmpresa['contador']>;
};

export type AreaContabilEmpresaFormGroup = FormGroup<AreaContabilEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AreaContabilEmpresaFormService {
  createAreaContabilEmpresaFormGroup(areaContabilEmpresa: AreaContabilEmpresaFormGroupInput = { id: null }): AreaContabilEmpresaFormGroup {
    const areaContabilEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...areaContabilEmpresa,
    };
    return new FormGroup<AreaContabilEmpresaFormGroupContent>({
      id: new FormControl(
        { value: areaContabilEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      pontuacao: new FormControl(areaContabilEmpresaRawValue.pontuacao),
      depoimento: new FormControl(areaContabilEmpresaRawValue.depoimento),
      reclamacao: new FormControl(areaContabilEmpresaRawValue.reclamacao),
      contador: new FormControl(areaContabilEmpresaRawValue.contador, {
        validators: [Validators.required],
      }),
    });
  }

  getAreaContabilEmpresa(form: AreaContabilEmpresaFormGroup): IAreaContabilEmpresa | NewAreaContabilEmpresa {
    return form.getRawValue() as IAreaContabilEmpresa | NewAreaContabilEmpresa;
  }

  resetForm(form: AreaContabilEmpresaFormGroup, areaContabilEmpresa: AreaContabilEmpresaFormGroupInput): void {
    const areaContabilEmpresaRawValue = { ...this.getFormDefaults(), ...areaContabilEmpresa };
    form.reset(
      {
        ...areaContabilEmpresaRawValue,
        id: { value: areaContabilEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AreaContabilEmpresaFormDefaults {
    return {
      id: null,
    };
  }
}
