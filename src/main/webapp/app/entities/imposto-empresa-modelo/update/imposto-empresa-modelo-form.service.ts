import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IImpostoEmpresaModelo, NewImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImpostoEmpresaModelo for edit and NewImpostoEmpresaModeloFormGroupInput for create.
 */
type ImpostoEmpresaModeloFormGroupInput = IImpostoEmpresaModelo | PartialWithRequiredKeyOf<NewImpostoEmpresaModelo>;

type ImpostoEmpresaModeloFormDefaults = Pick<NewImpostoEmpresaModelo, 'id'>;

type ImpostoEmpresaModeloFormGroupContent = {
  id: FormControl<IImpostoEmpresaModelo['id'] | NewImpostoEmpresaModelo['id']>;
  nome: FormControl<IImpostoEmpresaModelo['nome']>;
  observacao: FormControl<IImpostoEmpresaModelo['observacao']>;
  empresaModelo: FormControl<IImpostoEmpresaModelo['empresaModelo']>;
  imposto: FormControl<IImpostoEmpresaModelo['imposto']>;
};

export type ImpostoEmpresaModeloFormGroup = FormGroup<ImpostoEmpresaModeloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImpostoEmpresaModeloFormService {
  createImpostoEmpresaModeloFormGroup(
    impostoEmpresaModelo: ImpostoEmpresaModeloFormGroupInput = { id: null },
  ): ImpostoEmpresaModeloFormGroup {
    const impostoEmpresaModeloRawValue = {
      ...this.getFormDefaults(),
      ...impostoEmpresaModelo,
    };
    return new FormGroup<ImpostoEmpresaModeloFormGroupContent>({
      id: new FormControl(
        { value: impostoEmpresaModeloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(impostoEmpresaModeloRawValue.nome),
      observacao: new FormControl(impostoEmpresaModeloRawValue.observacao, {
        validators: [Validators.maxLength(200)],
      }),
      empresaModelo: new FormControl(impostoEmpresaModeloRawValue.empresaModelo, {
        validators: [Validators.required],
      }),
      imposto: new FormControl(impostoEmpresaModeloRawValue.imposto, {
        validators: [Validators.required],
      }),
    });
  }

  getImpostoEmpresaModelo(form: ImpostoEmpresaModeloFormGroup): IImpostoEmpresaModelo | NewImpostoEmpresaModelo {
    return form.getRawValue() as IImpostoEmpresaModelo | NewImpostoEmpresaModelo;
  }

  resetForm(form: ImpostoEmpresaModeloFormGroup, impostoEmpresaModelo: ImpostoEmpresaModeloFormGroupInput): void {
    const impostoEmpresaModeloRawValue = { ...this.getFormDefaults(), ...impostoEmpresaModelo };
    form.reset(
      {
        ...impostoEmpresaModeloRawValue,
        id: { value: impostoEmpresaModeloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ImpostoEmpresaModeloFormDefaults {
    return {
      id: null,
    };
  }
}
