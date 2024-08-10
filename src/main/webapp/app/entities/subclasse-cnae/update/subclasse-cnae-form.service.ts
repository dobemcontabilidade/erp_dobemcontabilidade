import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISubclasseCnae, NewSubclasseCnae } from '../subclasse-cnae.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISubclasseCnae for edit and NewSubclasseCnaeFormGroupInput for create.
 */
type SubclasseCnaeFormGroupInput = ISubclasseCnae | PartialWithRequiredKeyOf<NewSubclasseCnae>;

type SubclasseCnaeFormDefaults = Pick<
  NewSubclasseCnae,
  'id' | 'atendidoFreemium' | 'atendido' | 'optanteSimples' | 'aceitaMEI' | 'segmentoCnaes'
>;

type SubclasseCnaeFormGroupContent = {
  id: FormControl<ISubclasseCnae['id'] | NewSubclasseCnae['id']>;
  codigo: FormControl<ISubclasseCnae['codigo']>;
  descricao: FormControl<ISubclasseCnae['descricao']>;
  anexo: FormControl<ISubclasseCnae['anexo']>;
  atendidoFreemium: FormControl<ISubclasseCnae['atendidoFreemium']>;
  atendido: FormControl<ISubclasseCnae['atendido']>;
  optanteSimples: FormControl<ISubclasseCnae['optanteSimples']>;
  aceitaMEI: FormControl<ISubclasseCnae['aceitaMEI']>;
  categoria: FormControl<ISubclasseCnae['categoria']>;
  classe: FormControl<ISubclasseCnae['classe']>;
  segmentoCnaes: FormControl<ISubclasseCnae['segmentoCnaes']>;
};

export type SubclasseCnaeFormGroup = FormGroup<SubclasseCnaeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SubclasseCnaeFormService {
  createSubclasseCnaeFormGroup(subclasseCnae: SubclasseCnaeFormGroupInput = { id: null }): SubclasseCnaeFormGroup {
    const subclasseCnaeRawValue = {
      ...this.getFormDefaults(),
      ...subclasseCnae,
    };
    return new FormGroup<SubclasseCnaeFormGroupContent>({
      id: new FormControl(
        { value: subclasseCnaeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigo: new FormControl(subclasseCnaeRawValue.codigo, {
        validators: [Validators.required, Validators.maxLength(15)],
      }),
      descricao: new FormControl(subclasseCnaeRawValue.descricao, {
        validators: [Validators.required],
      }),
      anexo: new FormControl(subclasseCnaeRawValue.anexo),
      atendidoFreemium: new FormControl(subclasseCnaeRawValue.atendidoFreemium),
      atendido: new FormControl(subclasseCnaeRawValue.atendido),
      optanteSimples: new FormControl(subclasseCnaeRawValue.optanteSimples),
      aceitaMEI: new FormControl(subclasseCnaeRawValue.aceitaMEI),
      categoria: new FormControl(subclasseCnaeRawValue.categoria),
      classe: new FormControl(subclasseCnaeRawValue.classe, {
        validators: [Validators.required],
      }),
      segmentoCnaes: new FormControl(subclasseCnaeRawValue.segmentoCnaes ?? []),
    });
  }

  getSubclasseCnae(form: SubclasseCnaeFormGroup): ISubclasseCnae | NewSubclasseCnae {
    return form.getRawValue() as ISubclasseCnae | NewSubclasseCnae;
  }

  resetForm(form: SubclasseCnaeFormGroup, subclasseCnae: SubclasseCnaeFormGroupInput): void {
    const subclasseCnaeRawValue = { ...this.getFormDefaults(), ...subclasseCnae };
    form.reset(
      {
        ...subclasseCnaeRawValue,
        id: { value: subclasseCnaeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SubclasseCnaeFormDefaults {
    return {
      id: null,
      atendidoFreemium: false,
      atendido: false,
      optanteSimples: false,
      aceitaMEI: false,
      segmentoCnaes: [],
    };
  }
}
