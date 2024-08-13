import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoRequeridoTarefaOrdemServico, NewAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';

export type PartialUpdateAnexoRequeridoTarefaOrdemServico = Partial<IAnexoRequeridoTarefaOrdemServico> &
  Pick<IAnexoRequeridoTarefaOrdemServico, 'id'>;

export type EntityResponseType = HttpResponse<IAnexoRequeridoTarefaOrdemServico>;
export type EntityArrayResponseType = HttpResponse<IAnexoRequeridoTarefaOrdemServico[]>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoTarefaOrdemServicoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-requerido-tarefa-ordem-servicos');

  create(anexoRequeridoTarefaOrdemServico: NewAnexoRequeridoTarefaOrdemServico): Observable<EntityResponseType> {
    return this.http.post<IAnexoRequeridoTarefaOrdemServico>(this.resourceUrl, anexoRequeridoTarefaOrdemServico, { observe: 'response' });
  }

  update(anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico): Observable<EntityResponseType> {
    return this.http.put<IAnexoRequeridoTarefaOrdemServico>(
      `${this.resourceUrl}/${this.getAnexoRequeridoTarefaOrdemServicoIdentifier(anexoRequeridoTarefaOrdemServico)}`,
      anexoRequeridoTarefaOrdemServico,
      { observe: 'response' },
    );
  }

  partialUpdate(anexoRequeridoTarefaOrdemServico: PartialUpdateAnexoRequeridoTarefaOrdemServico): Observable<EntityResponseType> {
    return this.http.patch<IAnexoRequeridoTarefaOrdemServico>(
      `${this.resourceUrl}/${this.getAnexoRequeridoTarefaOrdemServicoIdentifier(anexoRequeridoTarefaOrdemServico)}`,
      anexoRequeridoTarefaOrdemServico,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexoRequeridoTarefaOrdemServico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexoRequeridoTarefaOrdemServico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoRequeridoTarefaOrdemServicoIdentifier(anexoRequeridoTarefaOrdemServico: Pick<IAnexoRequeridoTarefaOrdemServico, 'id'>): number {
    return anexoRequeridoTarefaOrdemServico.id;
  }

  compareAnexoRequeridoTarefaOrdemServico(
    o1: Pick<IAnexoRequeridoTarefaOrdemServico, 'id'> | null,
    o2: Pick<IAnexoRequeridoTarefaOrdemServico, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAnexoRequeridoTarefaOrdemServicoIdentifier(o1) === this.getAnexoRequeridoTarefaOrdemServicoIdentifier(o2)
      : o1 === o2;
  }

  addAnexoRequeridoTarefaOrdemServicoToCollectionIfMissing<Type extends Pick<IAnexoRequeridoTarefaOrdemServico, 'id'>>(
    anexoRequeridoTarefaOrdemServicoCollection: Type[],
    ...anexoRequeridoTarefaOrdemServicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoRequeridoTarefaOrdemServicos: Type[] = anexoRequeridoTarefaOrdemServicosToCheck.filter(isPresent);
    if (anexoRequeridoTarefaOrdemServicos.length > 0) {
      const anexoRequeridoTarefaOrdemServicoCollectionIdentifiers = anexoRequeridoTarefaOrdemServicoCollection.map(
        anexoRequeridoTarefaOrdemServicoItem => this.getAnexoRequeridoTarefaOrdemServicoIdentifier(anexoRequeridoTarefaOrdemServicoItem),
      );
      const anexoRequeridoTarefaOrdemServicosToAdd = anexoRequeridoTarefaOrdemServicos.filter(anexoRequeridoTarefaOrdemServicoItem => {
        const anexoRequeridoTarefaOrdemServicoIdentifier = this.getAnexoRequeridoTarefaOrdemServicoIdentifier(
          anexoRequeridoTarefaOrdemServicoItem,
        );
        if (anexoRequeridoTarefaOrdemServicoCollectionIdentifiers.includes(anexoRequeridoTarefaOrdemServicoIdentifier)) {
          return false;
        }
        anexoRequeridoTarefaOrdemServicoCollectionIdentifiers.push(anexoRequeridoTarefaOrdemServicoIdentifier);
        return true;
      });
      return [...anexoRequeridoTarefaOrdemServicosToAdd, ...anexoRequeridoTarefaOrdemServicoCollection];
    }
    return anexoRequeridoTarefaOrdemServicoCollection;
  }
}
