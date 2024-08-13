import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEtapaFluxoModelo, NewEtapaFluxoModelo } from '../etapa-fluxo-modelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEtapaFluxoModelo for edit and NewEtapaFluxoModeloFormGroupInput for create.
 */
type EtapaFluxoModeloFormGroupInput = IEtapaFluxoModelo | PartialWithRequiredKeyOf<NewEtapaFluxoModelo>;

type EtapaFluxoModeloFormDefaults = Pick<NewEtapaFluxoModelo, 'id'>;

type EtapaFluxoModeloFormGroupContent = {
  id: FormControl<IEtapaFluxoModelo['id'] | NewEtapaFluxoModelo['id']>;
  nome: FormControl<IEtapaFluxoModelo['nome']>;
  descricao: FormControl<IEtapaFluxoModelo['descricao']>;
  ordem: FormControl<IEtapaFluxoModelo['ordem']>;
  fluxoModelo: FormControl<IEtapaFluxoModelo['fluxoModelo']>;
};

export type EtapaFluxoModeloFormGroup = FormGroup<EtapaFluxoModeloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EtapaFluxoModeloFormService {
  createEtapaFluxoModeloFormGroup(etapaFluxoModelo: EtapaFluxoModeloFormGroupInput = { id: null }): EtapaFluxoModeloFormGroup {
    const etapaFluxoModeloRawValue = {
      ...this.getFormDefaults(),
      ...etapaFluxoModelo,
    };
    return new FormGroup<EtapaFluxoModeloFormGroupContent>({
      id: new FormControl(
        { value: etapaFluxoModeloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(etapaFluxoModeloRawValue.nome),
      descricao: new FormControl(etapaFluxoModeloRawValue.descricao),
      ordem: new FormControl(etapaFluxoModeloRawValue.ordem),
      fluxoModelo: new FormControl(etapaFluxoModeloRawValue.fluxoModelo, {
        validators: [Validators.required],
      }),
    });
  }

  getEtapaFluxoModelo(form: EtapaFluxoModeloFormGroup): IEtapaFluxoModelo | NewEtapaFluxoModelo {
    return form.getRawValue() as IEtapaFluxoModelo | NewEtapaFluxoModelo;
  }

  resetForm(form: EtapaFluxoModeloFormGroup, etapaFluxoModelo: EtapaFluxoModeloFormGroupInput): void {
    const etapaFluxoModeloRawValue = { ...this.getFormDefaults(), ...etapaFluxoModelo };
    form.reset(
      {
        ...etapaFluxoModeloRawValue,
        id: { value: etapaFluxoModeloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EtapaFluxoModeloFormDefaults {
    return {
      id: null,
    };
  }
}
