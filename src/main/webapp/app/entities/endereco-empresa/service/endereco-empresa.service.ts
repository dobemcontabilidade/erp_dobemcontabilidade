import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnderecoEmpresa, NewEnderecoEmpresa } from '../endereco-empresa.model';

export type PartialUpdateEnderecoEmpresa = Partial<IEnderecoEmpresa> & Pick<IEnderecoEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IEnderecoEmpresa>;
export type EntityArrayResponseType = HttpResponse<IEnderecoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class EnderecoEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/endereco-empresas');

  create(enderecoEmpresa: NewEnderecoEmpresa): Observable<EntityResponseType> {
    return this.http.post<IEnderecoEmpresa>(this.resourceUrl, enderecoEmpresa, { observe: 'response' });
  }

  update(enderecoEmpresa: IEnderecoEmpresa): Observable<EntityResponseType> {
    return this.http.put<IEnderecoEmpresa>(`${this.resourceUrl}/${this.getEnderecoEmpresaIdentifier(enderecoEmpresa)}`, enderecoEmpresa, {
      observe: 'response',
    });
  }

  partialUpdate(enderecoEmpresa: PartialUpdateEnderecoEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IEnderecoEmpresa>(`${this.resourceUrl}/${this.getEnderecoEmpresaIdentifier(enderecoEmpresa)}`, enderecoEmpresa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnderecoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnderecoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEnderecoEmpresaIdentifier(enderecoEmpresa: Pick<IEnderecoEmpresa, 'id'>): number {
    return enderecoEmpresa.id;
  }

  compareEnderecoEmpresa(o1: Pick<IEnderecoEmpresa, 'id'> | null, o2: Pick<IEnderecoEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getEnderecoEmpresaIdentifier(o1) === this.getEnderecoEmpresaIdentifier(o2) : o1 === o2;
  }

  addEnderecoEmpresaToCollectionIfMissing<Type extends Pick<IEnderecoEmpresa, 'id'>>(
    enderecoEmpresaCollection: Type[],
    ...enderecoEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const enderecoEmpresas: Type[] = enderecoEmpresasToCheck.filter(isPresent);
    if (enderecoEmpresas.length > 0) {
      const enderecoEmpresaCollectionIdentifiers = enderecoEmpresaCollection.map(enderecoEmpresaItem =>
        this.getEnderecoEmpresaIdentifier(enderecoEmpresaItem),
      );
      const enderecoEmpresasToAdd = enderecoEmpresas.filter(enderecoEmpresaItem => {
        const enderecoEmpresaIdentifier = this.getEnderecoEmpresaIdentifier(enderecoEmpresaItem);
        if (enderecoEmpresaCollectionIdentifiers.includes(enderecoEmpresaIdentifier)) {
          return false;
        }
        enderecoEmpresaCollectionIdentifiers.push(enderecoEmpresaIdentifier);
        return true;
      });
      return [...enderecoEmpresasToAdd, ...enderecoEmpresaCollection];
    }
    return enderecoEmpresaCollection;
  }
}
