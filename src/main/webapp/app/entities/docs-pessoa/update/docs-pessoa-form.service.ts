import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDocsPessoa, NewDocsPessoa } from '../docs-pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocsPessoa for edit and NewDocsPessoaFormGroupInput for create.
 */
type DocsPessoaFormGroupInput = IDocsPessoa | PartialWithRequiredKeyOf<NewDocsPessoa>;

type DocsPessoaFormDefaults = Pick<NewDocsPessoa, 'id'>;

type DocsPessoaFormGroupContent = {
  id: FormControl<IDocsPessoa['id'] | NewDocsPessoa['id']>;
  urlArquivo: FormControl<IDocsPessoa['urlArquivo']>;
  tipo: FormControl<IDocsPessoa['tipo']>;
  descricao: FormControl<IDocsPessoa['descricao']>;
  pessoa: FormControl<IDocsPessoa['pessoa']>;
};

export type DocsPessoaFormGroup = FormGroup<DocsPessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocsPessoaFormService {
  createDocsPessoaFormGroup(docsPessoa: DocsPessoaFormGroupInput = { id: null }): DocsPessoaFormGroup {
    const docsPessoaRawValue = {
      ...this.getFormDefaults(),
      ...docsPessoa,
    };
    return new FormGroup<DocsPessoaFormGroupContent>({
      id: new FormControl(
        { value: docsPessoaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      urlArquivo: new FormControl(docsPessoaRawValue.urlArquivo, {
        validators: [Validators.required],
      }),
      tipo: new FormControl(docsPessoaRawValue.tipo),
      descricao: new FormControl(docsPessoaRawValue.descricao),
      pessoa: new FormControl(docsPessoaRawValue.pessoa, {
        validators: [Validators.required],
      }),
    });
  }

  getDocsPessoa(form: DocsPessoaFormGroup): IDocsPessoa | NewDocsPessoa {
    return form.getRawValue() as IDocsPessoa | NewDocsPessoa;
  }

  resetForm(form: DocsPessoaFormGroup, docsPessoa: DocsPessoaFormGroupInput): void {
    const docsPessoaRawValue = { ...this.getFormDefaults(), ...docsPessoa };
    form.reset(
      {
        ...docsPessoaRawValue,
        id: { value: docsPessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DocsPessoaFormDefaults {
    return {
      id: null,
    };
  }
}
