import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoRequeridoTarefaRecorrente, NewAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';

export type PartialUpdateAnexoRequeridoTarefaRecorrente = Partial<IAnexoRequeridoTarefaRecorrente> &
  Pick<IAnexoRequeridoTarefaRecorrente, 'id'>;

export type EntityResponseType = HttpResponse<IAnexoRequeridoTarefaRecorrente>;
export type EntityArrayResponseType = HttpResponse<IAnexoRequeridoTarefaRecorrente[]>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoTarefaRecorrenteService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-requerido-tarefa-recorrentes');

  create(anexoRequeridoTarefaRecorrente: NewAnexoRequeridoTarefaRecorrente): Observable<EntityResponseType> {
    return this.http.post<IAnexoRequeridoTarefaRecorrente>(this.resourceUrl, anexoRequeridoTarefaRecorrente, { observe: 'response' });
  }

  update(anexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente): Observable<EntityResponseType> {
    return this.http.put<IAnexoRequeridoTarefaRecorrente>(
      `${this.resourceUrl}/${this.getAnexoRequeridoTarefaRecorrenteIdentifier(anexoRequeridoTarefaRecorrente)}`,
      anexoRequeridoTarefaRecorrente,
      { observe: 'response' },
    );
  }

  partialUpdate(anexoRequeridoTarefaRecorrente: PartialUpdateAnexoRequeridoTarefaRecorrente): Observable<EntityResponseType> {
    return this.http.patch<IAnexoRequeridoTarefaRecorrente>(
      `${this.resourceUrl}/${this.getAnexoRequeridoTarefaRecorrenteIdentifier(anexoRequeridoTarefaRecorrente)}`,
      anexoRequeridoTarefaRecorrente,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexoRequeridoTarefaRecorrente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexoRequeridoTarefaRecorrente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoRequeridoTarefaRecorrenteIdentifier(anexoRequeridoTarefaRecorrente: Pick<IAnexoRequeridoTarefaRecorrente, 'id'>): number {
    return anexoRequeridoTarefaRecorrente.id;
  }

  compareAnexoRequeridoTarefaRecorrente(
    o1: Pick<IAnexoRequeridoTarefaRecorrente, 'id'> | null,
    o2: Pick<IAnexoRequeridoTarefaRecorrente, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAnexoRequeridoTarefaRecorrenteIdentifier(o1) === this.getAnexoRequeridoTarefaRecorrenteIdentifier(o2)
      : o1 === o2;
  }

  addAnexoRequeridoTarefaRecorrenteToCollectionIfMissing<Type extends Pick<IAnexoRequeridoTarefaRecorrente, 'id'>>(
    anexoRequeridoTarefaRecorrenteCollection: Type[],
    ...anexoRequeridoTarefaRecorrentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoRequeridoTarefaRecorrentes: Type[] = anexoRequeridoTarefaRecorrentesToCheck.filter(isPresent);
    if (anexoRequeridoTarefaRecorrentes.length > 0) {
      const anexoRequeridoTarefaRecorrenteCollectionIdentifiers = anexoRequeridoTarefaRecorrenteCollection.map(
        anexoRequeridoTarefaRecorrenteItem => this.getAnexoRequeridoTarefaRecorrenteIdentifier(anexoRequeridoTarefaRecorrenteItem),
      );
      const anexoRequeridoTarefaRecorrentesToAdd = anexoRequeridoTarefaRecorrentes.filter(anexoRequeridoTarefaRecorrenteItem => {
        const anexoRequeridoTarefaRecorrenteIdentifier =
          this.getAnexoRequeridoTarefaRecorrenteIdentifier(anexoRequeridoTarefaRecorrenteItem);
        if (anexoRequeridoTarefaRecorrenteCollectionIdentifiers.includes(anexoRequeridoTarefaRecorrenteIdentifier)) {
          return false;
        }
        anexoRequeridoTarefaRecorrenteCollectionIdentifiers.push(anexoRequeridoTarefaRecorrenteIdentifier);
        return true;
      });
      return [...anexoRequeridoTarefaRecorrentesToAdd, ...anexoRequeridoTarefaRecorrenteCollection];
    }
    return anexoRequeridoTarefaRecorrenteCollection;
  }
}
