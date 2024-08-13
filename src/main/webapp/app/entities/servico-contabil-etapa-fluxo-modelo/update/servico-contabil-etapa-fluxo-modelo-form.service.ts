import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IServicoContabilEtapaFluxoModelo, NewServicoContabilEtapaFluxoModelo } from '../servico-contabil-etapa-fluxo-modelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServicoContabilEtapaFluxoModelo for edit and NewServicoContabilEtapaFluxoModeloFormGroupInput for create.
 */
type ServicoContabilEtapaFluxoModeloFormGroupInput =
  | IServicoContabilEtapaFluxoModelo
  | PartialWithRequiredKeyOf<NewServicoContabilEtapaFluxoModelo>;

type ServicoContabilEtapaFluxoModeloFormDefaults = Pick<NewServicoContabilEtapaFluxoModelo, 'id'>;

type ServicoContabilEtapaFluxoModeloFormGroupContent = {
  id: FormControl<IServicoContabilEtapaFluxoModelo['id'] | NewServicoContabilEtapaFluxoModelo['id']>;
  ordem: FormControl<IServicoContabilEtapaFluxoModelo['ordem']>;
  prazo: FormControl<IServicoContabilEtapaFluxoModelo['prazo']>;
  etapaFluxoModelo: FormControl<IServicoContabilEtapaFluxoModelo['etapaFluxoModelo']>;
  servicoContabil: FormControl<IServicoContabilEtapaFluxoModelo['servicoContabil']>;
};

export type ServicoContabilEtapaFluxoModeloFormGroup = FormGroup<ServicoContabilEtapaFluxoModeloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilEtapaFluxoModeloFormService {
  createServicoContabilEtapaFluxoModeloFormGroup(
    servicoContabilEtapaFluxoModelo: ServicoContabilEtapaFluxoModeloFormGroupInput = { id: null },
  ): ServicoContabilEtapaFluxoModeloFormGroup {
    const servicoContabilEtapaFluxoModeloRawValue = {
      ...this.getFormDefaults(),
      ...servicoContabilEtapaFluxoModelo,
    };
    return new FormGroup<ServicoContabilEtapaFluxoModeloFormGroupContent>({
      id: new FormControl(
        { value: servicoContabilEtapaFluxoModeloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ordem: new FormControl(servicoContabilEtapaFluxoModeloRawValue.ordem),
      prazo: new FormControl(servicoContabilEtapaFluxoModeloRawValue.prazo),
      etapaFluxoModelo: new FormControl(servicoContabilEtapaFluxoModeloRawValue.etapaFluxoModelo, {
        validators: [Validators.required],
      }),
      servicoContabil: new FormControl(servicoContabilEtapaFluxoModeloRawValue.servicoContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getServicoContabilEtapaFluxoModelo(
    form: ServicoContabilEtapaFluxoModeloFormGroup,
  ): IServicoContabilEtapaFluxoModelo | NewServicoContabilEtapaFluxoModelo {
    return form.getRawValue() as IServicoContabilEtapaFluxoModelo | NewServicoContabilEtapaFluxoModelo;
  }

  resetForm(
    form: ServicoContabilEtapaFluxoModeloFormGroup,
    servicoContabilEtapaFluxoModelo: ServicoContabilEtapaFluxoModeloFormGroupInput,
  ): void {
    const servicoContabilEtapaFluxoModeloRawValue = { ...this.getFormDefaults(), ...servicoContabilEtapaFluxoModelo };
    form.reset(
      {
        ...servicoContabilEtapaFluxoModeloRawValue,
        id: { value: servicoContabilEtapaFluxoModeloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServicoContabilEtapaFluxoModeloFormDefaults {
    return {
      id: null,
    };
  }
}
