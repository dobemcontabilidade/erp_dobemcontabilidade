import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IServicoContabilEmpresaModelo, NewServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServicoContabilEmpresaModelo for edit and NewServicoContabilEmpresaModeloFormGroupInput for create.
 */
type ServicoContabilEmpresaModeloFormGroupInput = IServicoContabilEmpresaModelo | PartialWithRequiredKeyOf<NewServicoContabilEmpresaModelo>;

type ServicoContabilEmpresaModeloFormDefaults = Pick<NewServicoContabilEmpresaModelo, 'id' | 'obrigatorio'>;

type ServicoContabilEmpresaModeloFormGroupContent = {
  id: FormControl<IServicoContabilEmpresaModelo['id'] | NewServicoContabilEmpresaModelo['id']>;
  obrigatorio: FormControl<IServicoContabilEmpresaModelo['obrigatorio']>;
  empresaModelo: FormControl<IServicoContabilEmpresaModelo['empresaModelo']>;
  servicoContabil: FormControl<IServicoContabilEmpresaModelo['servicoContabil']>;
};

export type ServicoContabilEmpresaModeloFormGroup = FormGroup<ServicoContabilEmpresaModeloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilEmpresaModeloFormService {
  createServicoContabilEmpresaModeloFormGroup(
    servicoContabilEmpresaModelo: ServicoContabilEmpresaModeloFormGroupInput = { id: null },
  ): ServicoContabilEmpresaModeloFormGroup {
    const servicoContabilEmpresaModeloRawValue = {
      ...this.getFormDefaults(),
      ...servicoContabilEmpresaModelo,
    };
    return new FormGroup<ServicoContabilEmpresaModeloFormGroupContent>({
      id: new FormControl(
        { value: servicoContabilEmpresaModeloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      obrigatorio: new FormControl(servicoContabilEmpresaModeloRawValue.obrigatorio),
      empresaModelo: new FormControl(servicoContabilEmpresaModeloRawValue.empresaModelo, {
        validators: [Validators.required],
      }),
      servicoContabil: new FormControl(servicoContabilEmpresaModeloRawValue.servicoContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getServicoContabilEmpresaModelo(
    form: ServicoContabilEmpresaModeloFormGroup,
  ): IServicoContabilEmpresaModelo | NewServicoContabilEmpresaModelo {
    return form.getRawValue() as IServicoContabilEmpresaModelo | NewServicoContabilEmpresaModelo;
  }

  resetForm(form: ServicoContabilEmpresaModeloFormGroup, servicoContabilEmpresaModelo: ServicoContabilEmpresaModeloFormGroupInput): void {
    const servicoContabilEmpresaModeloRawValue = { ...this.getFormDefaults(), ...servicoContabilEmpresaModelo };
    form.reset(
      {
        ...servicoContabilEmpresaModeloRawValue,
        id: { value: servicoContabilEmpresaModeloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServicoContabilEmpresaModeloFormDefaults {
    return {
      id: null,
      obrigatorio: false,
    };
  }
}
