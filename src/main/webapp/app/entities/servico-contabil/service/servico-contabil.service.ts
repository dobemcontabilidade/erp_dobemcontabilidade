import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServicoContabil, NewServicoContabil } from '../servico-contabil.model';

export type PartialUpdateServicoContabil = Partial<IServicoContabil> & Pick<IServicoContabil, 'id'>;

export type EntityResponseType = HttpResponse<IServicoContabil>;
export type EntityArrayResponseType = HttpResponse<IServicoContabil[]>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/servico-contabils');

  create(servicoContabil: NewServicoContabil): Observable<EntityResponseType> {
    return this.http.post<IServicoContabil>(this.resourceUrl, servicoContabil, { observe: 'response' });
  }

  update(servicoContabil: IServicoContabil): Observable<EntityResponseType> {
    return this.http.put<IServicoContabil>(`${this.resourceUrl}/${this.getServicoContabilIdentifier(servicoContabil)}`, servicoContabil, {
      observe: 'response',
    });
  }

  partialUpdate(servicoContabil: PartialUpdateServicoContabil): Observable<EntityResponseType> {
    return this.http.patch<IServicoContabil>(`${this.resourceUrl}/${this.getServicoContabilIdentifier(servicoContabil)}`, servicoContabil, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServicoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServicoContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServicoContabilIdentifier(servicoContabil: Pick<IServicoContabil, 'id'>): number {
    return servicoContabil.id;
  }

  compareServicoContabil(o1: Pick<IServicoContabil, 'id'> | null, o2: Pick<IServicoContabil, 'id'> | null): boolean {
    return o1 && o2 ? this.getServicoContabilIdentifier(o1) === this.getServicoContabilIdentifier(o2) : o1 === o2;
  }

  addServicoContabilToCollectionIfMissing<Type extends Pick<IServicoContabil, 'id'>>(
    servicoContabilCollection: Type[],
    ...servicoContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const servicoContabils: Type[] = servicoContabilsToCheck.filter(isPresent);
    if (servicoContabils.length > 0) {
      const servicoContabilCollectionIdentifiers = servicoContabilCollection.map(servicoContabilItem =>
        this.getServicoContabilIdentifier(servicoContabilItem),
      );
      const servicoContabilsToAdd = servicoContabils.filter(servicoContabilItem => {
        const servicoContabilIdentifier = this.getServicoContabilIdentifier(servicoContabilItem);
        if (servicoContabilCollectionIdentifiers.includes(servicoContabilIdentifier)) {
          return false;
        }
        servicoContabilCollectionIdentifiers.push(servicoContabilIdentifier);
        return true;
      });
      return [...servicoContabilsToAdd, ...servicoContabilCollection];
    }
    return servicoContabilCollection;
  }
}
