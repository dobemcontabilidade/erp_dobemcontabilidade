import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFeedBackUsuarioParaContador, NewFeedBackUsuarioParaContador } from '../feed-back-usuario-para-contador.model';

export type PartialUpdateFeedBackUsuarioParaContador = Partial<IFeedBackUsuarioParaContador> & Pick<IFeedBackUsuarioParaContador, 'id'>;

export type EntityResponseType = HttpResponse<IFeedBackUsuarioParaContador>;
export type EntityArrayResponseType = HttpResponse<IFeedBackUsuarioParaContador[]>;

@Injectable({ providedIn: 'root' })
export class FeedBackUsuarioParaContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/feed-back-usuario-para-contadors');

  create(feedBackUsuarioParaContador: NewFeedBackUsuarioParaContador): Observable<EntityResponseType> {
    return this.http.post<IFeedBackUsuarioParaContador>(this.resourceUrl, feedBackUsuarioParaContador, { observe: 'response' });
  }

  update(feedBackUsuarioParaContador: IFeedBackUsuarioParaContador): Observable<EntityResponseType> {
    return this.http.put<IFeedBackUsuarioParaContador>(
      `${this.resourceUrl}/${this.getFeedBackUsuarioParaContadorIdentifier(feedBackUsuarioParaContador)}`,
      feedBackUsuarioParaContador,
      { observe: 'response' },
    );
  }

  partialUpdate(feedBackUsuarioParaContador: PartialUpdateFeedBackUsuarioParaContador): Observable<EntityResponseType> {
    return this.http.patch<IFeedBackUsuarioParaContador>(
      `${this.resourceUrl}/${this.getFeedBackUsuarioParaContadorIdentifier(feedBackUsuarioParaContador)}`,
      feedBackUsuarioParaContador,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeedBackUsuarioParaContador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeedBackUsuarioParaContador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFeedBackUsuarioParaContadorIdentifier(feedBackUsuarioParaContador: Pick<IFeedBackUsuarioParaContador, 'id'>): number {
    return feedBackUsuarioParaContador.id;
  }

  compareFeedBackUsuarioParaContador(
    o1: Pick<IFeedBackUsuarioParaContador, 'id'> | null,
    o2: Pick<IFeedBackUsuarioParaContador, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getFeedBackUsuarioParaContadorIdentifier(o1) === this.getFeedBackUsuarioParaContadorIdentifier(o2) : o1 === o2;
  }

  addFeedBackUsuarioParaContadorToCollectionIfMissing<Type extends Pick<IFeedBackUsuarioParaContador, 'id'>>(
    feedBackUsuarioParaContadorCollection: Type[],
    ...feedBackUsuarioParaContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const feedBackUsuarioParaContadors: Type[] = feedBackUsuarioParaContadorsToCheck.filter(isPresent);
    if (feedBackUsuarioParaContadors.length > 0) {
      const feedBackUsuarioParaContadorCollectionIdentifiers = feedBackUsuarioParaContadorCollection.map(feedBackUsuarioParaContadorItem =>
        this.getFeedBackUsuarioParaContadorIdentifier(feedBackUsuarioParaContadorItem),
      );
      const feedBackUsuarioParaContadorsToAdd = feedBackUsuarioParaContadors.filter(feedBackUsuarioParaContadorItem => {
        const feedBackUsuarioParaContadorIdentifier = this.getFeedBackUsuarioParaContadorIdentifier(feedBackUsuarioParaContadorItem);
        if (feedBackUsuarioParaContadorCollectionIdentifiers.includes(feedBackUsuarioParaContadorIdentifier)) {
          return false;
        }
        feedBackUsuarioParaContadorCollectionIdentifiers.push(feedBackUsuarioParaContadorIdentifier);
        return true;
      });
      return [...feedBackUsuarioParaContadorsToAdd, ...feedBackUsuarioParaContadorCollection];
    }
    return feedBackUsuarioParaContadorCollection;
  }
}
