import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContador, NewContador } from '../contador.model';

export type PartialUpdateContador = Partial<IContador> & Pick<IContador, 'id'>;

export type EntityResponseType = HttpResponse<IContador>;
export type EntityArrayResponseType = HttpResponse<IContador[]>;

@Injectable({ providedIn: 'root' })
export class ContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contadors');

  create(contador: NewContador): Observable<EntityResponseType> {
    return this.http.post<IContador>(this.resourceUrl, contador, { observe: 'response' });
  }

  update(contador: IContador): Observable<EntityResponseType> {
    return this.http.put<IContador>(`${this.resourceUrl}/${this.getContadorIdentifier(contador)}`, contador, { observe: 'response' });
  }

  partialUpdate(contador: PartialUpdateContador): Observable<EntityResponseType> {
    return this.http.patch<IContador>(`${this.resourceUrl}/${this.getContadorIdentifier(contador)}`, contador, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContadorIdentifier(contador: Pick<IContador, 'id'>): number {
    return contador.id;
  }

  compareContador(o1: Pick<IContador, 'id'> | null, o2: Pick<IContador, 'id'> | null): boolean {
    return o1 && o2 ? this.getContadorIdentifier(o1) === this.getContadorIdentifier(o2) : o1 === o2;
  }

  addContadorToCollectionIfMissing<Type extends Pick<IContador, 'id'>>(
    contadorCollection: Type[],
    ...contadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contadors: Type[] = contadorsToCheck.filter(isPresent);
    if (contadors.length > 0) {
      const contadorCollectionIdentifiers = contadorCollection.map(contadorItem => this.getContadorIdentifier(contadorItem));
      const contadorsToAdd = contadors.filter(contadorItem => {
        const contadorIdentifier = this.getContadorIdentifier(contadorItem);
        if (contadorCollectionIdentifiers.includes(contadorIdentifier)) {
          return false;
        }
        contadorCollectionIdentifiers.push(contadorIdentifier);
        return true;
      });
      return [...contadorsToAdd, ...contadorCollection];
    }
    return contadorCollection;
  }
}
