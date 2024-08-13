import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnexoRequeridoEmpresa, NewAnexoRequeridoEmpresa } from '../anexo-requerido-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoRequeridoEmpresa for edit and NewAnexoRequeridoEmpresaFormGroupInput for create.
 */
type AnexoRequeridoEmpresaFormGroupInput = IAnexoRequeridoEmpresa | PartialWithRequiredKeyOf<NewAnexoRequeridoEmpresa>;

type AnexoRequeridoEmpresaFormDefaults = Pick<NewAnexoRequeridoEmpresa, 'id' | 'obrigatorio'>;

type AnexoRequeridoEmpresaFormGroupContent = {
  id: FormControl<IAnexoRequeridoEmpresa['id'] | NewAnexoRequeridoEmpresa['id']>;
  obrigatorio: FormControl<IAnexoRequeridoEmpresa['obrigatorio']>;
  urlArquivo: FormControl<IAnexoRequeridoEmpresa['urlArquivo']>;
  anexoRequerido: FormControl<IAnexoRequeridoEmpresa['anexoRequerido']>;
  enquadramento: FormControl<IAnexoRequeridoEmpresa['enquadramento']>;
  tributacao: FormControl<IAnexoRequeridoEmpresa['tributacao']>;
  ramo: FormControl<IAnexoRequeridoEmpresa['ramo']>;
  empresa: FormControl<IAnexoRequeridoEmpresa['empresa']>;
  empresaModelo: FormControl<IAnexoRequeridoEmpresa['empresaModelo']>;
};

export type AnexoRequeridoEmpresaFormGroup = FormGroup<AnexoRequeridoEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoEmpresaFormService {
  createAnexoRequeridoEmpresaFormGroup(
    anexoRequeridoEmpresa: AnexoRequeridoEmpresaFormGroupInput = { id: null },
  ): AnexoRequeridoEmpresaFormGroup {
    const anexoRequeridoEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...anexoRequeridoEmpresa,
    };
    return new FormGroup<AnexoRequeridoEmpresaFormGroupContent>({
      id: new FormControl(
        { value: anexoRequeridoEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      obrigatorio: new FormControl(anexoRequeridoEmpresaRawValue.obrigatorio),
      urlArquivo: new FormControl(anexoRequeridoEmpresaRawValue.urlArquivo),
      anexoRequerido: new FormControl(anexoRequeridoEmpresaRawValue.anexoRequerido, {
        validators: [Validators.required],
      }),
      enquadramento: new FormControl(anexoRequeridoEmpresaRawValue.enquadramento, {
        validators: [Validators.required],
      }),
      tributacao: new FormControl(anexoRequeridoEmpresaRawValue.tributacao, {
        validators: [Validators.required],
      }),
      ramo: new FormControl(anexoRequeridoEmpresaRawValue.ramo, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(anexoRequeridoEmpresaRawValue.empresa),
      empresaModelo: new FormControl(anexoRequeridoEmpresaRawValue.empresaModelo, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoRequeridoEmpresa(form: AnexoRequeridoEmpresaFormGroup): IAnexoRequeridoEmpresa | NewAnexoRequeridoEmpresa {
    return form.getRawValue() as IAnexoRequeridoEmpresa | NewAnexoRequeridoEmpresa;
  }

  resetForm(form: AnexoRequeridoEmpresaFormGroup, anexoRequeridoEmpresa: AnexoRequeridoEmpresaFormGroupInput): void {
    const anexoRequeridoEmpresaRawValue = { ...this.getFormDefaults(), ...anexoRequeridoEmpresa };
    form.reset(
      {
        ...anexoRequeridoEmpresaRawValue,
        id: { value: anexoRequeridoEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoRequeridoEmpresaFormDefaults {
    return {
      id: null,
      obrigatorio: false,
    };
  }
}
