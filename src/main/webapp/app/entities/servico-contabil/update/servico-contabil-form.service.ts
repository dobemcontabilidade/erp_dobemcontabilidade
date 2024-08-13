import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IServicoContabil, NewServicoContabil } from '../servico-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServicoContabil for edit and NewServicoContabilFormGroupInput for create.
 */
type ServicoContabilFormGroupInput = IServicoContabil | PartialWithRequiredKeyOf<NewServicoContabil>;

type ServicoContabilFormDefaults = Pick<NewServicoContabil, 'id' | 'geraMulta'>;

type ServicoContabilFormGroupContent = {
  id: FormControl<IServicoContabil['id'] | NewServicoContabil['id']>;
  nome: FormControl<IServicoContabil['nome']>;
  valor: FormControl<IServicoContabil['valor']>;
  descricao: FormControl<IServicoContabil['descricao']>;
  diasExecucao: FormControl<IServicoContabil['diasExecucao']>;
  geraMulta: FormControl<IServicoContabil['geraMulta']>;
  periodoExecucao: FormControl<IServicoContabil['periodoExecucao']>;
  diaLegal: FormControl<IServicoContabil['diaLegal']>;
  mesLegal: FormControl<IServicoContabil['mesLegal']>;
  valorRefMulta: FormControl<IServicoContabil['valorRefMulta']>;
  areaContabil: FormControl<IServicoContabil['areaContabil']>;
  esfera: FormControl<IServicoContabil['esfera']>;
};

export type ServicoContabilFormGroup = FormGroup<ServicoContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilFormService {
  createServicoContabilFormGroup(servicoContabil: ServicoContabilFormGroupInput = { id: null }): ServicoContabilFormGroup {
    const servicoContabilRawValue = {
      ...this.getFormDefaults(),
      ...servicoContabil,
    };
    return new FormGroup<ServicoContabilFormGroupContent>({
      id: new FormControl(
        { value: servicoContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(servicoContabilRawValue.nome),
      valor: new FormControl(servicoContabilRawValue.valor),
      descricao: new FormControl(servicoContabilRawValue.descricao, {
        validators: [Validators.maxLength(200)],
      }),
      diasExecucao: new FormControl(servicoContabilRawValue.diasExecucao),
      geraMulta: new FormControl(servicoContabilRawValue.geraMulta),
      periodoExecucao: new FormControl(servicoContabilRawValue.periodoExecucao),
      diaLegal: new FormControl(servicoContabilRawValue.diaLegal),
      mesLegal: new FormControl(servicoContabilRawValue.mesLegal),
      valorRefMulta: new FormControl(servicoContabilRawValue.valorRefMulta),
      areaContabil: new FormControl(servicoContabilRawValue.areaContabil, {
        validators: [Validators.required],
      }),
      esfera: new FormControl(servicoContabilRawValue.esfera, {
        validators: [Validators.required],
      }),
    });
  }

  getServicoContabil(form: ServicoContabilFormGroup): IServicoContabil | NewServicoContabil {
    return form.getRawValue() as IServicoContabil | NewServicoContabil;
  }

  resetForm(form: ServicoContabilFormGroup, servicoContabil: ServicoContabilFormGroupInput): void {
    const servicoContabilRawValue = { ...this.getFormDefaults(), ...servicoContabil };
    form.reset(
      {
        ...servicoContabilRawValue,
        id: { value: servicoContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServicoContabilFormDefaults {
    return {
      id: null,
      geraMulta: false,
    };
  }
}
