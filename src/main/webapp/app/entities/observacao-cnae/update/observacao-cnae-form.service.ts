import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IObservacaoCnae, NewObservacaoCnae } from '../observacao-cnae.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IObservacaoCnae for edit and NewObservacaoCnaeFormGroupInput for create.
 */
type ObservacaoCnaeFormGroupInput = IObservacaoCnae | PartialWithRequiredKeyOf<NewObservacaoCnae>;

type ObservacaoCnaeFormDefaults = Pick<NewObservacaoCnae, 'id'>;

type ObservacaoCnaeFormGroupContent = {
  id: FormControl<IObservacaoCnae['id'] | NewObservacaoCnae['id']>;
  descricao: FormControl<IObservacaoCnae['descricao']>;
  subclasse: FormControl<IObservacaoCnae['subclasse']>;
};

export type ObservacaoCnaeFormGroup = FormGroup<ObservacaoCnaeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ObservacaoCnaeFormService {
  createObservacaoCnaeFormGroup(observacaoCnae: ObservacaoCnaeFormGroupInput = { id: null }): ObservacaoCnaeFormGroup {
    const observacaoCnaeRawValue = {
      ...this.getFormDefaults(),
      ...observacaoCnae,
    };
    return new FormGroup<ObservacaoCnaeFormGroupContent>({
      id: new FormControl(
        { value: observacaoCnaeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descricao: new FormControl(observacaoCnaeRawValue.descricao, {
        validators: [Validators.required],
      }),
      subclasse: new FormControl(observacaoCnaeRawValue.subclasse, {
        validators: [Validators.required],
      }),
    });
  }

  getObservacaoCnae(form: ObservacaoCnaeFormGroup): IObservacaoCnae | NewObservacaoCnae {
    return form.getRawValue() as IObservacaoCnae | NewObservacaoCnae;
  }

  resetForm(form: ObservacaoCnaeFormGroup, observacaoCnae: ObservacaoCnaeFormGroupInput): void {
    const observacaoCnaeRawValue = { ...this.getFormDefaults(), ...observacaoCnae };
    form.reset(
      {
        ...observacaoCnaeRawValue,
        id: { value: observacaoCnaeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ObservacaoCnaeFormDefaults {
    return {
      id: null,
    };
  }
}
