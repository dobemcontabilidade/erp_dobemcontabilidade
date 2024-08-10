import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnquadramento, NewEnquadramento } from '../enquadramento.model';

export type PartialUpdateEnquadramento = Partial<IEnquadramento> & Pick<IEnquadramento, 'id'>;

export type EntityResponseType = HttpResponse<IEnquadramento>;
export type EntityArrayResponseType = HttpResponse<IEnquadramento[]>;

@Injectable({ providedIn: 'root' })
export class EnquadramentoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/enquadramentos');

  create(enquadramento: NewEnquadramento): Observable<EntityResponseType> {
    return this.http.post<IEnquadramento>(this.resourceUrl, enquadramento, { observe: 'response' });
  }

  update(enquadramento: IEnquadramento): Observable<EntityResponseType> {
    return this.http.put<IEnquadramento>(`${this.resourceUrl}/${this.getEnquadramentoIdentifier(enquadramento)}`, enquadramento, {
      observe: 'response',
    });
  }

  partialUpdate(enquadramento: PartialUpdateEnquadramento): Observable<EntityResponseType> {
    return this.http.patch<IEnquadramento>(`${this.resourceUrl}/${this.getEnquadramentoIdentifier(enquadramento)}`, enquadramento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnquadramento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnquadramento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEnquadramentoIdentifier(enquadramento: Pick<IEnquadramento, 'id'>): number {
    return enquadramento.id;
  }

  compareEnquadramento(o1: Pick<IEnquadramento, 'id'> | null, o2: Pick<IEnquadramento, 'id'> | null): boolean {
    return o1 && o2 ? this.getEnquadramentoIdentifier(o1) === this.getEnquadramentoIdentifier(o2) : o1 === o2;
  }

  addEnquadramentoToCollectionIfMissing<Type extends Pick<IEnquadramento, 'id'>>(
    enquadramentoCollection: Type[],
    ...enquadramentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const enquadramentos: Type[] = enquadramentosToCheck.filter(isPresent);
    if (enquadramentos.length > 0) {
      const enquadramentoCollectionIdentifiers = enquadramentoCollection.map(enquadramentoItem =>
        this.getEnquadramentoIdentifier(enquadramentoItem),
      );
      const enquadramentosToAdd = enquadramentos.filter(enquadramentoItem => {
        const enquadramentoIdentifier = this.getEnquadramentoIdentifier(enquadramentoItem);
        if (enquadramentoCollectionIdentifiers.includes(enquadramentoIdentifier)) {
          return false;
        }
        enquadramentoCollectionIdentifiers.push(enquadramentoIdentifier);
        return true;
      });
      return [...enquadramentosToAdd, ...enquadramentoCollection];
    }
    return enquadramentoCollection;
  }
}
