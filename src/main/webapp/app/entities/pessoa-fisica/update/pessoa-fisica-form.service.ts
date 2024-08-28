import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPessoaFisica, NewPessoaFisica } from '../pessoa-fisica.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPessoaFisica for edit and NewPessoaFisicaFormGroupInput for create.
 */
type PessoaFisicaFormGroupInput = IPessoaFisica | PartialWithRequiredKeyOf<NewPessoaFisica>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPessoaFisica | NewPessoaFisica> = Omit<T, 'dataNascimento'> & {
  dataNascimento?: string | null;
};

type PessoaFisicaFormRawValue = FormValueOf<IPessoaFisica>;

type NewPessoaFisicaFormRawValue = FormValueOf<NewPessoaFisica>;

type PessoaFisicaFormDefaults = Pick<NewPessoaFisica, 'id' | 'dataNascimento'>;

type PessoaFisicaFormGroupContent = {
  id: FormControl<PessoaFisicaFormRawValue['id'] | NewPessoaFisica['id']>;
  nome: FormControl<PessoaFisicaFormRawValue['nome']>;
  cpf: FormControl<PessoaFisicaFormRawValue['cpf']>;
  dataNascimento: FormControl<PessoaFisicaFormRawValue['dataNascimento']>;
  tituloEleitor: FormControl<PessoaFisicaFormRawValue['tituloEleitor']>;
  rg: FormControl<PessoaFisicaFormRawValue['rg']>;
  rgOrgaoExpditor: FormControl<PessoaFisicaFormRawValue['rgOrgaoExpditor']>;
  rgUfExpedicao: FormControl<PessoaFisicaFormRawValue['rgUfExpedicao']>;
  estadoCivil: FormControl<PessoaFisicaFormRawValue['estadoCivil']>;
  sexo: FormControl<PessoaFisicaFormRawValue['sexo']>;
  urlFotoPerfil: FormControl<PessoaFisicaFormRawValue['urlFotoPerfil']>;
};

export type PessoaFisicaFormGroup = FormGroup<PessoaFisicaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PessoaFisicaFormService {
  createPessoaFisicaFormGroup(pessoaFisica: PessoaFisicaFormGroupInput = { id: null }): PessoaFisicaFormGroup {
    const pessoaFisicaRawValue = this.convertPessoaFisicaToPessoaFisicaRawValue({
      ...this.getFormDefaults(),
      ...pessoaFisica,
    });
    return new FormGroup<PessoaFisicaFormGroupContent>({
      id: new FormControl(
        { value: pessoaFisicaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(pessoaFisicaRawValue.nome, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      cpf: new FormControl(pessoaFisicaRawValue.cpf, {
        validators: [Validators.required],
      }),
      dataNascimento: new FormControl(pessoaFisicaRawValue.dataNascimento),
      tituloEleitor: new FormControl(pessoaFisicaRawValue.tituloEleitor),
      rg: new FormControl(pessoaFisicaRawValue.rg, {
        validators: [Validators.required],
      }),
      rgOrgaoExpditor: new FormControl(pessoaFisicaRawValue.rgOrgaoExpditor),
      rgUfExpedicao: new FormControl(pessoaFisicaRawValue.rgUfExpedicao),
      estadoCivil: new FormControl(pessoaFisicaRawValue.estadoCivil),
      sexo: new FormControl(pessoaFisicaRawValue.sexo, {
        validators: [Validators.required],
      }),
      urlFotoPerfil: new FormControl(pessoaFisicaRawValue.urlFotoPerfil),
    });
  }

  getPessoaFisica(form: PessoaFisicaFormGroup): IPessoaFisica | NewPessoaFisica {
    return this.convertPessoaFisicaRawValueToPessoaFisica(form.getRawValue() as PessoaFisicaFormRawValue | NewPessoaFisicaFormRawValue);
  }

  resetForm(form: PessoaFisicaFormGroup, pessoaFisica: PessoaFisicaFormGroupInput): void {
    const pessoaFisicaRawValue = this.convertPessoaFisicaToPessoaFisicaRawValue({ ...this.getFormDefaults(), ...pessoaFisica });
    form.reset(
      {
        ...pessoaFisicaRawValue,
        id: { value: pessoaFisicaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PessoaFisicaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataNascimento: currentTime,
    };
  }

  private convertPessoaFisicaRawValueToPessoaFisica(
    rawPessoaFisica: PessoaFisicaFormRawValue | NewPessoaFisicaFormRawValue,
  ): IPessoaFisica | NewPessoaFisica {
    return {
      ...rawPessoaFisica,
      dataNascimento: dayjs(rawPessoaFisica.dataNascimento, DATE_TIME_FORMAT),
    };
  }

  private convertPessoaFisicaToPessoaFisicaRawValue(
    pessoaFisica: IPessoaFisica | (Partial<NewPessoaFisica> & PessoaFisicaFormDefaults),
  ): PessoaFisicaFormRawValue | PartialWithRequiredKeyOf<NewPessoaFisicaFormRawValue> {
    return {
      ...pessoaFisica,
      dataNascimento: pessoaFisica.dataNascimento ? pessoaFisica.dataNascimento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
