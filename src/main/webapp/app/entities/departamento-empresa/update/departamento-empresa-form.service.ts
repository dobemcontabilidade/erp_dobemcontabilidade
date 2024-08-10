import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDepartamentoEmpresa, NewDepartamentoEmpresa } from '../departamento-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDepartamentoEmpresa for edit and NewDepartamentoEmpresaFormGroupInput for create.
 */
type DepartamentoEmpresaFormGroupInput = IDepartamentoEmpresa | PartialWithRequiredKeyOf<NewDepartamentoEmpresa>;

type DepartamentoEmpresaFormDefaults = Pick<NewDepartamentoEmpresa, 'id'>;

type DepartamentoEmpresaFormGroupContent = {
  id: FormControl<IDepartamentoEmpresa['id'] | NewDepartamentoEmpresa['id']>;
  pontuacao: FormControl<IDepartamentoEmpresa['pontuacao']>;
  depoimento: FormControl<IDepartamentoEmpresa['depoimento']>;
  reclamacao: FormControl<IDepartamentoEmpresa['reclamacao']>;
  departamento: FormControl<IDepartamentoEmpresa['departamento']>;
  empresa: FormControl<IDepartamentoEmpresa['empresa']>;
  contador: FormControl<IDepartamentoEmpresa['contador']>;
};

export type DepartamentoEmpresaFormGroup = FormGroup<DepartamentoEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DepartamentoEmpresaFormService {
  createDepartamentoEmpresaFormGroup(departamentoEmpresa: DepartamentoEmpresaFormGroupInput = { id: null }): DepartamentoEmpresaFormGroup {
    const departamentoEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...departamentoEmpresa,
    };
    return new FormGroup<DepartamentoEmpresaFormGroupContent>({
      id: new FormControl(
        { value: departamentoEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      pontuacao: new FormControl(departamentoEmpresaRawValue.pontuacao),
      depoimento: new FormControl(departamentoEmpresaRawValue.depoimento),
      reclamacao: new FormControl(departamentoEmpresaRawValue.reclamacao),
      departamento: new FormControl(departamentoEmpresaRawValue.departamento, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(departamentoEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
      contador: new FormControl(departamentoEmpresaRawValue.contador, {
        validators: [Validators.required],
      }),
    });
  }

  getDepartamentoEmpresa(form: DepartamentoEmpresaFormGroup): IDepartamentoEmpresa | NewDepartamentoEmpresa {
    return form.getRawValue() as IDepartamentoEmpresa | NewDepartamentoEmpresa;
  }

  resetForm(form: DepartamentoEmpresaFormGroup, departamentoEmpresa: DepartamentoEmpresaFormGroupInput): void {
    const departamentoEmpresaRawValue = { ...this.getFormDefaults(), ...departamentoEmpresa };
    form.reset(
      {
        ...departamentoEmpresaRawValue,
        id: { value: departamentoEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DepartamentoEmpresaFormDefaults {
    return {
      id: null,
    };
  }
}
