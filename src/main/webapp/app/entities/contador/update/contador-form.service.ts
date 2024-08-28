import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContador, NewContador } from '../contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContador for edit and NewContadorFormGroupInput for create.
 */
type ContadorFormGroupInput = IContador | PartialWithRequiredKeyOf<NewContador>;

type ContadorFormDefaults = Pick<NewContador, 'id'>;

type ContadorFormGroupContent = {
  id: FormControl<IContador['id'] | NewContador['id']>;
  nome: FormControl<IContador['nome']>;
  cpf: FormControl<IContador['cpf']>;
  dataNascimento: FormControl<IContador['dataNascimento']>;
  tituloEleitor: FormControl<IContador['tituloEleitor']>;
  rg: FormControl<IContador['rg']>;
  rgOrgaoExpeditor: FormControl<IContador['rgOrgaoExpeditor']>;
  rgUfExpedicao: FormControl<IContador['rgUfExpedicao']>;
  nomeMae: FormControl<IContador['nomeMae']>;
  nomePai: FormControl<IContador['nomePai']>;
  localNascimento: FormControl<IContador['localNascimento']>;
  racaECor: FormControl<IContador['racaECor']>;
  pessoaComDeficiencia: FormControl<IContador['pessoaComDeficiencia']>;
  estadoCivil: FormControl<IContador['estadoCivil']>;
  sexo: FormControl<IContador['sexo']>;
  urlFotoPerfil: FormControl<IContador['urlFotoPerfil']>;
  rgOrgaoExpditor: FormControl<IContador['rgOrgaoExpditor']>;
  crc: FormControl<IContador['crc']>;
  limiteEmpresas: FormControl<IContador['limiteEmpresas']>;
  limiteAreaContabils: FormControl<IContador['limiteAreaContabils']>;
  limiteFaturamento: FormControl<IContador['limiteFaturamento']>;
  limiteDepartamentos: FormControl<IContador['limiteDepartamentos']>;
  situacaoContador: FormControl<IContador['situacaoContador']>;
};

export type ContadorFormGroup = FormGroup<ContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContadorFormService {
  createContadorFormGroup(contador: ContadorFormGroupInput = { id: null }): ContadorFormGroup {
    const contadorRawValue = {
      ...this.getFormDefaults(),
      ...contador,
    };
    return new FormGroup<ContadorFormGroupContent>({
      id: new FormControl(
        { value: contadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(contadorRawValue.nome, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      cpf: new FormControl(contadorRawValue.cpf, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      dataNascimento: new FormControl(contadorRawValue.dataNascimento, {
        validators: [Validators.maxLength(15)],
      }),
      tituloEleitor: new FormControl(contadorRawValue.tituloEleitor),
      rg: new FormControl(contadorRawValue.rg, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      rgOrgaoExpeditor: new FormControl(contadorRawValue.rgOrgaoExpeditor),
      rgUfExpedicao: new FormControl(contadorRawValue.rgUfExpedicao),
      nomeMae: new FormControl(contadorRawValue.nomeMae, {
        validators: [Validators.maxLength(200)],
      }),
      nomePai: new FormControl(contadorRawValue.nomePai, {
        validators: [Validators.maxLength(200)],
      }),
      localNascimento: new FormControl(contadorRawValue.localNascimento),
      racaECor: new FormControl(contadorRawValue.racaECor),
      pessoaComDeficiencia: new FormControl(contadorRawValue.pessoaComDeficiencia),
      estadoCivil: new FormControl(contadorRawValue.estadoCivil),
      sexo: new FormControl(contadorRawValue.sexo, {
        validators: [Validators.required],
      }),
      urlFotoPerfil: new FormControl(contadorRawValue.urlFotoPerfil),
      rgOrgaoExpditor: new FormControl(contadorRawValue.rgOrgaoExpditor),
      crc: new FormControl(contadorRawValue.crc, {
        validators: [Validators.required],
      }),
      limiteEmpresas: new FormControl(contadorRawValue.limiteEmpresas),
      limiteAreaContabils: new FormControl(contadorRawValue.limiteAreaContabils),
      limiteFaturamento: new FormControl(contadorRawValue.limiteFaturamento),
      limiteDepartamentos: new FormControl(contadorRawValue.limiteDepartamentos),
      situacaoContador: new FormControl(contadorRawValue.situacaoContador),
    });
  }

  getContador(form: ContadorFormGroup): IContador | NewContador {
    return form.getRawValue() as IContador | NewContador;
  }

  resetForm(form: ContadorFormGroup, contador: ContadorFormGroupInput): void {
    const contadorRawValue = { ...this.getFormDefaults(), ...contador };
    form.reset(
      {
        ...contadorRawValue,
        id: { value: contadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContadorFormDefaults {
    return {
      id: null,
    };
  }
}
