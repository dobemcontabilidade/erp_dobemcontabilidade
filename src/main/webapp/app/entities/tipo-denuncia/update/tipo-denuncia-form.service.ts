import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITipoDenuncia, NewTipoDenuncia } from '../tipo-denuncia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITipoDenuncia for edit and NewTipoDenunciaFormGroupInput for create.
 */
type TipoDenunciaFormGroupInput = ITipoDenuncia | PartialWithRequiredKeyOf<NewTipoDenuncia>;

type TipoDenunciaFormDefaults = Pick<NewTipoDenuncia, 'id'>;

type TipoDenunciaFormGroupContent = {
  id: FormControl<ITipoDenuncia['id'] | NewTipoDenuncia['id']>;
  tipo: FormControl<ITipoDenuncia['tipo']>;
  descricao: FormControl<ITipoDenuncia['descricao']>;
};

export type TipoDenunciaFormGroup = FormGroup<TipoDenunciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TipoDenunciaFormService {
  createTipoDenunciaFormGroup(tipoDenuncia: TipoDenunciaFormGroupInput = { id: null }): TipoDenunciaFormGroup {
    const tipoDenunciaRawValue = {
      ...this.getFormDefaults(),
      ...tipoDenuncia,
    };
    return new FormGroup<TipoDenunciaFormGroupContent>({
      id: new FormControl(
        { value: tipoDenunciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tipo: new FormControl(tipoDenunciaRawValue.tipo),
      descricao: new FormControl(tipoDenunciaRawValue.descricao),
    });
  }

  getTipoDenuncia(form: TipoDenunciaFormGroup): ITipoDenuncia | NewTipoDenuncia {
    return form.getRawValue() as ITipoDenuncia | NewTipoDenuncia;
  }

  resetForm(form: TipoDenunciaFormGroup, tipoDenuncia: TipoDenunciaFormGroupInput): void {
    const tipoDenunciaRawValue = { ...this.getFormDefaults(), ...tipoDenuncia };
    form.reset(
      {
        ...tipoDenunciaRawValue,
        id: { value: tipoDenunciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TipoDenunciaFormDefaults {
    return {
      id: null,
    };
  }
}
