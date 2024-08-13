import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOpcaoRazaoSocialEmpresa, NewOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';

export type PartialUpdateOpcaoRazaoSocialEmpresa = Partial<IOpcaoRazaoSocialEmpresa> & Pick<IOpcaoRazaoSocialEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IOpcaoRazaoSocialEmpresa>;
export type EntityArrayResponseType = HttpResponse<IOpcaoRazaoSocialEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class OpcaoRazaoSocialEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/opcao-razao-social-empresas');

  create(opcaoRazaoSocialEmpresa: NewOpcaoRazaoSocialEmpresa): Observable<EntityResponseType> {
    return this.http.post<IOpcaoRazaoSocialEmpresa>(this.resourceUrl, opcaoRazaoSocialEmpresa, { observe: 'response' });
  }

  update(opcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa): Observable<EntityResponseType> {
    return this.http.put<IOpcaoRazaoSocialEmpresa>(
      `${this.resourceUrl}/${this.getOpcaoRazaoSocialEmpresaIdentifier(opcaoRazaoSocialEmpresa)}`,
      opcaoRazaoSocialEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(opcaoRazaoSocialEmpresa: PartialUpdateOpcaoRazaoSocialEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IOpcaoRazaoSocialEmpresa>(
      `${this.resourceUrl}/${this.getOpcaoRazaoSocialEmpresaIdentifier(opcaoRazaoSocialEmpresa)}`,
      opcaoRazaoSocialEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOpcaoRazaoSocialEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOpcaoRazaoSocialEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOpcaoRazaoSocialEmpresaIdentifier(opcaoRazaoSocialEmpresa: Pick<IOpcaoRazaoSocialEmpresa, 'id'>): number {
    return opcaoRazaoSocialEmpresa.id;
  }

  compareOpcaoRazaoSocialEmpresa(
    o1: Pick<IOpcaoRazaoSocialEmpresa, 'id'> | null,
    o2: Pick<IOpcaoRazaoSocialEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getOpcaoRazaoSocialEmpresaIdentifier(o1) === this.getOpcaoRazaoSocialEmpresaIdentifier(o2) : o1 === o2;
  }

  addOpcaoRazaoSocialEmpresaToCollectionIfMissing<Type extends Pick<IOpcaoRazaoSocialEmpresa, 'id'>>(
    opcaoRazaoSocialEmpresaCollection: Type[],
    ...opcaoRazaoSocialEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const opcaoRazaoSocialEmpresas: Type[] = opcaoRazaoSocialEmpresasToCheck.filter(isPresent);
    if (opcaoRazaoSocialEmpresas.length > 0) {
      const opcaoRazaoSocialEmpresaCollectionIdentifiers = opcaoRazaoSocialEmpresaCollection.map(opcaoRazaoSocialEmpresaItem =>
        this.getOpcaoRazaoSocialEmpresaIdentifier(opcaoRazaoSocialEmpresaItem),
      );
      const opcaoRazaoSocialEmpresasToAdd = opcaoRazaoSocialEmpresas.filter(opcaoRazaoSocialEmpresaItem => {
        const opcaoRazaoSocialEmpresaIdentifier = this.getOpcaoRazaoSocialEmpresaIdentifier(opcaoRazaoSocialEmpresaItem);
        if (opcaoRazaoSocialEmpresaCollectionIdentifiers.includes(opcaoRazaoSocialEmpresaIdentifier)) {
          return false;
        }
        opcaoRazaoSocialEmpresaCollectionIdentifiers.push(opcaoRazaoSocialEmpresaIdentifier);
        return true;
      });
      return [...opcaoRazaoSocialEmpresasToAdd, ...opcaoRazaoSocialEmpresaCollection];
    }
    return opcaoRazaoSocialEmpresaCollection;
  }
}
