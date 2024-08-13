import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFeedBackContadorParaUsuario, NewFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFeedBackContadorParaUsuario for edit and NewFeedBackContadorParaUsuarioFormGroupInput for create.
 */
type FeedBackContadorParaUsuarioFormGroupInput = IFeedBackContadorParaUsuario | PartialWithRequiredKeyOf<NewFeedBackContadorParaUsuario>;

type FeedBackContadorParaUsuarioFormDefaults = Pick<NewFeedBackContadorParaUsuario, 'id'>;

type FeedBackContadorParaUsuarioFormGroupContent = {
  id: FormControl<IFeedBackContadorParaUsuario['id'] | NewFeedBackContadorParaUsuario['id']>;
  comentario: FormControl<IFeedBackContadorParaUsuario['comentario']>;
  pontuacao: FormControl<IFeedBackContadorParaUsuario['pontuacao']>;
  criterioAvaliacaoAtor: FormControl<IFeedBackContadorParaUsuario['criterioAvaliacaoAtor']>;
  usuarioEmpresa: FormControl<IFeedBackContadorParaUsuario['usuarioEmpresa']>;
  contador: FormControl<IFeedBackContadorParaUsuario['contador']>;
  ordemServico: FormControl<IFeedBackContadorParaUsuario['ordemServico']>;
};

export type FeedBackContadorParaUsuarioFormGroup = FormGroup<FeedBackContadorParaUsuarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FeedBackContadorParaUsuarioFormService {
  createFeedBackContadorParaUsuarioFormGroup(
    feedBackContadorParaUsuario: FeedBackContadorParaUsuarioFormGroupInput = { id: null },
  ): FeedBackContadorParaUsuarioFormGroup {
    const feedBackContadorParaUsuarioRawValue = {
      ...this.getFormDefaults(),
      ...feedBackContadorParaUsuario,
    };
    return new FormGroup<FeedBackContadorParaUsuarioFormGroupContent>({
      id: new FormControl(
        { value: feedBackContadorParaUsuarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      comentario: new FormControl(feedBackContadorParaUsuarioRawValue.comentario),
      pontuacao: new FormControl(feedBackContadorParaUsuarioRawValue.pontuacao),
      criterioAvaliacaoAtor: new FormControl(feedBackContadorParaUsuarioRawValue.criterioAvaliacaoAtor, {
        validators: [Validators.required],
      }),
      usuarioEmpresa: new FormControl(feedBackContadorParaUsuarioRawValue.usuarioEmpresa, {
        validators: [Validators.required],
      }),
      contador: new FormControl(feedBackContadorParaUsuarioRawValue.contador, {
        validators: [Validators.required],
      }),
      ordemServico: new FormControl(feedBackContadorParaUsuarioRawValue.ordemServico, {
        validators: [Validators.required],
      }),
    });
  }

  getFeedBackContadorParaUsuario(
    form: FeedBackContadorParaUsuarioFormGroup,
  ): IFeedBackContadorParaUsuario | NewFeedBackContadorParaUsuario {
    return form.getRawValue() as IFeedBackContadorParaUsuario | NewFeedBackContadorParaUsuario;
  }

  resetForm(form: FeedBackContadorParaUsuarioFormGroup, feedBackContadorParaUsuario: FeedBackContadorParaUsuarioFormGroupInput): void {
    const feedBackContadorParaUsuarioRawValue = { ...this.getFormDefaults(), ...feedBackContadorParaUsuario };
    form.reset(
      {
        ...feedBackContadorParaUsuarioRawValue,
        id: { value: feedBackContadorParaUsuarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FeedBackContadorParaUsuarioFormDefaults {
    return {
      id: null,
    };
  }
}
