import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInstituicaoEnsino, NewInstituicaoEnsino } from '../instituicao-ensino.model';

export type PartialUpdateInstituicaoEnsino = Partial<IInstituicaoEnsino> & Pick<IInstituicaoEnsino, 'id'>;

export type EntityResponseType = HttpResponse<IInstituicaoEnsino>;
export type EntityArrayResponseType = HttpResponse<IInstituicaoEnsino[]>;

@Injectable({ providedIn: 'root' })
export class InstituicaoEnsinoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/instituicao-ensinos');

  create(instituicaoEnsino: NewInstituicaoEnsino): Observable<EntityResponseType> {
    return this.http.post<IInstituicaoEnsino>(this.resourceUrl, instituicaoEnsino, { observe: 'response' });
  }

  update(instituicaoEnsino: IInstituicaoEnsino): Observable<EntityResponseType> {
    return this.http.put<IInstituicaoEnsino>(
      `${this.resourceUrl}/${this.getInstituicaoEnsinoIdentifier(instituicaoEnsino)}`,
      instituicaoEnsino,
      { observe: 'response' },
    );
  }

  partialUpdate(instituicaoEnsino: PartialUpdateInstituicaoEnsino): Observable<EntityResponseType> {
    return this.http.patch<IInstituicaoEnsino>(
      `${this.resourceUrl}/${this.getInstituicaoEnsinoIdentifier(instituicaoEnsino)}`,
      instituicaoEnsino,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInstituicaoEnsino>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInstituicaoEnsino[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInstituicaoEnsinoIdentifier(instituicaoEnsino: Pick<IInstituicaoEnsino, 'id'>): number {
    return instituicaoEnsino.id;
  }

  compareInstituicaoEnsino(o1: Pick<IInstituicaoEnsino, 'id'> | null, o2: Pick<IInstituicaoEnsino, 'id'> | null): boolean {
    return o1 && o2 ? this.getInstituicaoEnsinoIdentifier(o1) === this.getInstituicaoEnsinoIdentifier(o2) : o1 === o2;
  }

  addInstituicaoEnsinoToCollectionIfMissing<Type extends Pick<IInstituicaoEnsino, 'id'>>(
    instituicaoEnsinoCollection: Type[],
    ...instituicaoEnsinosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const instituicaoEnsinos: Type[] = instituicaoEnsinosToCheck.filter(isPresent);
    if (instituicaoEnsinos.length > 0) {
      const instituicaoEnsinoCollectionIdentifiers = instituicaoEnsinoCollection.map(instituicaoEnsinoItem =>
        this.getInstituicaoEnsinoIdentifier(instituicaoEnsinoItem),
      );
      const instituicaoEnsinosToAdd = instituicaoEnsinos.filter(instituicaoEnsinoItem => {
        const instituicaoEnsinoIdentifier = this.getInstituicaoEnsinoIdentifier(instituicaoEnsinoItem);
        if (instituicaoEnsinoCollectionIdentifiers.includes(instituicaoEnsinoIdentifier)) {
          return false;
        }
        instituicaoEnsinoCollectionIdentifiers.push(instituicaoEnsinoIdentifier);
        return true;
      });
      return [...instituicaoEnsinosToAdd, ...instituicaoEnsinoCollection];
    }
    return instituicaoEnsinoCollection;
  }
}
