import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPerfilContador, NewPerfilContador } from '../perfil-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerfilContador for edit and NewPerfilContadorFormGroupInput for create.
 */
type PerfilContadorFormGroupInput = IPerfilContador | PartialWithRequiredKeyOf<NewPerfilContador>;

type PerfilContadorFormDefaults = Pick<NewPerfilContador, 'id'>;

type PerfilContadorFormGroupContent = {
  id: FormControl<IPerfilContador['id'] | NewPerfilContador['id']>;
  perfil: FormControl<IPerfilContador['perfil']>;
  descricao: FormControl<IPerfilContador['descricao']>;
  limiteEmpresas: FormControl<IPerfilContador['limiteEmpresas']>;
  limiteDepartamentos: FormControl<IPerfilContador['limiteDepartamentos']>;
  limiteFaturamento: FormControl<IPerfilContador['limiteFaturamento']>;
};

export type PerfilContadorFormGroup = FormGroup<PerfilContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PerfilContadorFormService {
  createPerfilContadorFormGroup(perfilContador: PerfilContadorFormGroupInput = { id: null }): PerfilContadorFormGroup {
    const perfilContadorRawValue = {
      ...this.getFormDefaults(),
      ...perfilContador,
    };
    return new FormGroup<PerfilContadorFormGroupContent>({
      id: new FormControl(
        { value: perfilContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      perfil: new FormControl(perfilContadorRawValue.perfil, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(perfilContadorRawValue.descricao, {
        validators: [Validators.maxLength(200)],
      }),
      limiteEmpresas: new FormControl(perfilContadorRawValue.limiteEmpresas),
      limiteDepartamentos: new FormControl(perfilContadorRawValue.limiteDepartamentos),
      limiteFaturamento: new FormControl(perfilContadorRawValue.limiteFaturamento),
    });
  }

  getPerfilContador(form: PerfilContadorFormGroup): IPerfilContador | NewPerfilContador {
    return form.getRawValue() as IPerfilContador | NewPerfilContador;
  }

  resetForm(form: PerfilContadorFormGroup, perfilContador: PerfilContadorFormGroupInput): void {
    const perfilContadorRawValue = { ...this.getFormDefaults(), ...perfilContador };
    form.reset(
      {
        ...perfilContadorRawValue,
        id: { value: perfilContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PerfilContadorFormDefaults {
    return {
      id: null,
    };
  }
}
