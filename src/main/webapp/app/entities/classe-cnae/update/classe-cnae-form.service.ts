import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IClasseCnae, NewClasseCnae } from '../classe-cnae.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClasseCnae for edit and NewClasseCnaeFormGroupInput for create.
 */
type ClasseCnaeFormGroupInput = IClasseCnae | PartialWithRequiredKeyOf<NewClasseCnae>;

type ClasseCnaeFormDefaults = Pick<NewClasseCnae, 'id'>;

type ClasseCnaeFormGroupContent = {
  id: FormControl<IClasseCnae['id'] | NewClasseCnae['id']>;
  codigo: FormControl<IClasseCnae['codigo']>;
  descricao: FormControl<IClasseCnae['descricao']>;
  grupo: FormControl<IClasseCnae['grupo']>;
};

export type ClasseCnaeFormGroup = FormGroup<ClasseCnaeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClasseCnaeFormService {
  createClasseCnaeFormGroup(classeCnae: ClasseCnaeFormGroupInput = { id: null }): ClasseCnaeFormGroup {
    const classeCnaeRawValue = {
      ...this.getFormDefaults(),
      ...classeCnae,
    };
    return new FormGroup<ClasseCnaeFormGroupContent>({
      id: new FormControl(
        { value: classeCnaeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigo: new FormControl(classeCnaeRawValue.codigo, {
        validators: [Validators.maxLength(15)],
      }),
      descricao: new FormControl(classeCnaeRawValue.descricao, {
        validators: [Validators.required],
      }),
      grupo: new FormControl(classeCnaeRawValue.grupo, {
        validators: [Validators.required],
      }),
    });
  }

  getClasseCnae(form: ClasseCnaeFormGroup): IClasseCnae | NewClasseCnae {
    return form.getRawValue() as IClasseCnae | NewClasseCnae;
  }

  resetForm(form: ClasseCnaeFormGroup, classeCnae: ClasseCnaeFormGroupInput): void {
    const classeCnaeRawValue = { ...this.getFormDefaults(), ...classeCnae };
    form.reset(
      {
        ...classeCnaeRawValue,
        id: { value: classeCnaeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ClasseCnaeFormDefaults {
    return {
      id: null,
    };
  }
}
