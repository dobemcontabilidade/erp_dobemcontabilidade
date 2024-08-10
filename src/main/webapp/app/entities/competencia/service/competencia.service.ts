import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompetencia, NewCompetencia } from '../competencia.model';

export type PartialUpdateCompetencia = Partial<ICompetencia> & Pick<ICompetencia, 'id'>;

export type EntityResponseType = HttpResponse<ICompetencia>;
export type EntityArrayResponseType = HttpResponse<ICompetencia[]>;

@Injectable({ providedIn: 'root' })
export class CompetenciaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/competencias');

  create(competencia: NewCompetencia): Observable<EntityResponseType> {
    return this.http.post<ICompetencia>(this.resourceUrl, competencia, { observe: 'response' });
  }

  update(competencia: ICompetencia): Observable<EntityResponseType> {
    return this.http.put<ICompetencia>(`${this.resourceUrl}/${this.getCompetenciaIdentifier(competencia)}`, competencia, {
      observe: 'response',
    });
  }

  partialUpdate(competencia: PartialUpdateCompetencia): Observable<EntityResponseType> {
    return this.http.patch<ICompetencia>(`${this.resourceUrl}/${this.getCompetenciaIdentifier(competencia)}`, competencia, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompetenciaIdentifier(competencia: Pick<ICompetencia, 'id'>): number {
    return competencia.id;
  }

  compareCompetencia(o1: Pick<ICompetencia, 'id'> | null, o2: Pick<ICompetencia, 'id'> | null): boolean {
    return o1 && o2 ? this.getCompetenciaIdentifier(o1) === this.getCompetenciaIdentifier(o2) : o1 === o2;
  }

  addCompetenciaToCollectionIfMissing<Type extends Pick<ICompetencia, 'id'>>(
    competenciaCollection: Type[],
    ...competenciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const competencias: Type[] = competenciasToCheck.filter(isPresent);
    if (competencias.length > 0) {
      const competenciaCollectionIdentifiers = competenciaCollection.map(competenciaItem => this.getCompetenciaIdentifier(competenciaItem));
      const competenciasToAdd = competencias.filter(competenciaItem => {
        const competenciaIdentifier = this.getCompetenciaIdentifier(competenciaItem);
        if (competenciaCollectionIdentifiers.includes(competenciaIdentifier)) {
          return false;
        }
        competenciaCollectionIdentifiers.push(competenciaIdentifier);
        return true;
      });
      return [...competenciasToAdd, ...competenciaCollection];
    }
    return competenciaCollection;
  }
}
