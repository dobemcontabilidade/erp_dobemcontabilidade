import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPermisao, NewPermisao } from '../permisao.model';

export type PartialUpdatePermisao = Partial<IPermisao> & Pick<IPermisao, 'id'>;

export type EntityResponseType = HttpResponse<IPermisao>;
export type EntityArrayResponseType = HttpResponse<IPermisao[]>;

@Injectable({ providedIn: 'root' })
export class PermisaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/permisaos');

  create(permisao: NewPermisao): Observable<EntityResponseType> {
    return this.http.post<IPermisao>(this.resourceUrl, permisao, { observe: 'response' });
  }

  update(permisao: IPermisao): Observable<EntityResponseType> {
    return this.http.put<IPermisao>(`${this.resourceUrl}/${this.getPermisaoIdentifier(permisao)}`, permisao, { observe: 'response' });
  }

  partialUpdate(permisao: PartialUpdatePermisao): Observable<EntityResponseType> {
    return this.http.patch<IPermisao>(`${this.resourceUrl}/${this.getPermisaoIdentifier(permisao)}`, permisao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPermisao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPermisao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPermisaoIdentifier(permisao: Pick<IPermisao, 'id'>): number {
    return permisao.id;
  }

  comparePermisao(o1: Pick<IPermisao, 'id'> | null, o2: Pick<IPermisao, 'id'> | null): boolean {
    return o1 && o2 ? this.getPermisaoIdentifier(o1) === this.getPermisaoIdentifier(o2) : o1 === o2;
  }

  addPermisaoToCollectionIfMissing<Type extends Pick<IPermisao, 'id'>>(
    permisaoCollection: Type[],
    ...permisaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const permisaos: Type[] = permisaosToCheck.filter(isPresent);
    if (permisaos.length > 0) {
      const permisaoCollectionIdentifiers = permisaoCollection.map(permisaoItem => this.getPermisaoIdentifier(permisaoItem));
      const permisaosToAdd = permisaos.filter(permisaoItem => {
        const permisaoIdentifier = this.getPermisaoIdentifier(permisaoItem);
        if (permisaoCollectionIdentifiers.includes(permisaoIdentifier)) {
          return false;
        }
        permisaoCollectionIdentifiers.push(permisaoIdentifier);
        return true;
      });
      return [...permisaosToAdd, ...permisaoCollection];
    }
    return permisaoCollection;
  }
}
