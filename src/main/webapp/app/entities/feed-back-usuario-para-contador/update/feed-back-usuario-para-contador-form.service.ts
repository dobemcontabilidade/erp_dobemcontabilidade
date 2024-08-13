import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFeedBackUsuarioParaContador, NewFeedBackUsuarioParaContador } from '../feed-back-usuario-para-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFeedBackUsuarioParaContador for edit and NewFeedBackUsuarioParaContadorFormGroupInput for create.
 */
type FeedBackUsuarioParaContadorFormGroupInput = IFeedBackUsuarioParaContador | PartialWithRequiredKeyOf<NewFeedBackUsuarioParaContador>;

type FeedBackUsuarioParaContadorFormDefaults = Pick<NewFeedBackUsuarioParaContador, 'id'>;

type FeedBackUsuarioParaContadorFormGroupContent = {
  id: FormControl<IFeedBackUsuarioParaContador['id'] | NewFeedBackUsuarioParaContador['id']>;
  comentario: FormControl<IFeedBackUsuarioParaContador['comentario']>;
  pontuacao: FormControl<IFeedBackUsuarioParaContador['pontuacao']>;
  usuarioEmpresa: FormControl<IFeedBackUsuarioParaContador['usuarioEmpresa']>;
  usuarioContador: FormControl<IFeedBackUsuarioParaContador['usuarioContador']>;
  criterioAvaliacaoAtor: FormControl<IFeedBackUsuarioParaContador['criterioAvaliacaoAtor']>;
  ordemServico: FormControl<IFeedBackUsuarioParaContador['ordemServico']>;
};

export type FeedBackUsuarioParaContadorFormGroup = FormGroup<FeedBackUsuarioParaContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FeedBackUsuarioParaContadorFormService {
  createFeedBackUsuarioParaContadorFormGroup(
    feedBackUsuarioParaContador: FeedBackUsuarioParaContadorFormGroupInput = { id: null },
  ): FeedBackUsuarioParaContadorFormGroup {
    const feedBackUsuarioParaContadorRawValue = {
      ...this.getFormDefaults(),
      ...feedBackUsuarioParaContador,
    };
    return new FormGroup<FeedBackUsuarioParaContadorFormGroupContent>({
      id: new FormControl(
        { value: feedBackUsuarioParaContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      comentario: new FormControl(feedBackUsuarioParaContadorRawValue.comentario),
      pontuacao: new FormControl(feedBackUsuarioParaContadorRawValue.pontuacao),
      usuarioEmpresa: new FormControl(feedBackUsuarioParaContadorRawValue.usuarioEmpresa, {
        validators: [Validators.required],
      }),
      usuarioContador: new FormControl(feedBackUsuarioParaContadorRawValue.usuarioContador, {
        validators: [Validators.required],
      }),
      criterioAvaliacaoAtor: new FormControl(feedBackUsuarioParaContadorRawValue.criterioAvaliacaoAtor, {
        validators: [Validators.required],
      }),
      ordemServico: new FormControl(feedBackUsuarioParaContadorRawValue.ordemServico, {
        validators: [Validators.required],
      }),
    });
  }

  getFeedBackUsuarioParaContador(
    form: FeedBackUsuarioParaContadorFormGroup,
  ): IFeedBackUsuarioParaContador | NewFeedBackUsuarioParaContador {
    return form.getRawValue() as IFeedBackUsuarioParaContador | NewFeedBackUsuarioParaContador;
  }

  resetForm(form: FeedBackUsuarioParaContadorFormGroup, feedBackUsuarioParaContador: FeedBackUsuarioParaContadorFormGroupInput): void {
    const feedBackUsuarioParaContadorRawValue = { ...this.getFormDefaults(), ...feedBackUsuarioParaContador };
    form.reset(
      {
        ...feedBackUsuarioParaContadorRawValue,
        id: { value: feedBackUsuarioParaContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FeedBackUsuarioParaContadorFormDefaults {
    return {
      id: null,
    };
  }
}
