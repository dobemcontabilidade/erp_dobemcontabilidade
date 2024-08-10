import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClasseCnae, NewClasseCnae } from '../classe-cnae.model';

export type PartialUpdateClasseCnae = Partial<IClasseCnae> & Pick<IClasseCnae, 'id'>;

export type EntityResponseType = HttpResponse<IClasseCnae>;
export type EntityArrayResponseType = HttpResponse<IClasseCnae[]>;

@Injectable({ providedIn: 'root' })
export class ClasseCnaeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/classe-cnaes');

  create(classeCnae: NewClasseCnae): Observable<EntityResponseType> {
    return this.http.post<IClasseCnae>(this.resourceUrl, classeCnae, { observe: 'response' });
  }

  update(classeCnae: IClasseCnae): Observable<EntityResponseType> {
    return this.http.put<IClasseCnae>(`${this.resourceUrl}/${this.getClasseCnaeIdentifier(classeCnae)}`, classeCnae, {
      observe: 'response',
    });
  }

  partialUpdate(classeCnae: PartialUpdateClasseCnae): Observable<EntityResponseType> {
    return this.http.patch<IClasseCnae>(`${this.resourceUrl}/${this.getClasseCnaeIdentifier(classeCnae)}`, classeCnae, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClasseCnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClasseCnae[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getClasseCnaeIdentifier(classeCnae: Pick<IClasseCnae, 'id'>): number {
    return classeCnae.id;
  }

  compareClasseCnae(o1: Pick<IClasseCnae, 'id'> | null, o2: Pick<IClasseCnae, 'id'> | null): boolean {
    return o1 && o2 ? this.getClasseCnaeIdentifier(o1) === this.getClasseCnaeIdentifier(o2) : o1 === o2;
  }

  addClasseCnaeToCollectionIfMissing<Type extends Pick<IClasseCnae, 'id'>>(
    classeCnaeCollection: Type[],
    ...classeCnaesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const classeCnaes: Type[] = classeCnaesToCheck.filter(isPresent);
    if (classeCnaes.length > 0) {
      const classeCnaeCollectionIdentifiers = classeCnaeCollection.map(classeCnaeItem => this.getClasseCnaeIdentifier(classeCnaeItem));
      const classeCnaesToAdd = classeCnaes.filter(classeCnaeItem => {
        const classeCnaeIdentifier = this.getClasseCnaeIdentifier(classeCnaeItem);
        if (classeCnaeCollectionIdentifiers.includes(classeCnaeIdentifier)) {
          return false;
        }
        classeCnaeCollectionIdentifiers.push(classeCnaeIdentifier);
        return true;
      });
      return [...classeCnaesToAdd, ...classeCnaeCollection];
    }
    return classeCnaeCollection;
  }
}
