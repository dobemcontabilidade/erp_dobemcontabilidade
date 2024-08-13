import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISecaoCnae, NewSecaoCnae } from '../secao-cnae.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISecaoCnae for edit and NewSecaoCnaeFormGroupInput for create.
 */
type SecaoCnaeFormGroupInput = ISecaoCnae | PartialWithRequiredKeyOf<NewSecaoCnae>;

type SecaoCnaeFormDefaults = Pick<NewSecaoCnae, 'id'>;

type SecaoCnaeFormGroupContent = {
  id: FormControl<ISecaoCnae['id'] | NewSecaoCnae['id']>;
  codigo: FormControl<ISecaoCnae['codigo']>;
  descricao: FormControl<ISecaoCnae['descricao']>;
};

export type SecaoCnaeFormGroup = FormGroup<SecaoCnaeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SecaoCnaeFormService {
  createSecaoCnaeFormGroup(secaoCnae: SecaoCnaeFormGroupInput = { id: null }): SecaoCnaeFormGroup {
    const secaoCnaeRawValue = {
      ...this.getFormDefaults(),
      ...secaoCnae,
    };
    return new FormGroup<SecaoCnaeFormGroupContent>({
      id: new FormControl(
        { value: secaoCnaeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigo: new FormControl(secaoCnaeRawValue.codigo, {
        validators: [Validators.maxLength(15)],
      }),
      descricao: new FormControl(secaoCnaeRawValue.descricao, {
        validators: [Validators.required],
      }),
    });
  }

  getSecaoCnae(form: SecaoCnaeFormGroup): ISecaoCnae | NewSecaoCnae {
    return form.getRawValue() as ISecaoCnae | NewSecaoCnae;
  }

  resetForm(form: SecaoCnaeFormGroup, secaoCnae: SecaoCnaeFormGroupInput): void {
    const secaoCnaeRawValue = { ...this.getFormDefaults(), ...secaoCnae };
    form.reset(
      {
        ...secaoCnaeRawValue,
        id: { value: secaoCnaeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SecaoCnaeFormDefaults {
    return {
      id: null,
    };
  }
}
