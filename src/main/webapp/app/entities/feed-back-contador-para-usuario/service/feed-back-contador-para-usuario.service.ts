import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFeedBackContadorParaUsuario, NewFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';

export type PartialUpdateFeedBackContadorParaUsuario = Partial<IFeedBackContadorParaUsuario> & Pick<IFeedBackContadorParaUsuario, 'id'>;

export type EntityResponseType = HttpResponse<IFeedBackContadorParaUsuario>;
export type EntityArrayResponseType = HttpResponse<IFeedBackContadorParaUsuario[]>;

@Injectable({ providedIn: 'root' })
export class FeedBackContadorParaUsuarioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/feed-back-contador-para-usuarios');

  create(feedBackContadorParaUsuario: NewFeedBackContadorParaUsuario): Observable<EntityResponseType> {
    return this.http.post<IFeedBackContadorParaUsuario>(this.resourceUrl, feedBackContadorParaUsuario, { observe: 'response' });
  }

  update(feedBackContadorParaUsuario: IFeedBackContadorParaUsuario): Observable<EntityResponseType> {
    return this.http.put<IFeedBackContadorParaUsuario>(
      `${this.resourceUrl}/${this.getFeedBackContadorParaUsuarioIdentifier(feedBackContadorParaUsuario)}`,
      feedBackContadorParaUsuario,
      { observe: 'response' },
    );
  }

  partialUpdate(feedBackContadorParaUsuario: PartialUpdateFeedBackContadorParaUsuario): Observable<EntityResponseType> {
    return this.http.patch<IFeedBackContadorParaUsuario>(
      `${this.resourceUrl}/${this.getFeedBackContadorParaUsuarioIdentifier(feedBackContadorParaUsuario)}`,
      feedBackContadorParaUsuario,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeedBackContadorParaUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeedBackContadorParaUsuario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFeedBackContadorParaUsuarioIdentifier(feedBackContadorParaUsuario: Pick<IFeedBackContadorParaUsuario, 'id'>): number {
    return feedBackContadorParaUsuario.id;
  }

  compareFeedBackContadorParaUsuario(
    o1: Pick<IFeedBackContadorParaUsuario, 'id'> | null,
    o2: Pick<IFeedBackContadorParaUsuario, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getFeedBackContadorParaUsuarioIdentifier(o1) === this.getFeedBackContadorParaUsuarioIdentifier(o2) : o1 === o2;
  }

  addFeedBackContadorParaUsuarioToCollectionIfMissing<Type extends Pick<IFeedBackContadorParaUsuario, 'id'>>(
    feedBackContadorParaUsuarioCollection: Type[],
    ...feedBackContadorParaUsuariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const feedBackContadorParaUsuarios: Type[] = feedBackContadorParaUsuariosToCheck.filter(isPresent);
    if (feedBackContadorParaUsuarios.length > 0) {
      const feedBackContadorParaUsuarioCollectionIdentifiers = feedBackContadorParaUsuarioCollection.map(feedBackContadorParaUsuarioItem =>
        this.getFeedBackContadorParaUsuarioIdentifier(feedBackContadorParaUsuarioItem),
      );
      const feedBackContadorParaUsuariosToAdd = feedBackContadorParaUsuarios.filter(feedBackContadorParaUsuarioItem => {
        const feedBackContadorParaUsuarioIdentifier = this.getFeedBackContadorParaUsuarioIdentifier(feedBackContadorParaUsuarioItem);
        if (feedBackContadorParaUsuarioCollectionIdentifiers.includes(feedBackContadorParaUsuarioIdentifier)) {
          return false;
        }
        feedBackContadorParaUsuarioCollectionIdentifiers.push(feedBackContadorParaUsuarioIdentifier);
        return true;
      });
      return [...feedBackContadorParaUsuariosToAdd, ...feedBackContadorParaUsuarioCollection];
    }
    return feedBackContadorParaUsuarioCollection;
  }
}
