import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITermoDeAdesao, NewTermoDeAdesao } from '../termo-de-adesao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITermoDeAdesao for edit and NewTermoDeAdesaoFormGroupInput for create.
 */
type TermoDeAdesaoFormGroupInput = ITermoDeAdesao | PartialWithRequiredKeyOf<NewTermoDeAdesao>;

type TermoDeAdesaoFormDefaults = Pick<NewTermoDeAdesao, 'id'>;

type TermoDeAdesaoFormGroupContent = {
  id: FormControl<ITermoDeAdesao['id'] | NewTermoDeAdesao['id']>;
  titulo: FormControl<ITermoDeAdesao['titulo']>;
  descricao: FormControl<ITermoDeAdesao['descricao']>;
  urlDoc: FormControl<ITermoDeAdesao['urlDoc']>;
};

export type TermoDeAdesaoFormGroup = FormGroup<TermoDeAdesaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TermoDeAdesaoFormService {
  createTermoDeAdesaoFormGroup(termoDeAdesao: TermoDeAdesaoFormGroupInput = { id: null }): TermoDeAdesaoFormGroup {
    const termoDeAdesaoRawValue = {
      ...this.getFormDefaults(),
      ...termoDeAdesao,
    };
    return new FormGroup<TermoDeAdesaoFormGroupContent>({
      id: new FormControl(
        { value: termoDeAdesaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titulo: new FormControl(termoDeAdesaoRawValue.titulo),
      descricao: new FormControl(termoDeAdesaoRawValue.descricao),
      urlDoc: new FormControl(termoDeAdesaoRawValue.urlDoc),
    });
  }

  getTermoDeAdesao(form: TermoDeAdesaoFormGroup): ITermoDeAdesao | NewTermoDeAdesao {
    return form.getRawValue() as ITermoDeAdesao | NewTermoDeAdesao;
  }

  resetForm(form: TermoDeAdesaoFormGroup, termoDeAdesao: TermoDeAdesaoFormGroupInput): void {
    const termoDeAdesaoRawValue = { ...this.getFormDefaults(), ...termoDeAdesao };
    form.reset(
      {
        ...termoDeAdesaoRawValue,
        id: { value: termoDeAdesaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TermoDeAdesaoFormDefaults {
    return {
      id: null,
    };
  }
}
