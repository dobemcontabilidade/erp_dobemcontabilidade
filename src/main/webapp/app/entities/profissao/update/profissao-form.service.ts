import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProfissao, NewProfissao } from '../profissao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfissao for edit and NewProfissaoFormGroupInput for create.
 */
type ProfissaoFormGroupInput = IProfissao | PartialWithRequiredKeyOf<NewProfissao>;

type ProfissaoFormDefaults = Pick<NewProfissao, 'id'>;

type ProfissaoFormGroupContent = {
  id: FormControl<IProfissao['id'] | NewProfissao['id']>;
  nome: FormControl<IProfissao['nome']>;
  cbo: FormControl<IProfissao['cbo']>;
  categoria: FormControl<IProfissao['categoria']>;
  descricao: FormControl<IProfissao['descricao']>;
};

export type ProfissaoFormGroup = FormGroup<ProfissaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfissaoFormService {
  createProfissaoFormGroup(profissao: ProfissaoFormGroupInput = { id: null }): ProfissaoFormGroup {
    const profissaoRawValue = {
      ...this.getFormDefaults(),
      ...profissao,
    };
    return new FormGroup<ProfissaoFormGroupContent>({
      id: new FormControl(
        { value: profissaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(profissaoRawValue.nome),
      cbo: new FormControl(profissaoRawValue.cbo),
      categoria: new FormControl(profissaoRawValue.categoria),
      descricao: new FormControl(profissaoRawValue.descricao),
    });
  }

  getProfissao(form: ProfissaoFormGroup): IProfissao | NewProfissao {
    return form.getRawValue() as IProfissao | NewProfissao;
  }

  resetForm(form: ProfissaoFormGroup, profissao: ProfissaoFormGroupInput): void {
    const profissaoRawValue = { ...this.getFormDefaults(), ...profissao };
    form.reset(
      {
        ...profissaoRawValue,
        id: { value: profissaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProfissaoFormDefaults {
    return {
      id: null,
    };
  }
}
