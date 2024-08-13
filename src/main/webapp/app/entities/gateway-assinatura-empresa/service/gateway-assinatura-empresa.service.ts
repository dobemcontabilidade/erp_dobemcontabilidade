import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGatewayAssinaturaEmpresa, NewGatewayAssinaturaEmpresa } from '../gateway-assinatura-empresa.model';

export type PartialUpdateGatewayAssinaturaEmpresa = Partial<IGatewayAssinaturaEmpresa> & Pick<IGatewayAssinaturaEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IGatewayAssinaturaEmpresa>;
export type EntityArrayResponseType = HttpResponse<IGatewayAssinaturaEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class GatewayAssinaturaEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/gateway-assinatura-empresas');

  create(gatewayAssinaturaEmpresa: NewGatewayAssinaturaEmpresa): Observable<EntityResponseType> {
    return this.http.post<IGatewayAssinaturaEmpresa>(this.resourceUrl, gatewayAssinaturaEmpresa, { observe: 'response' });
  }

  update(gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa): Observable<EntityResponseType> {
    return this.http.put<IGatewayAssinaturaEmpresa>(
      `${this.resourceUrl}/${this.getGatewayAssinaturaEmpresaIdentifier(gatewayAssinaturaEmpresa)}`,
      gatewayAssinaturaEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(gatewayAssinaturaEmpresa: PartialUpdateGatewayAssinaturaEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IGatewayAssinaturaEmpresa>(
      `${this.resourceUrl}/${this.getGatewayAssinaturaEmpresaIdentifier(gatewayAssinaturaEmpresa)}`,
      gatewayAssinaturaEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGatewayAssinaturaEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGatewayAssinaturaEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGatewayAssinaturaEmpresaIdentifier(gatewayAssinaturaEmpresa: Pick<IGatewayAssinaturaEmpresa, 'id'>): number {
    return gatewayAssinaturaEmpresa.id;
  }

  compareGatewayAssinaturaEmpresa(
    o1: Pick<IGatewayAssinaturaEmpresa, 'id'> | null,
    o2: Pick<IGatewayAssinaturaEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getGatewayAssinaturaEmpresaIdentifier(o1) === this.getGatewayAssinaturaEmpresaIdentifier(o2) : o1 === o2;
  }

  addGatewayAssinaturaEmpresaToCollectionIfMissing<Type extends Pick<IGatewayAssinaturaEmpresa, 'id'>>(
    gatewayAssinaturaEmpresaCollection: Type[],
    ...gatewayAssinaturaEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const gatewayAssinaturaEmpresas: Type[] = gatewayAssinaturaEmpresasToCheck.filter(isPresent);
    if (gatewayAssinaturaEmpresas.length > 0) {
      const gatewayAssinaturaEmpresaCollectionIdentifiers = gatewayAssinaturaEmpresaCollection.map(gatewayAssinaturaEmpresaItem =>
        this.getGatewayAssinaturaEmpresaIdentifier(gatewayAssinaturaEmpresaItem),
      );
      const gatewayAssinaturaEmpresasToAdd = gatewayAssinaturaEmpresas.filter(gatewayAssinaturaEmpresaItem => {
        const gatewayAssinaturaEmpresaIdentifier = this.getGatewayAssinaturaEmpresaIdentifier(gatewayAssinaturaEmpresaItem);
        if (gatewayAssinaturaEmpresaCollectionIdentifiers.includes(gatewayAssinaturaEmpresaIdentifier)) {
          return false;
        }
        gatewayAssinaturaEmpresaCollectionIdentifiers.push(gatewayAssinaturaEmpresaIdentifier);
        return true;
      });
      return [...gatewayAssinaturaEmpresasToAdd, ...gatewayAssinaturaEmpresaCollection];
    }
    return gatewayAssinaturaEmpresaCollection;
  }
}
