import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrazoAssinatura, NewPrazoAssinatura } from '../prazo-assinatura.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrazoAssinatura for edit and NewPrazoAssinaturaFormGroupInput for create.
 */
type PrazoAssinaturaFormGroupInput = IPrazoAssinatura | PartialWithRequiredKeyOf<NewPrazoAssinatura>;

type PrazoAssinaturaFormDefaults = Pick<NewPrazoAssinatura, 'id'>;

type PrazoAssinaturaFormGroupContent = {
  id: FormControl<IPrazoAssinatura['id'] | NewPrazoAssinatura['id']>;
  prazo: FormControl<IPrazoAssinatura['prazo']>;
  meses: FormControl<IPrazoAssinatura['meses']>;
};

export type PrazoAssinaturaFormGroup = FormGroup<PrazoAssinaturaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrazoAssinaturaFormService {
  createPrazoAssinaturaFormGroup(prazoAssinatura: PrazoAssinaturaFormGroupInput = { id: null }): PrazoAssinaturaFormGroup {
    const prazoAssinaturaRawValue = {
      ...this.getFormDefaults(),
      ...prazoAssinatura,
    };
    return new FormGroup<PrazoAssinaturaFormGroupContent>({
      id: new FormControl(
        { value: prazoAssinaturaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      prazo: new FormControl(prazoAssinaturaRawValue.prazo),
      meses: new FormControl(prazoAssinaturaRawValue.meses),
    });
  }

  getPrazoAssinatura(form: PrazoAssinaturaFormGroup): IPrazoAssinatura | NewPrazoAssinatura {
    return form.getRawValue() as IPrazoAssinatura | NewPrazoAssinatura;
  }

  resetForm(form: PrazoAssinaturaFormGroup, prazoAssinatura: PrazoAssinaturaFormGroupInput): void {
    const prazoAssinaturaRawValue = { ...this.getFormDefaults(), ...prazoAssinatura };
    form.reset(
      {
        ...prazoAssinaturaRawValue,
        id: { value: prazoAssinaturaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PrazoAssinaturaFormDefaults {
    return {
      id: null,
    };
  }
}
