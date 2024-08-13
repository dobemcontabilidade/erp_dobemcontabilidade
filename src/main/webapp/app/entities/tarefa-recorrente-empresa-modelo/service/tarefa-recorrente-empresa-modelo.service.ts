import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarefaRecorrenteEmpresaModelo, NewTarefaRecorrenteEmpresaModelo } from '../tarefa-recorrente-empresa-modelo.model';

export type PartialUpdateTarefaRecorrenteEmpresaModelo = Partial<ITarefaRecorrenteEmpresaModelo> &
  Pick<ITarefaRecorrenteEmpresaModelo, 'id'>;

export type EntityResponseType = HttpResponse<ITarefaRecorrenteEmpresaModelo>;
export type EntityArrayResponseType = HttpResponse<ITarefaRecorrenteEmpresaModelo[]>;

@Injectable({ providedIn: 'root' })
export class TarefaRecorrenteEmpresaModeloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tarefa-recorrente-empresa-modelos');

  create(tarefaRecorrenteEmpresaModelo: NewTarefaRecorrenteEmpresaModelo): Observable<EntityResponseType> {
    return this.http.post<ITarefaRecorrenteEmpresaModelo>(this.resourceUrl, tarefaRecorrenteEmpresaModelo, { observe: 'response' });
  }

  update(tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo): Observable<EntityResponseType> {
    return this.http.put<ITarefaRecorrenteEmpresaModelo>(
      `${this.resourceUrl}/${this.getTarefaRecorrenteEmpresaModeloIdentifier(tarefaRecorrenteEmpresaModelo)}`,
      tarefaRecorrenteEmpresaModelo,
      { observe: 'response' },
    );
  }

  partialUpdate(tarefaRecorrenteEmpresaModelo: PartialUpdateTarefaRecorrenteEmpresaModelo): Observable<EntityResponseType> {
    return this.http.patch<ITarefaRecorrenteEmpresaModelo>(
      `${this.resourceUrl}/${this.getTarefaRecorrenteEmpresaModeloIdentifier(tarefaRecorrenteEmpresaModelo)}`,
      tarefaRecorrenteEmpresaModelo,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarefaRecorrenteEmpresaModelo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarefaRecorrenteEmpresaModelo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTarefaRecorrenteEmpresaModeloIdentifier(tarefaRecorrenteEmpresaModelo: Pick<ITarefaRecorrenteEmpresaModelo, 'id'>): number {
    return tarefaRecorrenteEmpresaModelo.id;
  }

  compareTarefaRecorrenteEmpresaModelo(
    o1: Pick<ITarefaRecorrenteEmpresaModelo, 'id'> | null,
    o2: Pick<ITarefaRecorrenteEmpresaModelo, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getTarefaRecorrenteEmpresaModeloIdentifier(o1) === this.getTarefaRecorrenteEmpresaModeloIdentifier(o2)
      : o1 === o2;
  }

  addTarefaRecorrenteEmpresaModeloToCollectionIfMissing<Type extends Pick<ITarefaRecorrenteEmpresaModelo, 'id'>>(
    tarefaRecorrenteEmpresaModeloCollection: Type[],
    ...tarefaRecorrenteEmpresaModelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tarefaRecorrenteEmpresaModelos: Type[] = tarefaRecorrenteEmpresaModelosToCheck.filter(isPresent);
    if (tarefaRecorrenteEmpresaModelos.length > 0) {
      const tarefaRecorrenteEmpresaModeloCollectionIdentifiers = tarefaRecorrenteEmpresaModeloCollection.map(
        tarefaRecorrenteEmpresaModeloItem => this.getTarefaRecorrenteEmpresaModeloIdentifier(tarefaRecorrenteEmpresaModeloItem),
      );
      const tarefaRecorrenteEmpresaModelosToAdd = tarefaRecorrenteEmpresaModelos.filter(tarefaRecorrenteEmpresaModeloItem => {
        const tarefaRecorrenteEmpresaModeloIdentifier = this.getTarefaRecorrenteEmpresaModeloIdentifier(tarefaRecorrenteEmpresaModeloItem);
        if (tarefaRecorrenteEmpresaModeloCollectionIdentifiers.includes(tarefaRecorrenteEmpresaModeloIdentifier)) {
          return false;
        }
        tarefaRecorrenteEmpresaModeloCollectionIdentifiers.push(tarefaRecorrenteEmpresaModeloIdentifier);
        return true;
      });
      return [...tarefaRecorrenteEmpresaModelosToAdd, ...tarefaRecorrenteEmpresaModeloCollection];
    }
    return tarefaRecorrenteEmpresaModeloCollection;
  }
}
