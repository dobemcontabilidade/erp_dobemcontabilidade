import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentoTarefa, NewDocumentoTarefa } from '../documento-tarefa.model';

export type PartialUpdateDocumentoTarefa = Partial<IDocumentoTarefa> & Pick<IDocumentoTarefa, 'id'>;

export type EntityResponseType = HttpResponse<IDocumentoTarefa>;
export type EntityArrayResponseType = HttpResponse<IDocumentoTarefa[]>;

@Injectable({ providedIn: 'root' })
export class DocumentoTarefaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/documento-tarefas');

  create(documentoTarefa: NewDocumentoTarefa): Observable<EntityResponseType> {
    return this.http.post<IDocumentoTarefa>(this.resourceUrl, documentoTarefa, { observe: 'response' });
  }

  update(documentoTarefa: IDocumentoTarefa): Observable<EntityResponseType> {
    return this.http.put<IDocumentoTarefa>(`${this.resourceUrl}/${this.getDocumentoTarefaIdentifier(documentoTarefa)}`, documentoTarefa, {
      observe: 'response',
    });
  }

  partialUpdate(documentoTarefa: PartialUpdateDocumentoTarefa): Observable<EntityResponseType> {
    return this.http.patch<IDocumentoTarefa>(`${this.resourceUrl}/${this.getDocumentoTarefaIdentifier(documentoTarefa)}`, documentoTarefa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocumentoTarefa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumentoTarefa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocumentoTarefaIdentifier(documentoTarefa: Pick<IDocumentoTarefa, 'id'>): number {
    return documentoTarefa.id;
  }

  compareDocumentoTarefa(o1: Pick<IDocumentoTarefa, 'id'> | null, o2: Pick<IDocumentoTarefa, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocumentoTarefaIdentifier(o1) === this.getDocumentoTarefaIdentifier(o2) : o1 === o2;
  }

  addDocumentoTarefaToCollectionIfMissing<Type extends Pick<IDocumentoTarefa, 'id'>>(
    documentoTarefaCollection: Type[],
    ...documentoTarefasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const documentoTarefas: Type[] = documentoTarefasToCheck.filter(isPresent);
    if (documentoTarefas.length > 0) {
      const documentoTarefaCollectionIdentifiers = documentoTarefaCollection.map(documentoTarefaItem =>
        this.getDocumentoTarefaIdentifier(documentoTarefaItem),
      );
      const documentoTarefasToAdd = documentoTarefas.filter(documentoTarefaItem => {
        const documentoTarefaIdentifier = this.getDocumentoTarefaIdentifier(documentoTarefaItem);
        if (documentoTarefaCollectionIdentifiers.includes(documentoTarefaIdentifier)) {
          return false;
        }
        documentoTarefaCollectionIdentifiers.push(documentoTarefaIdentifier);
        return true;
      });
      return [...documentoTarefasToAdd, ...documentoTarefaCollection];
    }
    return documentoTarefaCollection;
  }
}
