import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServicoContabilEtapaFluxoModelo, NewServicoContabilEtapaFluxoModelo } from '../servico-contabil-etapa-fluxo-modelo.model';

export type PartialUpdateServicoContabilEtapaFluxoModelo = Partial<IServicoContabilEtapaFluxoModelo> &
  Pick<IServicoContabilEtapaFluxoModelo, 'id'>;

export type EntityResponseType = HttpResponse<IServicoContabilEtapaFluxoModelo>;
export type EntityArrayResponseType = HttpResponse<IServicoContabilEtapaFluxoModelo[]>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilEtapaFluxoModeloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/servico-contabil-etapa-fluxo-modelos');

  create(servicoContabilEtapaFluxoModelo: NewServicoContabilEtapaFluxoModelo): Observable<EntityResponseType> {
    return this.http.post<IServicoContabilEtapaFluxoModelo>(this.resourceUrl, servicoContabilEtapaFluxoModelo, { observe: 'response' });
  }

  update(servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo): Observable<EntityResponseType> {
    return this.http.put<IServicoContabilEtapaFluxoModelo>(
      `${this.resourceUrl}/${this.getServicoContabilEtapaFluxoModeloIdentifier(servicoContabilEtapaFluxoModelo)}`,
      servicoContabilEtapaFluxoModelo,
      { observe: 'response' },
    );
  }

  partialUpdate(servicoContabilEtapaFluxoModelo: PartialUpdateServicoContabilEtapaFluxoModelo): Observable<EntityResponseType> {
    return this.http.patch<IServicoContabilEtapaFluxoModelo>(
      `${this.resourceUrl}/${this.getServicoContabilEtapaFluxoModeloIdentifier(servicoContabilEtapaFluxoModelo)}`,
      servicoContabilEtapaFluxoModelo,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServicoContabilEtapaFluxoModelo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServicoContabilEtapaFluxoModelo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServicoContabilEtapaFluxoModeloIdentifier(servicoContabilEtapaFluxoModelo: Pick<IServicoContabilEtapaFluxoModelo, 'id'>): number {
    return servicoContabilEtapaFluxoModelo.id;
  }

  compareServicoContabilEtapaFluxoModelo(
    o1: Pick<IServicoContabilEtapaFluxoModelo, 'id'> | null,
    o2: Pick<IServicoContabilEtapaFluxoModelo, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getServicoContabilEtapaFluxoModeloIdentifier(o1) === this.getServicoContabilEtapaFluxoModeloIdentifier(o2)
      : o1 === o2;
  }

  addServicoContabilEtapaFluxoModeloToCollectionIfMissing<Type extends Pick<IServicoContabilEtapaFluxoModelo, 'id'>>(
    servicoContabilEtapaFluxoModeloCollection: Type[],
    ...servicoContabilEtapaFluxoModelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const servicoContabilEtapaFluxoModelos: Type[] = servicoContabilEtapaFluxoModelosToCheck.filter(isPresent);
    if (servicoContabilEtapaFluxoModelos.length > 0) {
      const servicoContabilEtapaFluxoModeloCollectionIdentifiers = servicoContabilEtapaFluxoModeloCollection.map(
        servicoContabilEtapaFluxoModeloItem => this.getServicoContabilEtapaFluxoModeloIdentifier(servicoContabilEtapaFluxoModeloItem),
      );
      const servicoContabilEtapaFluxoModelosToAdd = servicoContabilEtapaFluxoModelos.filter(servicoContabilEtapaFluxoModeloItem => {
        const servicoContabilEtapaFluxoModeloIdentifier =
          this.getServicoContabilEtapaFluxoModeloIdentifier(servicoContabilEtapaFluxoModeloItem);
        if (servicoContabilEtapaFluxoModeloCollectionIdentifiers.includes(servicoContabilEtapaFluxoModeloIdentifier)) {
          return false;
        }
        servicoContabilEtapaFluxoModeloCollectionIdentifiers.push(servicoContabilEtapaFluxoModeloIdentifier);
        return true;
      });
      return [...servicoContabilEtapaFluxoModelosToAdd, ...servicoContabilEtapaFluxoModeloCollection];
    }
    return servicoContabilEtapaFluxoModeloCollection;
  }
}
