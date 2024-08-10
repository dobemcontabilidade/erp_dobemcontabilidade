import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubtarefa, NewSubtarefa } from '../subtarefa.model';

export type PartialUpdateSubtarefa = Partial<ISubtarefa> & Pick<ISubtarefa, 'id'>;

export type EntityResponseType = HttpResponse<ISubtarefa>;
export type EntityArrayResponseType = HttpResponse<ISubtarefa[]>;

@Injectable({ providedIn: 'root' })
export class SubtarefaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subtarefas');

  create(subtarefa: NewSubtarefa): Observable<EntityResponseType> {
    return this.http.post<ISubtarefa>(this.resourceUrl, subtarefa, { observe: 'response' });
  }

  update(subtarefa: ISubtarefa): Observable<EntityResponseType> {
    return this.http.put<ISubtarefa>(`${this.resourceUrl}/${this.getSubtarefaIdentifier(subtarefa)}`, subtarefa, { observe: 'response' });
  }

  partialUpdate(subtarefa: PartialUpdateSubtarefa): Observable<EntityResponseType> {
    return this.http.patch<ISubtarefa>(`${this.resourceUrl}/${this.getSubtarefaIdentifier(subtarefa)}`, subtarefa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubtarefa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubtarefa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSubtarefaIdentifier(subtarefa: Pick<ISubtarefa, 'id'>): number {
    return subtarefa.id;
  }

  compareSubtarefa(o1: Pick<ISubtarefa, 'id'> | null, o2: Pick<ISubtarefa, 'id'> | null): boolean {
    return o1 && o2 ? this.getSubtarefaIdentifier(o1) === this.getSubtarefaIdentifier(o2) : o1 === o2;
  }

  addSubtarefaToCollectionIfMissing<Type extends Pick<ISubtarefa, 'id'>>(
    subtarefaCollection: Type[],
    ...subtarefasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const subtarefas: Type[] = subtarefasToCheck.filter(isPresent);
    if (subtarefas.length > 0) {
      const subtarefaCollectionIdentifiers = subtarefaCollection.map(subtarefaItem => this.getSubtarefaIdentifier(subtarefaItem));
      const subtarefasToAdd = subtarefas.filter(subtarefaItem => {
        const subtarefaIdentifier = this.getSubtarefaIdentifier(subtarefaItem);
        if (subtarefaCollectionIdentifiers.includes(subtarefaIdentifier)) {
          return false;
        }
        subtarefaCollectionIdentifiers.push(subtarefaIdentifier);
        return true;
      });
      return [...subtarefasToAdd, ...subtarefaCollection];
    }
    return subtarefaCollection;
  }
}
