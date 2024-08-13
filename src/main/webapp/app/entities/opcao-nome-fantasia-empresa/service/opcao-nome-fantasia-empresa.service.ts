import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOpcaoNomeFantasiaEmpresa, NewOpcaoNomeFantasiaEmpresa } from '../opcao-nome-fantasia-empresa.model';

export type PartialUpdateOpcaoNomeFantasiaEmpresa = Partial<IOpcaoNomeFantasiaEmpresa> & Pick<IOpcaoNomeFantasiaEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IOpcaoNomeFantasiaEmpresa>;
export type EntityArrayResponseType = HttpResponse<IOpcaoNomeFantasiaEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class OpcaoNomeFantasiaEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/opcao-nome-fantasia-empresas');

  create(opcaoNomeFantasiaEmpresa: NewOpcaoNomeFantasiaEmpresa): Observable<EntityResponseType> {
    return this.http.post<IOpcaoNomeFantasiaEmpresa>(this.resourceUrl, opcaoNomeFantasiaEmpresa, { observe: 'response' });
  }

  update(opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa): Observable<EntityResponseType> {
    return this.http.put<IOpcaoNomeFantasiaEmpresa>(
      `${this.resourceUrl}/${this.getOpcaoNomeFantasiaEmpresaIdentifier(opcaoNomeFantasiaEmpresa)}`,
      opcaoNomeFantasiaEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(opcaoNomeFantasiaEmpresa: PartialUpdateOpcaoNomeFantasiaEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IOpcaoNomeFantasiaEmpresa>(
      `${this.resourceUrl}/${this.getOpcaoNomeFantasiaEmpresaIdentifier(opcaoNomeFantasiaEmpresa)}`,
      opcaoNomeFantasiaEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOpcaoNomeFantasiaEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOpcaoNomeFantasiaEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOpcaoNomeFantasiaEmpresaIdentifier(opcaoNomeFantasiaEmpresa: Pick<IOpcaoNomeFantasiaEmpresa, 'id'>): number {
    return opcaoNomeFantasiaEmpresa.id;
  }

  compareOpcaoNomeFantasiaEmpresa(
    o1: Pick<IOpcaoNomeFantasiaEmpresa, 'id'> | null,
    o2: Pick<IOpcaoNomeFantasiaEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getOpcaoNomeFantasiaEmpresaIdentifier(o1) === this.getOpcaoNomeFantasiaEmpresaIdentifier(o2) : o1 === o2;
  }

  addOpcaoNomeFantasiaEmpresaToCollectionIfMissing<Type extends Pick<IOpcaoNomeFantasiaEmpresa, 'id'>>(
    opcaoNomeFantasiaEmpresaCollection: Type[],
    ...opcaoNomeFantasiaEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const opcaoNomeFantasiaEmpresas: Type[] = opcaoNomeFantasiaEmpresasToCheck.filter(isPresent);
    if (opcaoNomeFantasiaEmpresas.length > 0) {
      const opcaoNomeFantasiaEmpresaCollectionIdentifiers = opcaoNomeFantasiaEmpresaCollection.map(opcaoNomeFantasiaEmpresaItem =>
        this.getOpcaoNomeFantasiaEmpresaIdentifier(opcaoNomeFantasiaEmpresaItem),
      );
      const opcaoNomeFantasiaEmpresasToAdd = opcaoNomeFantasiaEmpresas.filter(opcaoNomeFantasiaEmpresaItem => {
        const opcaoNomeFantasiaEmpresaIdentifier = this.getOpcaoNomeFantasiaEmpresaIdentifier(opcaoNomeFantasiaEmpresaItem);
        if (opcaoNomeFantasiaEmpresaCollectionIdentifiers.includes(opcaoNomeFantasiaEmpresaIdentifier)) {
          return false;
        }
        opcaoNomeFantasiaEmpresaCollectionIdentifiers.push(opcaoNomeFantasiaEmpresaIdentifier);
        return true;
      });
      return [...opcaoNomeFantasiaEmpresasToAdd, ...opcaoNomeFantasiaEmpresaCollection];
    }
    return opcaoNomeFantasiaEmpresaCollection;
  }
}
