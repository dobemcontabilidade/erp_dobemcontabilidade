import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPerfilContador, NewPerfilContador } from '../perfil-contador.model';

export type PartialUpdatePerfilContador = Partial<IPerfilContador> & Pick<IPerfilContador, 'id'>;

export type EntityResponseType = HttpResponse<IPerfilContador>;
export type EntityArrayResponseType = HttpResponse<IPerfilContador[]>;

@Injectable({ providedIn: 'root' })
export class PerfilContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/perfil-contadors');

  create(perfilContador: NewPerfilContador): Observable<EntityResponseType> {
    return this.http.post<IPerfilContador>(this.resourceUrl, perfilContador, { observe: 'response' });
  }

  update(perfilContador: IPerfilContador): Observable<EntityResponseType> {
    return this.http.put<IPerfilContador>(`${this.resourceUrl}/${this.getPerfilContadorIdentifier(perfilContador)}`, perfilContador, {
      observe: 'response',
    });
  }

  partialUpdate(perfilContador: PartialUpdatePerfilContador): Observable<EntityResponseType> {
    return this.http.patch<IPerfilContador>(`${this.resourceUrl}/${this.getPerfilContadorIdentifier(perfilContador)}`, perfilContador, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfilContador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfilContador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPerfilContadorIdentifier(perfilContador: Pick<IPerfilContador, 'id'>): number {
    return perfilContador.id;
  }

  comparePerfilContador(o1: Pick<IPerfilContador, 'id'> | null, o2: Pick<IPerfilContador, 'id'> | null): boolean {
    return o1 && o2 ? this.getPerfilContadorIdentifier(o1) === this.getPerfilContadorIdentifier(o2) : o1 === o2;
  }

  addPerfilContadorToCollectionIfMissing<Type extends Pick<IPerfilContador, 'id'>>(
    perfilContadorCollection: Type[],
    ...perfilContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const perfilContadors: Type[] = perfilContadorsToCheck.filter(isPresent);
    if (perfilContadors.length > 0) {
      const perfilContadorCollectionIdentifiers = perfilContadorCollection.map(perfilContadorItem =>
        this.getPerfilContadorIdentifier(perfilContadorItem),
      );
      const perfilContadorsToAdd = perfilContadors.filter(perfilContadorItem => {
        const perfilContadorIdentifier = this.getPerfilContadorIdentifier(perfilContadorItem);
        if (perfilContadorCollectionIdentifiers.includes(perfilContadorIdentifier)) {
          return false;
        }
        perfilContadorCollectionIdentifiers.push(perfilContadorIdentifier);
        return true;
      });
      return [...perfilContadorsToAdd, ...perfilContadorCollection];
    }
    return perfilContadorCollection;
  }
}
