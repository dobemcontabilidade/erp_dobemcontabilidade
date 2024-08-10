import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
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

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPessoa | NewPessoa> = Omit<T, 'dataNascimento'> & {
  dataNascimento?: string | null;
};

type PessoaFormRawValue = FormValueOf<IPessoa>;

type NewPessoaFormRawValue = FormValueOf<NewPessoa>;

type PessoaFormDefaults = Pick<NewPessoa, 'id' | 'dataNascimento'>;

type PessoaFormGroupContent = {
  id: FormControl<PessoaFormRawValue['id'] | NewPessoa['id']>;
  nome: FormControl<PessoaFormRawValue['nome']>;
  cpf: FormControl<PessoaFormRawValue['cpf']>;
  dataNascimento: FormControl<PessoaFormRawValue['dataNascimento']>;
  tituloEleitor: FormControl<PessoaFormRawValue['tituloEleitor']>;
  rg: FormControl<PessoaFormRawValue['rg']>;
  rgOrgaoExpditor: FormControl<PessoaFormRawValue['rgOrgaoExpditor']>;
  rgUfExpedicao: FormControl<PessoaFormRawValue['rgUfExpedicao']>;
  estadoCivil: FormControl<PessoaFormRawValue['estadoCivil']>;
  sexo: FormControl<PessoaFormRawValue['sexo']>;
  urlFotoPerfil: FormControl<PessoaFormRawValue['urlFotoPerfil']>;
};

export type PessoaFormGroup = FormGroup<PessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PessoaFormService {
  createPessoaFormGroup(pessoa: PessoaFormGroupInput = { id: null }): PessoaFormGroup {
    const pessoaRawValue = this.convertPessoaToPessoaRawValue({
      ...this.getFormDefaults(),
      ...pessoa,
    });
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
        validators: [Validators.required],
      }),
      dataNascimento: new FormControl(pessoaRawValue.dataNascimento),
      tituloEleitor: new FormControl(pessoaRawValue.tituloEleitor),
      rg: new FormControl(pessoaRawValue.rg, {
        validators: [Validators.required],
      }),
      rgOrgaoExpditor: new FormControl(pessoaRawValue.rgOrgaoExpditor),
      rgUfExpedicao: new FormControl(pessoaRawValue.rgUfExpedicao),
      estadoCivil: new FormControl(pessoaRawValue.estadoCivil),
      sexo: new FormControl(pessoaRawValue.sexo, {
        validators: [Validators.required],
      }),
      urlFotoPerfil: new FormControl(pessoaRawValue.urlFotoPerfil),
    });
  }

  getPessoa(form: PessoaFormGroup): IPessoa | NewPessoa {
    return this.convertPessoaRawValueToPessoa(form.getRawValue() as PessoaFormRawValue | NewPessoaFormRawValue);
  }

  resetForm(form: PessoaFormGroup, pessoa: PessoaFormGroupInput): void {
    const pessoaRawValue = this.convertPessoaToPessoaRawValue({ ...this.getFormDefaults(), ...pessoa });
    form.reset(
      {
        ...pessoaRawValue,
        id: { value: pessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PessoaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataNascimento: currentTime,
    };
  }

  private convertPessoaRawValueToPessoa(rawPessoa: PessoaFormRawValue | NewPessoaFormRawValue): IPessoa | NewPessoa {
    return {
      ...rawPessoa,
      dataNascimento: dayjs(rawPessoa.dataNascimento, DATE_TIME_FORMAT),
    };
  }

  private convertPessoaToPessoaRawValue(
    pessoa: IPessoa | (Partial<NewPessoa> & PessoaFormDefaults),
  ): PessoaFormRawValue | PartialWithRequiredKeyOf<NewPessoaFormRawValue> {
    return {
      ...pessoa,
      dataNascimento: pessoa.dataNascimento ? pessoa.dataNascimento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
