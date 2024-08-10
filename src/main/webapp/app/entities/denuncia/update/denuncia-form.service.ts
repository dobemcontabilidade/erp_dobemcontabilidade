import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDenuncia, NewDenuncia } from '../denuncia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDenuncia for edit and NewDenunciaFormGroupInput for create.
 */
type DenunciaFormGroupInput = IDenuncia | PartialWithRequiredKeyOf<NewDenuncia>;

type DenunciaFormDefaults = Pick<NewDenuncia, 'id'>;

type DenunciaFormGroupContent = {
  id: FormControl<IDenuncia['id'] | NewDenuncia['id']>;
  titulo: FormControl<IDenuncia['titulo']>;
  mensagem: FormControl<IDenuncia['mensagem']>;
  descricao: FormControl<IDenuncia['descricao']>;
};

export type DenunciaFormGroup = FormGroup<DenunciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DenunciaFormService {
  createDenunciaFormGroup(denuncia: DenunciaFormGroupInput = { id: null }): DenunciaFormGroup {
    const denunciaRawValue = {
      ...this.getFormDefaults(),
      ...denuncia,
    };
    return new FormGroup<DenunciaFormGroupContent>({
      id: new FormControl(
        { value: denunciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titulo: new FormControl(denunciaRawValue.titulo, {
        validators: [Validators.required],
      }),
      mensagem: new FormControl(denunciaRawValue.mensagem, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(denunciaRawValue.descricao),
    });
  }

  getDenuncia(form: DenunciaFormGroup): IDenuncia | NewDenuncia {
    return form.getRawValue() as IDenuncia | NewDenuncia;
  }

  resetForm(form: DenunciaFormGroup, denuncia: DenunciaFormGroupInput): void {
    const denunciaRawValue = { ...this.getFormDefaults(), ...denuncia };
    form.reset(
      {
        ...denunciaRawValue,
        id: { value: denunciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DenunciaFormDefaults {
    return {
      id: null,
    };
  }
}
