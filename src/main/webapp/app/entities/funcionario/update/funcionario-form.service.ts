import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFuncionario, NewFuncionario } from '../funcionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuncionario for edit and NewFuncionarioFormGroupInput for create.
 */
type FuncionarioFormGroupInput = IFuncionario | PartialWithRequiredKeyOf<NewFuncionario>;

type FuncionarioFormDefaults = Pick<NewFuncionario, 'id' | 'reintegrado' | 'primeiroEmprego' | 'multiploVinculos' | 'filiacaoSindical'>;

type FuncionarioFormGroupContent = {
  id: FormControl<IFuncionario['id'] | NewFuncionario['id']>;
  numeroPisNisPasep: FormControl<IFuncionario['numeroPisNisPasep']>;
  reintegrado: FormControl<IFuncionario['reintegrado']>;
  primeiroEmprego: FormControl<IFuncionario['primeiroEmprego']>;
  multiploVinculos: FormControl<IFuncionario['multiploVinculos']>;
  dataOpcaoFgts: FormControl<IFuncionario['dataOpcaoFgts']>;
  filiacaoSindical: FormControl<IFuncionario['filiacaoSindical']>;
  cnpjSindicato: FormControl<IFuncionario['cnpjSindicato']>;
  tipoFuncionarioEnum: FormControl<IFuncionario['tipoFuncionarioEnum']>;
  usuarioEmpresa: FormControl<IFuncionario['usuarioEmpresa']>;
  pessoa: FormControl<IFuncionario['pessoa']>;
  empresa: FormControl<IFuncionario['empresa']>;
  profissao: FormControl<IFuncionario['profissao']>;
};

export type FuncionarioFormGroup = FormGroup<FuncionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuncionarioFormService {
  createFuncionarioFormGroup(funcionario: FuncionarioFormGroupInput = { id: null }): FuncionarioFormGroup {
    const funcionarioRawValue = {
      ...this.getFormDefaults(),
      ...funcionario,
    };
    return new FormGroup<FuncionarioFormGroupContent>({
      id: new FormControl(
        { value: funcionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      numeroPisNisPasep: new FormControl(funcionarioRawValue.numeroPisNisPasep),
      reintegrado: new FormControl(funcionarioRawValue.reintegrado),
      primeiroEmprego: new FormControl(funcionarioRawValue.primeiroEmprego),
      multiploVinculos: new FormControl(funcionarioRawValue.multiploVinculos),
      dataOpcaoFgts: new FormControl(funcionarioRawValue.dataOpcaoFgts),
      filiacaoSindical: new FormControl(funcionarioRawValue.filiacaoSindical),
      cnpjSindicato: new FormControl(funcionarioRawValue.cnpjSindicato, {
        validators: [Validators.maxLength(20)],
      }),
      tipoFuncionarioEnum: new FormControl(funcionarioRawValue.tipoFuncionarioEnum),
      usuarioEmpresa: new FormControl(funcionarioRawValue.usuarioEmpresa, {
        validators: [Validators.required],
      }),
      pessoa: new FormControl(funcionarioRawValue.pessoa, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(funcionarioRawValue.empresa, {
        validators: [Validators.required],
      }),
      profissao: new FormControl(funcionarioRawValue.profissao, {
        validators: [Validators.required],
      }),
    });
  }

  getFuncionario(form: FuncionarioFormGroup): IFuncionario | NewFuncionario {
    return form.getRawValue() as IFuncionario | NewFuncionario;
  }

  resetForm(form: FuncionarioFormGroup, funcionario: FuncionarioFormGroupInput): void {
    const funcionarioRawValue = { ...this.getFormDefaults(), ...funcionario };
    form.reset(
      {
        ...funcionarioRawValue,
        id: { value: funcionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuncionarioFormDefaults {
    return {
      id: null,
      reintegrado: false,
      primeiroEmprego: false,
      multiploVinculos: false,
      filiacaoSindical: false,
    };
  }
}
