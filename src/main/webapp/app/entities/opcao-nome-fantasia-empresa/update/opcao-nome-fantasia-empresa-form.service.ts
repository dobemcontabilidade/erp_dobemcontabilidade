import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOpcaoNomeFantasiaEmpresa, NewOpcaoNomeFantasiaEmpresa } from '../opcao-nome-fantasia-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOpcaoNomeFantasiaEmpresa for edit and NewOpcaoNomeFantasiaEmpresaFormGroupInput for create.
 */
type OpcaoNomeFantasiaEmpresaFormGroupInput = IOpcaoNomeFantasiaEmpresa | PartialWithRequiredKeyOf<NewOpcaoNomeFantasiaEmpresa>;

type OpcaoNomeFantasiaEmpresaFormDefaults = Pick<NewOpcaoNomeFantasiaEmpresa, 'id' | 'selecionado'>;

type OpcaoNomeFantasiaEmpresaFormGroupContent = {
  id: FormControl<IOpcaoNomeFantasiaEmpresa['id'] | NewOpcaoNomeFantasiaEmpresa['id']>;
  nome: FormControl<IOpcaoNomeFantasiaEmpresa['nome']>;
  ordem: FormControl<IOpcaoNomeFantasiaEmpresa['ordem']>;
  selecionado: FormControl<IOpcaoNomeFantasiaEmpresa['selecionado']>;
  empresa: FormControl<IOpcaoNomeFantasiaEmpresa['empresa']>;
};

export type OpcaoNomeFantasiaEmpresaFormGroup = FormGroup<OpcaoNomeFantasiaEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OpcaoNomeFantasiaEmpresaFormService {
  createOpcaoNomeFantasiaEmpresaFormGroup(
    opcaoNomeFantasiaEmpresa: OpcaoNomeFantasiaEmpresaFormGroupInput = { id: null },
  ): OpcaoNomeFantasiaEmpresaFormGroup {
    const opcaoNomeFantasiaEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...opcaoNomeFantasiaEmpresa,
    };
    return new FormGroup<OpcaoNomeFantasiaEmpresaFormGroupContent>({
      id: new FormControl(
        { value: opcaoNomeFantasiaEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(opcaoNomeFantasiaEmpresaRawValue.nome, {
        validators: [Validators.required],
      }),
      ordem: new FormControl(opcaoNomeFantasiaEmpresaRawValue.ordem),
      selecionado: new FormControl(opcaoNomeFantasiaEmpresaRawValue.selecionado),
      empresa: new FormControl(opcaoNomeFantasiaEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getOpcaoNomeFantasiaEmpresa(form: OpcaoNomeFantasiaEmpresaFormGroup): IOpcaoNomeFantasiaEmpresa | NewOpcaoNomeFantasiaEmpresa {
    return form.getRawValue() as IOpcaoNomeFantasiaEmpresa | NewOpcaoNomeFantasiaEmpresa;
  }

  resetForm(form: OpcaoNomeFantasiaEmpresaFormGroup, opcaoNomeFantasiaEmpresa: OpcaoNomeFantasiaEmpresaFormGroupInput): void {
    const opcaoNomeFantasiaEmpresaRawValue = { ...this.getFormDefaults(), ...opcaoNomeFantasiaEmpresa };
    form.reset(
      {
        ...opcaoNomeFantasiaEmpresaRawValue,
        id: { value: opcaoNomeFantasiaEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OpcaoNomeFantasiaEmpresaFormDefaults {
    return {
      id: null,
      selecionado: false,
    };
  }
}
