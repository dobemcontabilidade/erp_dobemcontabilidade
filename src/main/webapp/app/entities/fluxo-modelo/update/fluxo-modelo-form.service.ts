import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFluxoModelo, NewFluxoModelo } from '../fluxo-modelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFluxoModelo for edit and NewFluxoModeloFormGroupInput for create.
 */
type FluxoModeloFormGroupInput = IFluxoModelo | PartialWithRequiredKeyOf<NewFluxoModelo>;

type FluxoModeloFormDefaults = Pick<NewFluxoModelo, 'id'>;

type FluxoModeloFormGroupContent = {
  id: FormControl<IFluxoModelo['id'] | NewFluxoModelo['id']>;
  nome: FormControl<IFluxoModelo['nome']>;
  descricao: FormControl<IFluxoModelo['descricao']>;
  cidade: FormControl<IFluxoModelo['cidade']>;
};

export type FluxoModeloFormGroup = FormGroup<FluxoModeloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FluxoModeloFormService {
  createFluxoModeloFormGroup(fluxoModelo: FluxoModeloFormGroupInput = { id: null }): FluxoModeloFormGroup {
    const fluxoModeloRawValue = {
      ...this.getFormDefaults(),
      ...fluxoModelo,
    };
    return new FormGroup<FluxoModeloFormGroupContent>({
      id: new FormControl(
        { value: fluxoModeloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(fluxoModeloRawValue.nome),
      descricao: new FormControl(fluxoModeloRawValue.descricao),
      cidade: new FormControl(fluxoModeloRawValue.cidade, {
        validators: [Validators.required],
      }),
    });
  }

  getFluxoModelo(form: FluxoModeloFormGroup): IFluxoModelo | NewFluxoModelo {
    return form.getRawValue() as IFluxoModelo | NewFluxoModelo;
  }

  resetForm(form: FluxoModeloFormGroup, fluxoModelo: FluxoModeloFormGroupInput): void {
    const fluxoModeloRawValue = { ...this.getFormDefaults(), ...fluxoModelo };
    form.reset(
      {
        ...fluxoModeloRawValue,
        id: { value: fluxoModeloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FluxoModeloFormDefaults {
    return {
      id: null,
    };
  }
}
