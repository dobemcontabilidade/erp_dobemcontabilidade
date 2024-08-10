import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBancoContador, NewBancoContador } from '../banco-contador.model';

export type PartialUpdateBancoContador = Partial<IBancoContador> & Pick<IBancoContador, 'id'>;

export type EntityResponseType = HttpResponse<IBancoContador>;
export type EntityArrayResponseType = HttpResponse<IBancoContador[]>;

@Injectable({ providedIn: 'root' })
export class BancoContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/banco-contadors');

  create(bancoContador: NewBancoContador): Observable<EntityResponseType> {
    return this.http.post<IBancoContador>(this.resourceUrl, bancoContador, { observe: 'response' });
  }

  update(bancoContador: IBancoContador): Observable<EntityResponseType> {
    return this.http.put<IBancoContador>(`${this.resourceUrl}/${this.getBancoContadorIdentifier(bancoContador)}`, bancoContador, {
      observe: 'response',
    });
  }

  partialUpdate(bancoContador: PartialUpdateBancoContador): Observable<EntityResponseType> {
    return this.http.patch<IBancoContador>(`${this.resourceUrl}/${this.getBancoContadorIdentifier(bancoContador)}`, bancoContador, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBancoContador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBancoContador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBancoContadorIdentifier(bancoContador: Pick<IBancoContador, 'id'>): number {
    return bancoContador.id;
  }

  compareBancoContador(o1: Pick<IBancoContador, 'id'> | null, o2: Pick<IBancoContador, 'id'> | null): boolean {
    return o1 && o2 ? this.getBancoContadorIdentifier(o1) === this.getBancoContadorIdentifier(o2) : o1 === o2;
  }

  addBancoContadorToCollectionIfMissing<Type extends Pick<IBancoContador, 'id'>>(
    bancoContadorCollection: Type[],
    ...bancoContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bancoContadors: Type[] = bancoContadorsToCheck.filter(isPresent);
    if (bancoContadors.length > 0) {
      const bancoContadorCollectionIdentifiers = bancoContadorCollection.map(bancoContadorItem =>
        this.getBancoContadorIdentifier(bancoContadorItem),
      );
      const bancoContadorsToAdd = bancoContadors.filter(bancoContadorItem => {
        const bancoContadorIdentifier = this.getBancoContadorIdentifier(bancoContadorItem);
        if (bancoContadorCollectionIdentifiers.includes(bancoContadorIdentifier)) {
          return false;
        }
        bancoContadorCollectionIdentifiers.push(bancoContadorIdentifier);
        return true;
      });
      return [...bancoContadorsToAdd, ...bancoContadorCollection];
    }
    return bancoContadorCollection;
  }
}
