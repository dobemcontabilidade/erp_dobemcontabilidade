import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdicionalEnquadramento, NewAdicionalEnquadramento } from '../adicional-enquadramento.model';

export type PartialUpdateAdicionalEnquadramento = Partial<IAdicionalEnquadramento> & Pick<IAdicionalEnquadramento, 'id'>;

export type EntityResponseType = HttpResponse<IAdicionalEnquadramento>;
export type EntityArrayResponseType = HttpResponse<IAdicionalEnquadramento[]>;

@Injectable({ providedIn: 'root' })
export class AdicionalEnquadramentoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/adicional-enquadramentos');

  create(adicionalEnquadramento: NewAdicionalEnquadramento): Observable<EntityResponseType> {
    return this.http.post<IAdicionalEnquadramento>(this.resourceUrl, adicionalEnquadramento, { observe: 'response' });
  }

  update(adicionalEnquadramento: IAdicionalEnquadramento): Observable<EntityResponseType> {
    return this.http.put<IAdicionalEnquadramento>(
      `${this.resourceUrl}/${this.getAdicionalEnquadramentoIdentifier(adicionalEnquadramento)}`,
      adicionalEnquadramento,
      { observe: 'response' },
    );
  }

  partialUpdate(adicionalEnquadramento: PartialUpdateAdicionalEnquadramento): Observable<EntityResponseType> {
    return this.http.patch<IAdicionalEnquadramento>(
      `${this.resourceUrl}/${this.getAdicionalEnquadramentoIdentifier(adicionalEnquadramento)}`,
      adicionalEnquadramento,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdicionalEnquadramento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdicionalEnquadramento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAdicionalEnquadramentoIdentifier(adicionalEnquadramento: Pick<IAdicionalEnquadramento, 'id'>): number {
    return adicionalEnquadramento.id;
  }

  compareAdicionalEnquadramento(o1: Pick<IAdicionalEnquadramento, 'id'> | null, o2: Pick<IAdicionalEnquadramento, 'id'> | null): boolean {
    return o1 && o2 ? this.getAdicionalEnquadramentoIdentifier(o1) === this.getAdicionalEnquadramentoIdentifier(o2) : o1 === o2;
  }

  addAdicionalEnquadramentoToCollectionIfMissing<Type extends Pick<IAdicionalEnquadramento, 'id'>>(
    adicionalEnquadramentoCollection: Type[],
    ...adicionalEnquadramentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const adicionalEnquadramentos: Type[] = adicionalEnquadramentosToCheck.filter(isPresent);
    if (adicionalEnquadramentos.length > 0) {
      const adicionalEnquadramentoCollectionIdentifiers = adicionalEnquadramentoCollection.map(adicionalEnquadramentoItem =>
        this.getAdicionalEnquadramentoIdentifier(adicionalEnquadramentoItem),
      );
      const adicionalEnquadramentosToAdd = adicionalEnquadramentos.filter(adicionalEnquadramentoItem => {
        const adicionalEnquadramentoIdentifier = this.getAdicionalEnquadramentoIdentifier(adicionalEnquadramentoItem);
        if (adicionalEnquadramentoCollectionIdentifiers.includes(adicionalEnquadramentoIdentifier)) {
          return false;
        }
        adicionalEnquadramentoCollectionIdentifiers.push(adicionalEnquadramentoIdentifier);
        return true;
      });
      return [...adicionalEnquadramentosToAdd, ...adicionalEnquadramentoCollection];
    }
    return adicionalEnquadramentoCollection;
  }
}
