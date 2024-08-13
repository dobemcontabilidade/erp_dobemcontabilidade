import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServicoContabilEmpresaModelo, NewServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';

export type PartialUpdateServicoContabilEmpresaModelo = Partial<IServicoContabilEmpresaModelo> & Pick<IServicoContabilEmpresaModelo, 'id'>;

export type EntityResponseType = HttpResponse<IServicoContabilEmpresaModelo>;
export type EntityArrayResponseType = HttpResponse<IServicoContabilEmpresaModelo[]>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilEmpresaModeloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/servico-contabil-empresa-modelos');

  create(servicoContabilEmpresaModelo: NewServicoContabilEmpresaModelo): Observable<EntityResponseType> {
    return this.http.post<IServicoContabilEmpresaModelo>(this.resourceUrl, servicoContabilEmpresaModelo, { observe: 'response' });
  }

  update(servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo): Observable<EntityResponseType> {
    return this.http.put<IServicoContabilEmpresaModelo>(
      `${this.resourceUrl}/${this.getServicoContabilEmpresaModeloIdentifier(servicoContabilEmpresaModelo)}`,
      servicoContabilEmpresaModelo,
      { observe: 'response' },
    );
  }

  partialUpdate(servicoContabilEmpresaModelo: PartialUpdateServicoContabilEmpresaModelo): Observable<EntityResponseType> {
    return this.http.patch<IServicoContabilEmpresaModelo>(
      `${this.resourceUrl}/${this.getServicoContabilEmpresaModeloIdentifier(servicoContabilEmpresaModelo)}`,
      servicoContabilEmpresaModelo,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServicoContabilEmpresaModelo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServicoContabilEmpresaModelo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServicoContabilEmpresaModeloIdentifier(servicoContabilEmpresaModelo: Pick<IServicoContabilEmpresaModelo, 'id'>): number {
    return servicoContabilEmpresaModelo.id;
  }

  compareServicoContabilEmpresaModelo(
    o1: Pick<IServicoContabilEmpresaModelo, 'id'> | null,
    o2: Pick<IServicoContabilEmpresaModelo, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getServicoContabilEmpresaModeloIdentifier(o1) === this.getServicoContabilEmpresaModeloIdentifier(o2) : o1 === o2;
  }

  addServicoContabilEmpresaModeloToCollectionIfMissing<Type extends Pick<IServicoContabilEmpresaModelo, 'id'>>(
    servicoContabilEmpresaModeloCollection: Type[],
    ...servicoContabilEmpresaModelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const servicoContabilEmpresaModelos: Type[] = servicoContabilEmpresaModelosToCheck.filter(isPresent);
    if (servicoContabilEmpresaModelos.length > 0) {
      const servicoContabilEmpresaModeloCollectionIdentifiers = servicoContabilEmpresaModeloCollection.map(
        servicoContabilEmpresaModeloItem => this.getServicoContabilEmpresaModeloIdentifier(servicoContabilEmpresaModeloItem),
      );
      const servicoContabilEmpresaModelosToAdd = servicoContabilEmpresaModelos.filter(servicoContabilEmpresaModeloItem => {
        const servicoContabilEmpresaModeloIdentifier = this.getServicoContabilEmpresaModeloIdentifier(servicoContabilEmpresaModeloItem);
        if (servicoContabilEmpresaModeloCollectionIdentifiers.includes(servicoContabilEmpresaModeloIdentifier)) {
          return false;
        }
        servicoContabilEmpresaModeloCollectionIdentifiers.push(servicoContabilEmpresaModeloIdentifier);
        return true;
      });
      return [...servicoContabilEmpresaModelosToAdd, ...servicoContabilEmpresaModeloCollection];
    }
    return servicoContabilEmpresaModeloCollection;
  }
}
