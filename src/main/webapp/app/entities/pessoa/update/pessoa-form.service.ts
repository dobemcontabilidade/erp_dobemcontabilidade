import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPessoa, NewPessoa } from '../pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPessoa for edit and NewPessoaFormGroupInput for create.
 */
type PessoaFormGroupInput = IPessoa | PartialWithRequiredKeyOf<NewPessoa>;

type PessoaFormDefaults = Pick<NewPessoa, 'id'>;

type PessoaFormGroupContent = {
  id: FormControl<IPessoa['id'] | NewPessoa['id']>;
  nome: FormControl<IPessoa['nome']>;
  cpf: FormControl<IPessoa['cpf']>;
  dataNascimento: FormControl<IPessoa['dataNascimento']>;
  tituloEleitor: FormControl<IPessoa['tituloEleitor']>;
  rg: FormControl<IPessoa['rg']>;
  rgOrgaoExpeditor: FormControl<IPessoa['rgOrgaoExpeditor']>;
  rgUfExpedicao: FormControl<IPessoa['rgUfExpedicao']>;
  nomeMae: FormControl<IPessoa['nomeMae']>;
  nomePai: FormControl<IPessoa['nomePai']>;
  localNascimento: FormControl<IPessoa['localNascimento']>;
  racaECor: FormControl<IPessoa['racaECor']>;
  pessoaComDeficiencia: FormControl<IPessoa['pessoaComDeficiencia']>;
  estadoCivil: FormControl<IPessoa['estadoCivil']>;
  sexo: FormControl<IPessoa['sexo']>;
  urlFotoPerfil: FormControl<IPessoa['urlFotoPerfil']>;
};

export type PessoaFormGroup = FormGroup<PessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PessoaFormService {
  createPessoaFormGroup(pessoa: PessoaFormGroupInput = { id: null }): PessoaFormGroup {
    const pessoaRawValue = {
      ...this.getFormDefaults(),
      ...pessoa,
    };
    return new FormGroup<PessoaFormGroupContent>({
      id: new FormControl(
        { value: pessoaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(pessoaRawValue.nome, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      cpf: new FormControl(pessoaRawValue.cpf, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      dataNascimento: new FormControl(pessoaRawValue.dataNascimento, {
        validators: [Validators.maxLength(15)],
      }),
      tituloEleitor: new FormControl(pessoaRawValue.tituloEleitor),
      rg: new FormControl(pessoaRawValue.rg, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      rgOrgaoExpeditor: new FormControl(pessoaRawValue.rgOrgaoExpeditor),
      rgUfExpedicao: new FormControl(pessoaRawValue.rgUfExpedicao),
      nomeMae: new FormControl(pessoaRawValue.nomeMae, {
        validators: [Validators.maxLength(200)],
      }),
      nomePai: new FormControl(pessoaRawValue.nomePai, {
        validators: [Validators.maxLength(200)],
      }),
      localNascimento: new FormControl(pessoaRawValue.localNascimento),
      racaECor: new FormControl(pessoaRawValue.racaECor),
      pessoaComDeficiencia: new FormControl(pessoaRawValue.pessoaComDeficiencia),
      estadoCivil: new FormControl(pessoaRawValue.estadoCivil),
      sexo: new FormControl(pessoaRawValue.sexo, {
        validators: [Validators.required],
      }),
      urlFotoPerfil: new FormControl(pessoaRawValue.urlFotoPerfil),
    });
  }

  getPessoa(form: PessoaFormGroup): IPessoa | NewPessoa {
    return form.getRawValue() as IPessoa | NewPessoa;
  }

  resetForm(form: PessoaFormGroup, pessoa: PessoaFormGroupInput): void {
    const pessoaRawValue = { ...this.getFormDefaults(), ...pessoa };
    form.reset(
      {
        ...pessoaRawValue,
        id: { value: pessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PessoaFormDefaults {
    return {
      id: null,
    };
  }
}
