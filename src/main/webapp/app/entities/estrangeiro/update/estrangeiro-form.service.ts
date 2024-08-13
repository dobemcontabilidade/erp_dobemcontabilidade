import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEstrangeiro, NewEstrangeiro } from '../estrangeiro.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEstrangeiro for edit and NewEstrangeiroFormGroupInput for create.
 */
type EstrangeiroFormGroupInput = IEstrangeiro | PartialWithRequiredKeyOf<NewEstrangeiro>;

type EstrangeiroFormDefaults = Pick<NewEstrangeiro, 'id' | 'casadoComBrasileiro' | 'filhosComBrasileiro' | 'checked'>;

type EstrangeiroFormGroupContent = {
  id: FormControl<IEstrangeiro['id'] | NewEstrangeiro['id']>;
  dataChegada: FormControl<IEstrangeiro['dataChegada']>;
  dataNaturalizacao: FormControl<IEstrangeiro['dataNaturalizacao']>;
  casadoComBrasileiro: FormControl<IEstrangeiro['casadoComBrasileiro']>;
  filhosComBrasileiro: FormControl<IEstrangeiro['filhosComBrasileiro']>;
  checked: FormControl<IEstrangeiro['checked']>;
  funcionario: FormControl<IEstrangeiro['funcionario']>;
};

export type EstrangeiroFormGroup = FormGroup<EstrangeiroFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EstrangeiroFormService {
  createEstrangeiroFormGroup(estrangeiro: EstrangeiroFormGroupInput = { id: null }): EstrangeiroFormGroup {
    const estrangeiroRawValue = {
      ...this.getFormDefaults(),
      ...estrangeiro,
    };
    return new FormGroup<EstrangeiroFormGroupContent>({
      id: new FormControl(
        { value: estrangeiroRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataChegada: new FormControl(estrangeiroRawValue.dataChegada),
      dataNaturalizacao: new FormControl(estrangeiroRawValue.dataNaturalizacao),
      casadoComBrasileiro: new FormControl(estrangeiroRawValue.casadoComBrasileiro),
      filhosComBrasileiro: new FormControl(estrangeiroRawValue.filhosComBrasileiro),
      checked: new FormControl(estrangeiroRawValue.checked),
      funcionario: new FormControl(estrangeiroRawValue.funcionario, {
        validators: [Validators.required],
      }),
    });
  }

  getEstrangeiro(form: EstrangeiroFormGroup): IEstrangeiro | NewEstrangeiro {
    return form.getRawValue() as IEstrangeiro | NewEstrangeiro;
  }

  resetForm(form: EstrangeiroFormGroup, estrangeiro: EstrangeiroFormGroupInput): void {
    const estrangeiroRawValue = { ...this.getFormDefaults(), ...estrangeiro };
    form.reset(
      {
        ...estrangeiroRawValue,
        id: { value: estrangeiroRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EstrangeiroFormDefaults {
    return {
      id: null,
      casadoComBrasileiro: false,
      filhosComBrasileiro: false,
      checked: false,
    };
  }
}
