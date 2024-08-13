import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoTarefaRecorrenteExecucao, NewAnexoTarefaRecorrenteExecucao } from '../anexo-tarefa-recorrente-execucao.model';

export type PartialUpdateAnexoTarefaRecorrenteExecucao = Partial<IAnexoTarefaRecorrenteExecucao> &
  Pick<IAnexoTarefaRecorrenteExecucao, 'id'>;

type RestOf<T extends IAnexoTarefaRecorrenteExecucao | NewAnexoTarefaRecorrenteExecucao> = Omit<T, 'dataHoraUpload'> & {
  dataHoraUpload?: string | null;
};

export type RestAnexoTarefaRecorrenteExecucao = RestOf<IAnexoTarefaRecorrenteExecucao>;

export type NewRestAnexoTarefaRecorrenteExecucao = RestOf<NewAnexoTarefaRecorrenteExecucao>;

export type PartialUpdateRestAnexoTarefaRecorrenteExecucao = RestOf<PartialUpdateAnexoTarefaRecorrenteExecucao>;

export type EntityResponseType = HttpResponse<IAnexoTarefaRecorrenteExecucao>;
export type EntityArrayResponseType = HttpResponse<IAnexoTarefaRecorrenteExecucao[]>;

@Injectable({ providedIn: 'root' })
export class AnexoTarefaRecorrenteExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-tarefa-recorrente-execucaos');

  create(anexoTarefaRecorrenteExecucao: NewAnexoTarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoTarefaRecorrenteExecucao);
    return this.http
      .post<RestAnexoTarefaRecorrenteExecucao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoTarefaRecorrenteExecucao);
    return this.http
      .put<RestAnexoTarefaRecorrenteExecucao>(
        `${this.resourceUrl}/${this.getAnexoTarefaRecorrenteExecucaoIdentifier(anexoTarefaRecorrenteExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(anexoTarefaRecorrenteExecucao: PartialUpdateAnexoTarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoTarefaRecorrenteExecucao);
    return this.http
      .patch<RestAnexoTarefaRecorrenteExecucao>(
        `${this.resourceUrl}/${this.getAnexoTarefaRecorrenteExecucaoIdentifier(anexoTarefaRecorrenteExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAnexoTarefaRecorrenteExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAnexoTarefaRecorrenteExecucao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoTarefaRecorrenteExecucaoIdentifier(anexoTarefaRecorrenteExecucao: Pick<IAnexoTarefaRecorrenteExecucao, 'id'>): number {
    return anexoTarefaRecorrenteExecucao.id;
  }

  compareAnexoTarefaRecorrenteExecucao(
    o1: Pick<IAnexoTarefaRecorrenteExecucao, 'id'> | null,
    o2: Pick<IAnexoTarefaRecorrenteExecucao, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAnexoTarefaRecorrenteExecucaoIdentifier(o1) === this.getAnexoTarefaRecorrenteExecucaoIdentifier(o2)
      : o1 === o2;
  }

  addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing<Type extends Pick<IAnexoTarefaRecorrenteExecucao, 'id'>>(
    anexoTarefaRecorrenteExecucaoCollection: Type[],
    ...anexoTarefaRecorrenteExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoTarefaRecorrenteExecucaos: Type[] = anexoTarefaRecorrenteExecucaosToCheck.filter(isPresent);
    if (anexoTarefaRecorrenteExecucaos.length > 0) {
      const anexoTarefaRecorrenteExecucaoCollectionIdentifiers = anexoTarefaRecorrenteExecucaoCollection.map(
        anexoTarefaRecorrenteExecucaoItem => this.getAnexoTarefaRecorrenteExecucaoIdentifier(anexoTarefaRecorrenteExecucaoItem),
      );
      const anexoTarefaRecorrenteExecucaosToAdd = anexoTarefaRecorrenteExecucaos.filter(anexoTarefaRecorrenteExecucaoItem => {
        const anexoTarefaRecorrenteExecucaoIdentifier = this.getAnexoTarefaRecorrenteExecucaoIdentifier(anexoTarefaRecorrenteExecucaoItem);
        if (anexoTarefaRecorrenteExecucaoCollectionIdentifiers.includes(anexoTarefaRecorrenteExecucaoIdentifier)) {
          return false;
        }
        anexoTarefaRecorrenteExecucaoCollectionIdentifiers.push(anexoTarefaRecorrenteExecucaoIdentifier);
        return true;
      });
      return [...anexoTarefaRecorrenteExecucaosToAdd, ...anexoTarefaRecorrenteExecucaoCollection];
    }
    return anexoTarefaRecorrenteExecucaoCollection;
  }

  protected convertDateFromClient<
    T extends IAnexoTarefaRecorrenteExecucao | NewAnexoTarefaRecorrenteExecucao | PartialUpdateAnexoTarefaRecorrenteExecucao,
  >(anexoTarefaRecorrenteExecucao: T): RestOf<T> {
    return {
      ...anexoTarefaRecorrenteExecucao,
      dataHoraUpload: anexoTarefaRecorrenteExecucao.dataHoraUpload?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAnexoTarefaRecorrenteExecucao: RestAnexoTarefaRecorrenteExecucao): IAnexoTarefaRecorrenteExecucao {
    return {
      ...restAnexoTarefaRecorrenteExecucao,
      dataHoraUpload: restAnexoTarefaRecorrenteExecucao.dataHoraUpload
        ? dayjs(restAnexoTarefaRecorrenteExecucao.dataHoraUpload)
        : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAnexoTarefaRecorrenteExecucao>): HttpResponse<IAnexoTarefaRecorrenteExecucao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAnexoTarefaRecorrenteExecucao[]>,
  ): HttpResponse<IAnexoTarefaRecorrenteExecucao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
