import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISegmentoCnae, NewSegmentoCnae } from '../segmento-cnae.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISegmentoCnae for edit and NewSegmentoCnaeFormGroupInput for create.
 */
type SegmentoCnaeFormGroupInput = ISegmentoCnae | PartialWithRequiredKeyOf<NewSegmentoCnae>;

type SegmentoCnaeFormDefaults = Pick<NewSegmentoCnae, 'id' | 'subclasseCnaes' | 'empresas' | 'empresaModelos'>;

type SegmentoCnaeFormGroupContent = {
  id: FormControl<ISegmentoCnae['id'] | NewSegmentoCnae['id']>;
  nome: FormControl<ISegmentoCnae['nome']>;
  descricao: FormControl<ISegmentoCnae['descricao']>;
  icon: FormControl<ISegmentoCnae['icon']>;
  imagem: FormControl<ISegmentoCnae['imagem']>;
  tags: FormControl<ISegmentoCnae['tags']>;
  tipo: FormControl<ISegmentoCnae['tipo']>;
  subclasseCnaes: FormControl<ISegmentoCnae['subclasseCnaes']>;
  ramo: FormControl<ISegmentoCnae['ramo']>;
  empresas: FormControl<ISegmentoCnae['empresas']>;
  empresaModelos: FormControl<ISegmentoCnae['empresaModelos']>;
};

export type SegmentoCnaeFormGroup = FormGroup<SegmentoCnaeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SegmentoCnaeFormService {
  createSegmentoCnaeFormGroup(segmentoCnae: SegmentoCnaeFormGroupInput = { id: null }): SegmentoCnaeFormGroup {
    const segmentoCnaeRawValue = {
      ...this.getFormDefaults(),
      ...segmentoCnae,
    };
    return new FormGroup<SegmentoCnaeFormGroupContent>({
      id: new FormControl(
        { value: segmentoCnaeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(segmentoCnaeRawValue.nome, {
        validators: [Validators.maxLength(200)],
      }),
      descricao: new FormControl(segmentoCnaeRawValue.descricao, {
        validators: [Validators.required],
      }),
      icon: new FormControl(segmentoCnaeRawValue.icon),
      imagem: new FormControl(segmentoCnaeRawValue.imagem),
      tags: new FormControl(segmentoCnaeRawValue.tags),
      tipo: new FormControl(segmentoCnaeRawValue.tipo, {
        validators: [Validators.required],
      }),
      subclasseCnaes: new FormControl(segmentoCnaeRawValue.subclasseCnaes ?? []),
      ramo: new FormControl(segmentoCnaeRawValue.ramo, {
        validators: [Validators.required],
      }),
      empresas: new FormControl(segmentoCnaeRawValue.empresas ?? []),
      empresaModelos: new FormControl(segmentoCnaeRawValue.empresaModelos ?? []),
    });
  }

  getSegmentoCnae(form: SegmentoCnaeFormGroup): ISegmentoCnae | NewSegmentoCnae {
    return form.getRawValue() as ISegmentoCnae | NewSegmentoCnae;
  }

  resetForm(form: SegmentoCnaeFormGroup, segmentoCnae: SegmentoCnaeFormGroupInput): void {
    const segmentoCnaeRawValue = { ...this.getFormDefaults(), ...segmentoCnae };
    form.reset(
      {
        ...segmentoCnaeRawValue,
        id: { value: segmentoCnaeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SegmentoCnaeFormDefaults {
    return {
      id: null,
      subclasseCnaes: [],
      empresas: [],
      empresaModelos: [],
    };
  }
}
