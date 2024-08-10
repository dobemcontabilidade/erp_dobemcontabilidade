import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICnae, NewCnae } from '../cnae.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICnae for edit and NewCnaeFormGroupInput for create.
 */
type CnaeFormGroupInput = ICnae | PartialWithRequiredKeyOf<NewCnae>;

type CnaeFormDefaults = Pick<NewCnae, 'id' | 'atendidoFreemium' | 'atendido' | 'optanteSimples'>;

type CnaeFormGroupContent = {
  id: FormControl<ICnae['id'] | NewCnae['id']>;
  codigo: FormControl<ICnae['codigo']>;
  descricao: FormControl<ICnae['descricao']>;
  anexo: FormControl<ICnae['anexo']>;
  atendidoFreemium: FormControl<ICnae['atendidoFreemium']>;
  atendido: FormControl<ICnae['atendido']>;
  optanteSimples: FormControl<ICnae['optanteSimples']>;
  categoria: FormControl<ICnae['categoria']>;
};

export type CnaeFormGroup = FormGroup<CnaeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CnaeFormService {
  createCnaeFormGroup(cnae: CnaeFormGroupInput = { id: null }): CnaeFormGroup {
    const cnaeRawValue = {
      ...this.getFormDefaults(),
      ...cnae,
    };
    return new FormGroup<CnaeFormGroupContent>({
      id: new FormControl(
        { value: cnaeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigo: new FormControl(cnaeRawValue.codigo, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(cnaeRawValue.descricao, {
        validators: [Validators.required],
      }),
      anexo: new FormControl(cnaeRawValue.anexo),
      atendidoFreemium: new FormControl(cnaeRawValue.atendidoFreemium),
      atendido: new FormControl(cnaeRawValue.atendido),
      optanteSimples: new FormControl(cnaeRawValue.optanteSimples),
      categoria: new FormControl(cnaeRawValue.categoria),
    });
  }

  getCnae(form: CnaeFormGroup): ICnae | NewCnae {
    return form.getRawValue() as ICnae | NewCnae;
  }

  resetForm(form: CnaeFormGroup, cnae: CnaeFormGroupInput): void {
    const cnaeRawValue = { ...this.getFormDefaults(), ...cnae };
    form.reset(
      {
        ...cnaeRawValue,
        id: { value: cnaeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CnaeFormDefaults {
    return {
      id: null,
      atendidoFreemium: false,
      atendido: false,
      optanteSimples: false,
    };
  }
}
