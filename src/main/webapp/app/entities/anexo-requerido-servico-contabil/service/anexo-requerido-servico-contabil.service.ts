import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoRequeridoServicoContabil, NewAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';

export type PartialUpdateAnexoRequeridoServicoContabil = Partial<IAnexoRequeridoServicoContabil> &
  Pick<IAnexoRequeridoServicoContabil, 'id'>;

export type EntityResponseType = HttpResponse<IAnexoRequeridoServicoContabil>;
export type EntityArrayResponseType = HttpResponse<IAnexoRequeridoServicoContabil[]>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoServicoContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-requerido-servico-contabils');

  create(anexoRequeridoServicoContabil: NewAnexoRequeridoServicoContabil): Observable<EntityResponseType> {
    return this.http.post<IAnexoRequeridoServicoContabil>(this.resourceUrl, anexoRequeridoServicoContabil, { observe: 'response' });
  }

  update(anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil): Observable<EntityResponseType> {
    return this.http.put<IAnexoRequeridoServicoContabil>(
      `${this.resourceUrl}/${this.getAnexoRequeridoServicoContabilIdentifier(anexoRequeridoServicoContabil)}`,
      anexoRequeridoServicoContabil,
      { observe: 'response' },
    );
  }

  partialUpdate(anexoRequeridoServicoContabil: PartialUpdateAnexoRequeridoServicoContabil): Observable<EntityResponseType> {
    return this.http.patch<IAnexoRequeridoServicoContabil>(
      `${this.resourceUrl}/${this.getAnexoRequeridoServicoContabilIdentifier(anexoRequeridoServicoContabil)}`,
      anexoRequeridoServicoContabil,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexoRequeridoServicoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexoRequeridoServicoContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoRequeridoServicoContabilIdentifier(anexoRequeridoServicoContabil: Pick<IAnexoRequeridoServicoContabil, 'id'>): number {
    return anexoRequeridoServicoContabil.id;
  }

  compareAnexoRequeridoServicoContabil(
    o1: Pick<IAnexoRequeridoServicoContabil, 'id'> | null,
    o2: Pick<IAnexoRequeridoServicoContabil, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAnexoRequeridoServicoContabilIdentifier(o1) === this.getAnexoRequeridoServicoContabilIdentifier(o2)
      : o1 === o2;
  }

  addAnexoRequeridoServicoContabilToCollectionIfMissing<Type extends Pick<IAnexoRequeridoServicoContabil, 'id'>>(
    anexoRequeridoServicoContabilCollection: Type[],
    ...anexoRequeridoServicoContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoRequeridoServicoContabils: Type[] = anexoRequeridoServicoContabilsToCheck.filter(isPresent);
    if (anexoRequeridoServicoContabils.length > 0) {
      const anexoRequeridoServicoContabilCollectionIdentifiers = anexoRequeridoServicoContabilCollection.map(
        anexoRequeridoServicoContabilItem => this.getAnexoRequeridoServicoContabilIdentifier(anexoRequeridoServicoContabilItem),
      );
      const anexoRequeridoServicoContabilsToAdd = anexoRequeridoServicoContabils.filter(anexoRequeridoServicoContabilItem => {
        const anexoRequeridoServicoContabilIdentifier = this.getAnexoRequeridoServicoContabilIdentifier(anexoRequeridoServicoContabilItem);
        if (anexoRequeridoServicoContabilCollectionIdentifiers.includes(anexoRequeridoServicoContabilIdentifier)) {
          return false;
        }
        anexoRequeridoServicoContabilCollectionIdentifiers.push(anexoRequeridoServicoContabilIdentifier);
        return true;
      });
      return [...anexoRequeridoServicoContabilsToAdd, ...anexoRequeridoServicoContabilCollection];
    }
    return anexoRequeridoServicoContabilCollection;
  }
}
