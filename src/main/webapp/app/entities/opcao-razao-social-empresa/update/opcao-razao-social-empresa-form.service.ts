import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOpcaoRazaoSocialEmpresa, NewOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOpcaoRazaoSocialEmpresa for edit and NewOpcaoRazaoSocialEmpresaFormGroupInput for create.
 */
type OpcaoRazaoSocialEmpresaFormGroupInput = IOpcaoRazaoSocialEmpresa | PartialWithRequiredKeyOf<NewOpcaoRazaoSocialEmpresa>;

type OpcaoRazaoSocialEmpresaFormDefaults = Pick<NewOpcaoRazaoSocialEmpresa, 'id' | 'selecionado'>;

type OpcaoRazaoSocialEmpresaFormGroupContent = {
  id: FormControl<IOpcaoRazaoSocialEmpresa['id'] | NewOpcaoRazaoSocialEmpresa['id']>;
  nome: FormControl<IOpcaoRazaoSocialEmpresa['nome']>;
  ordem: FormControl<IOpcaoRazaoSocialEmpresa['ordem']>;
  selecionado: FormControl<IOpcaoRazaoSocialEmpresa['selecionado']>;
  empresa: FormControl<IOpcaoRazaoSocialEmpresa['empresa']>;
};

export type OpcaoRazaoSocialEmpresaFormGroup = FormGroup<OpcaoRazaoSocialEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OpcaoRazaoSocialEmpresaFormService {
  createOpcaoRazaoSocialEmpresaFormGroup(
    opcaoRazaoSocialEmpresa: OpcaoRazaoSocialEmpresaFormGroupInput = { id: null },
  ): OpcaoRazaoSocialEmpresaFormGroup {
    const opcaoRazaoSocialEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...opcaoRazaoSocialEmpresa,
    };
    return new FormGroup<OpcaoRazaoSocialEmpresaFormGroupContent>({
      id: new FormControl(
        { value: opcaoRazaoSocialEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(opcaoRazaoSocialEmpresaRawValue.nome, {
        validators: [Validators.required],
      }),
      ordem: new FormControl(opcaoRazaoSocialEmpresaRawValue.ordem),
      selecionado: new FormControl(opcaoRazaoSocialEmpresaRawValue.selecionado),
      empresa: new FormControl(opcaoRazaoSocialEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getOpcaoRazaoSocialEmpresa(form: OpcaoRazaoSocialEmpresaFormGroup): IOpcaoRazaoSocialEmpresa | NewOpcaoRazaoSocialEmpresa {
    return form.getRawValue() as IOpcaoRazaoSocialEmpresa | NewOpcaoRazaoSocialEmpresa;
  }

  resetForm(form: OpcaoRazaoSocialEmpresaFormGroup, opcaoRazaoSocialEmpresa: OpcaoRazaoSocialEmpresaFormGroupInput): void {
    const opcaoRazaoSocialEmpresaRawValue = { ...this.getFormDefaults(), ...opcaoRazaoSocialEmpresa };
    form.reset(
      {
        ...opcaoRazaoSocialEmpresaRawValue,
        id: { value: opcaoRazaoSocialEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OpcaoRazaoSocialEmpresaFormDefaults {
    return {
      id: null,
      selecionado: false,
    };
  }
}
