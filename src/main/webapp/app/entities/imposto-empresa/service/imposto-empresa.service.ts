import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImpostoEmpresa, NewImpostoEmpresa } from '../imposto-empresa.model';

export type PartialUpdateImpostoEmpresa = Partial<IImpostoEmpresa> & Pick<IImpostoEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IImpostoEmpresa>;
export type EntityArrayResponseType = HttpResponse<IImpostoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class ImpostoEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/imposto-empresas');

  create(impostoEmpresa: NewImpostoEmpresa): Observable<EntityResponseType> {
    return this.http.post<IImpostoEmpresa>(this.resourceUrl, impostoEmpresa, { observe: 'response' });
  }

  update(impostoEmpresa: IImpostoEmpresa): Observable<EntityResponseType> {
    return this.http.put<IImpostoEmpresa>(`${this.resourceUrl}/${this.getImpostoEmpresaIdentifier(impostoEmpresa)}`, impostoEmpresa, {
      observe: 'response',
    });
  }

  partialUpdate(impostoEmpresa: PartialUpdateImpostoEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IImpostoEmpresa>(`${this.resourceUrl}/${this.getImpostoEmpresaIdentifier(impostoEmpresa)}`, impostoEmpresa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImpostoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImpostoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getImpostoEmpresaIdentifier(impostoEmpresa: Pick<IImpostoEmpresa, 'id'>): number {
    return impostoEmpresa.id;
  }

  compareImpostoEmpresa(o1: Pick<IImpostoEmpresa, 'id'> | null, o2: Pick<IImpostoEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getImpostoEmpresaIdentifier(o1) === this.getImpostoEmpresaIdentifier(o2) : o1 === o2;
  }

  addImpostoEmpresaToCollectionIfMissing<Type extends Pick<IImpostoEmpresa, 'id'>>(
    impostoEmpresaCollection: Type[],
    ...impostoEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const impostoEmpresas: Type[] = impostoEmpresasToCheck.filter(isPresent);
    if (impostoEmpresas.length > 0) {
      const impostoEmpresaCollectionIdentifiers = impostoEmpresaCollection.map(impostoEmpresaItem =>
        this.getImpostoEmpresaIdentifier(impostoEmpresaItem),
      );
      const impostoEmpresasToAdd = impostoEmpresas.filter(impostoEmpresaItem => {
        const impostoEmpresaIdentifier = this.getImpostoEmpresaIdentifier(impostoEmpresaItem);
        if (impostoEmpresaCollectionIdentifiers.includes(impostoEmpresaIdentifier)) {
          return false;
        }
        impostoEmpresaCollectionIdentifiers.push(impostoEmpresaIdentifier);
        return true;
      });
      return [...impostoEmpresasToAdd, ...impostoEmpresaCollection];
    }
    return impostoEmpresaCollection;
  }
}
