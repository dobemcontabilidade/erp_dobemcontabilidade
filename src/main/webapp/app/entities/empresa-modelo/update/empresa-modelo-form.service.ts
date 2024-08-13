import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEmpresaModelo, NewEmpresaModelo } from '../empresa-modelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmpresaModelo for edit and NewEmpresaModeloFormGroupInput for create.
 */
type EmpresaModeloFormGroupInput = IEmpresaModelo | PartialWithRequiredKeyOf<NewEmpresaModelo>;

type EmpresaModeloFormDefaults = Pick<NewEmpresaModelo, 'id' | 'segmentoCnaes'>;

type EmpresaModeloFormGroupContent = {
  id: FormControl<IEmpresaModelo['id'] | NewEmpresaModelo['id']>;
  nome: FormControl<IEmpresaModelo['nome']>;
  observacao: FormControl<IEmpresaModelo['observacao']>;
  segmentoCnaes: FormControl<IEmpresaModelo['segmentoCnaes']>;
  ramo: FormControl<IEmpresaModelo['ramo']>;
  enquadramento: FormControl<IEmpresaModelo['enquadramento']>;
  tributacao: FormControl<IEmpresaModelo['tributacao']>;
  cidade: FormControl<IEmpresaModelo['cidade']>;
};

export type EmpresaModeloFormGroup = FormGroup<EmpresaModeloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpresaModeloFormService {
  createEmpresaModeloFormGroup(empresaModelo: EmpresaModeloFormGroupInput = { id: null }): EmpresaModeloFormGroup {
    const empresaModeloRawValue = {
      ...this.getFormDefaults(),
      ...empresaModelo,
    };
    return new FormGroup<EmpresaModeloFormGroupContent>({
      id: new FormControl(
        { value: empresaModeloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(empresaModeloRawValue.nome),
      observacao: new FormControl(empresaModeloRawValue.observacao),
      segmentoCnaes: new FormControl(empresaModeloRawValue.segmentoCnaes ?? []),
      ramo: new FormControl(empresaModeloRawValue.ramo, {
        validators: [Validators.required],
      }),
      enquadramento: new FormControl(empresaModeloRawValue.enquadramento, {
        validators: [Validators.required],
      }),
      tributacao: new FormControl(empresaModeloRawValue.tributacao, {
        validators: [Validators.required],
      }),
      cidade: new FormControl(empresaModeloRawValue.cidade, {
        validators: [Validators.required],
      }),
    });
  }

  getEmpresaModelo(form: EmpresaModeloFormGroup): IEmpresaModelo | NewEmpresaModelo {
    return form.getRawValue() as IEmpresaModelo | NewEmpresaModelo;
  }

  resetForm(form: EmpresaModeloFormGroup, empresaModelo: EmpresaModeloFormGroupInput): void {
    const empresaModeloRawValue = { ...this.getFormDefaults(), ...empresaModelo };
    form.reset(
      {
        ...empresaModeloRawValue,
        id: { value: empresaModeloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmpresaModeloFormDefaults {
    return {
      id: null,
      segmentoCnaes: [],
    };
  }
}
