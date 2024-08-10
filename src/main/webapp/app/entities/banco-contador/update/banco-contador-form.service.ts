import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBancoContador, NewBancoContador } from '../banco-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBancoContador for edit and NewBancoContadorFormGroupInput for create.
 */
type BancoContadorFormGroupInput = IBancoContador | PartialWithRequiredKeyOf<NewBancoContador>;

type BancoContadorFormDefaults = Pick<NewBancoContador, 'id' | 'principal'>;

type BancoContadorFormGroupContent = {
  id: FormControl<IBancoContador['id'] | NewBancoContador['id']>;
  agencia: FormControl<IBancoContador['agencia']>;
  conta: FormControl<IBancoContador['conta']>;
  digitoAgencia: FormControl<IBancoContador['digitoAgencia']>;
  digitoConta: FormControl<IBancoContador['digitoConta']>;
  principal: FormControl<IBancoContador['principal']>;
  contador: FormControl<IBancoContador['contador']>;
  banco: FormControl<IBancoContador['banco']>;
};

export type BancoContadorFormGroup = FormGroup<BancoContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BancoContadorFormService {
  createBancoContadorFormGroup(bancoContador: BancoContadorFormGroupInput = { id: null }): BancoContadorFormGroup {
    const bancoContadorRawValue = {
      ...this.getFormDefaults(),
      ...bancoContador,
    };
    return new FormGroup<BancoContadorFormGroupContent>({
      id: new FormControl(
        { value: bancoContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      agencia: new FormControl(bancoContadorRawValue.agencia, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      conta: new FormControl(bancoContadorRawValue.conta, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      digitoAgencia: new FormControl(bancoContadorRawValue.digitoAgencia),
      digitoConta: new FormControl(bancoContadorRawValue.digitoConta),
      principal: new FormControl(bancoContadorRawValue.principal),
      contador: new FormControl(bancoContadorRawValue.contador, {
        validators: [Validators.required],
      }),
      banco: new FormControl(bancoContadorRawValue.banco, {
        validators: [Validators.required],
      }),
    });
  }

  getBancoContador(form: BancoContadorFormGroup): IBancoContador | NewBancoContador {
    return form.getRawValue() as IBancoContador | NewBancoContador;
  }

  resetForm(form: BancoContadorFormGroup, bancoContador: BancoContadorFormGroupInput): void {
    const bancoContadorRawValue = { ...this.getFormDefaults(), ...bancoContador };
    form.reset(
      {
        ...bancoContadorRawValue,
        id: { value: bancoContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BancoContadorFormDefaults {
    return {
      id: null,
      principal: false,
    };
  }
}
