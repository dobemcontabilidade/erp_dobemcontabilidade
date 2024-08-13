import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubTarefaRecorrente, NewSubTarefaRecorrente } from '../sub-tarefa-recorrente.model';

export type PartialUpdateSubTarefaRecorrente = Partial<ISubTarefaRecorrente> & Pick<ISubTarefaRecorrente, 'id'>;

export type EntityResponseType = HttpResponse<ISubTarefaRecorrente>;
export type EntityArrayResponseType = HttpResponse<ISubTarefaRecorrente[]>;

@Injectable({ providedIn: 'root' })
export class SubTarefaRecorrenteService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sub-tarefa-recorrentes');

  create(subTarefaRecorrente: NewSubTarefaRecorrente): Observable<EntityResponseType> {
    return this.http.post<ISubTarefaRecorrente>(this.resourceUrl, subTarefaRecorrente, { observe: 'response' });
  }

  update(subTarefaRecorrente: ISubTarefaRecorrente): Observable<EntityResponseType> {
    return this.http.put<ISubTarefaRecorrente>(
      `${this.resourceUrl}/${this.getSubTarefaRecorrenteIdentifier(subTarefaRecorrente)}`,
      subTarefaRecorrente,
      { observe: 'response' },
    );
  }

  partialUpdate(subTarefaRecorrente: PartialUpdateSubTarefaRecorrente): Observable<EntityResponseType> {
    return this.http.patch<ISubTarefaRecorrente>(
      `${this.resourceUrl}/${this.getSubTarefaRecorrenteIdentifier(subTarefaRecorrente)}`,
      subTarefaRecorrente,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubTarefaRecorrente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubTarefaRecorrente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSubTarefaRecorrenteIdentifier(subTarefaRecorrente: Pick<ISubTarefaRecorrente, 'id'>): number {
    return subTarefaRecorrente.id;
  }

  compareSubTarefaRecorrente(o1: Pick<ISubTarefaRecorrente, 'id'> | null, o2: Pick<ISubTarefaRecorrente, 'id'> | null): boolean {
    return o1 && o2 ? this.getSubTarefaRecorrenteIdentifier(o1) === this.getSubTarefaRecorrenteIdentifier(o2) : o1 === o2;
  }

  addSubTarefaRecorrenteToCollectionIfMissing<Type extends Pick<ISubTarefaRecorrente, 'id'>>(
    subTarefaRecorrenteCollection: Type[],
    ...subTarefaRecorrentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const subTarefaRecorrentes: Type[] = subTarefaRecorrentesToCheck.filter(isPresent);
    if (subTarefaRecorrentes.length > 0) {
      const subTarefaRecorrenteCollectionIdentifiers = subTarefaRecorrenteCollection.map(subTarefaRecorrenteItem =>
        this.getSubTarefaRecorrenteIdentifier(subTarefaRecorrenteItem),
      );
      const subTarefaRecorrentesToAdd = subTarefaRecorrentes.filter(subTarefaRecorrenteItem => {
        const subTarefaRecorrenteIdentifier = this.getSubTarefaRecorrenteIdentifier(subTarefaRecorrenteItem);
        if (subTarefaRecorrenteCollectionIdentifiers.includes(subTarefaRecorrenteIdentifier)) {
          return false;
        }
        subTarefaRecorrenteCollectionIdentifiers.push(subTarefaRecorrenteIdentifier);
        return true;
      });
      return [...subTarefaRecorrentesToAdd, ...subTarefaRecorrenteCollection];
    }
    return subTarefaRecorrenteCollection;
  }
}
