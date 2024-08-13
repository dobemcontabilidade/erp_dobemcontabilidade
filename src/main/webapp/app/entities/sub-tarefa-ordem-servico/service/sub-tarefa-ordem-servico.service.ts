import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubTarefaOrdemServico, NewSubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';

export type PartialUpdateSubTarefaOrdemServico = Partial<ISubTarefaOrdemServico> & Pick<ISubTarefaOrdemServico, 'id'>;

export type EntityResponseType = HttpResponse<ISubTarefaOrdemServico>;
export type EntityArrayResponseType = HttpResponse<ISubTarefaOrdemServico[]>;

@Injectable({ providedIn: 'root' })
export class SubTarefaOrdemServicoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sub-tarefa-ordem-servicos');

  create(subTarefaOrdemServico: NewSubTarefaOrdemServico): Observable<EntityResponseType> {
    return this.http.post<ISubTarefaOrdemServico>(this.resourceUrl, subTarefaOrdemServico, { observe: 'response' });
  }

  update(subTarefaOrdemServico: ISubTarefaOrdemServico): Observable<EntityResponseType> {
    return this.http.put<ISubTarefaOrdemServico>(
      `${this.resourceUrl}/${this.getSubTarefaOrdemServicoIdentifier(subTarefaOrdemServico)}`,
      subTarefaOrdemServico,
      { observe: 'response' },
    );
  }

  partialUpdate(subTarefaOrdemServico: PartialUpdateSubTarefaOrdemServico): Observable<EntityResponseType> {
    return this.http.patch<ISubTarefaOrdemServico>(
      `${this.resourceUrl}/${this.getSubTarefaOrdemServicoIdentifier(subTarefaOrdemServico)}`,
      subTarefaOrdemServico,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubTarefaOrdemServico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubTarefaOrdemServico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSubTarefaOrdemServicoIdentifier(subTarefaOrdemServico: Pick<ISubTarefaOrdemServico, 'id'>): number {
    return subTarefaOrdemServico.id;
  }

  compareSubTarefaOrdemServico(o1: Pick<ISubTarefaOrdemServico, 'id'> | null, o2: Pick<ISubTarefaOrdemServico, 'id'> | null): boolean {
    return o1 && o2 ? this.getSubTarefaOrdemServicoIdentifier(o1) === this.getSubTarefaOrdemServicoIdentifier(o2) : o1 === o2;
  }

  addSubTarefaOrdemServicoToCollectionIfMissing<Type extends Pick<ISubTarefaOrdemServico, 'id'>>(
    subTarefaOrdemServicoCollection: Type[],
    ...subTarefaOrdemServicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const subTarefaOrdemServicos: Type[] = subTarefaOrdemServicosToCheck.filter(isPresent);
    if (subTarefaOrdemServicos.length > 0) {
      const subTarefaOrdemServicoCollectionIdentifiers = subTarefaOrdemServicoCollection.map(subTarefaOrdemServicoItem =>
        this.getSubTarefaOrdemServicoIdentifier(subTarefaOrdemServicoItem),
      );
      const subTarefaOrdemServicosToAdd = subTarefaOrdemServicos.filter(subTarefaOrdemServicoItem => {
        const subTarefaOrdemServicoIdentifier = this.getSubTarefaOrdemServicoIdentifier(subTarefaOrdemServicoItem);
        if (subTarefaOrdemServicoCollectionIdentifiers.includes(subTarefaOrdemServicoIdentifier)) {
          return false;
        }
        subTarefaOrdemServicoCollectionIdentifiers.push(subTarefaOrdemServicoIdentifier);
        return true;
      });
      return [...subTarefaOrdemServicosToAdd, ...subTarefaOrdemServicoCollection];
    }
    return subTarefaOrdemServicoCollection;
  }
}
