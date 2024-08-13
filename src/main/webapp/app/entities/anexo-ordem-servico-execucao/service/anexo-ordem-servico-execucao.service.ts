import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoOrdemServicoExecucao, NewAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';

export type PartialUpdateAnexoOrdemServicoExecucao = Partial<IAnexoOrdemServicoExecucao> & Pick<IAnexoOrdemServicoExecucao, 'id'>;

type RestOf<T extends IAnexoOrdemServicoExecucao | NewAnexoOrdemServicoExecucao> = Omit<T, 'dataHoraUpload'> & {
  dataHoraUpload?: string | null;
};

export type RestAnexoOrdemServicoExecucao = RestOf<IAnexoOrdemServicoExecucao>;

export type NewRestAnexoOrdemServicoExecucao = RestOf<NewAnexoOrdemServicoExecucao>;

export type PartialUpdateRestAnexoOrdemServicoExecucao = RestOf<PartialUpdateAnexoOrdemServicoExecucao>;

export type EntityResponseType = HttpResponse<IAnexoOrdemServicoExecucao>;
export type EntityArrayResponseType = HttpResponse<IAnexoOrdemServicoExecucao[]>;

@Injectable({ providedIn: 'root' })
export class AnexoOrdemServicoExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-ordem-servico-execucaos');

  create(anexoOrdemServicoExecucao: NewAnexoOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoOrdemServicoExecucao);
    return this.http
      .post<RestAnexoOrdemServicoExecucao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoOrdemServicoExecucao);
    return this.http
      .put<RestAnexoOrdemServicoExecucao>(
        `${this.resourceUrl}/${this.getAnexoOrdemServicoExecucaoIdentifier(anexoOrdemServicoExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(anexoOrdemServicoExecucao: PartialUpdateAnexoOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoOrdemServicoExecucao);
    return this.http
      .patch<RestAnexoOrdemServicoExecucao>(
        `${this.resourceUrl}/${this.getAnexoOrdemServicoExecucaoIdentifier(anexoOrdemServicoExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAnexoOrdemServicoExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAnexoOrdemServicoExecucao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoOrdemServicoExecucaoIdentifier(anexoOrdemServicoExecucao: Pick<IAnexoOrdemServicoExecucao, 'id'>): number {
    return anexoOrdemServicoExecucao.id;
  }

  compareAnexoOrdemServicoExecucao(
    o1: Pick<IAnexoOrdemServicoExecucao, 'id'> | null,
    o2: Pick<IAnexoOrdemServicoExecucao, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getAnexoOrdemServicoExecucaoIdentifier(o1) === this.getAnexoOrdemServicoExecucaoIdentifier(o2) : o1 === o2;
  }

  addAnexoOrdemServicoExecucaoToCollectionIfMissing<Type extends Pick<IAnexoOrdemServicoExecucao, 'id'>>(
    anexoOrdemServicoExecucaoCollection: Type[],
    ...anexoOrdemServicoExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoOrdemServicoExecucaos: Type[] = anexoOrdemServicoExecucaosToCheck.filter(isPresent);
    if (anexoOrdemServicoExecucaos.length > 0) {
      const anexoOrdemServicoExecucaoCollectionIdentifiers = anexoOrdemServicoExecucaoCollection.map(anexoOrdemServicoExecucaoItem =>
        this.getAnexoOrdemServicoExecucaoIdentifier(anexoOrdemServicoExecucaoItem),
      );
      const anexoOrdemServicoExecucaosToAdd = anexoOrdemServicoExecucaos.filter(anexoOrdemServicoExecucaoItem => {
        const anexoOrdemServicoExecucaoIdentifier = this.getAnexoOrdemServicoExecucaoIdentifier(anexoOrdemServicoExecucaoItem);
        if (anexoOrdemServicoExecucaoCollectionIdentifiers.includes(anexoOrdemServicoExecucaoIdentifier)) {
          return false;
        }
        anexoOrdemServicoExecucaoCollectionIdentifiers.push(anexoOrdemServicoExecucaoIdentifier);
        return true;
      });
      return [...anexoOrdemServicoExecucaosToAdd, ...anexoOrdemServicoExecucaoCollection];
    }
    return anexoOrdemServicoExecucaoCollection;
  }

  protected convertDateFromClient<
    T extends IAnexoOrdemServicoExecucao | NewAnexoOrdemServicoExecucao | PartialUpdateAnexoOrdemServicoExecucao,
  >(anexoOrdemServicoExecucao: T): RestOf<T> {
    return {
      ...anexoOrdemServicoExecucao,
      dataHoraUpload: anexoOrdemServicoExecucao.dataHoraUpload?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAnexoOrdemServicoExecucao: RestAnexoOrdemServicoExecucao): IAnexoOrdemServicoExecucao {
    return {
      ...restAnexoOrdemServicoExecucao,
      dataHoraUpload: restAnexoOrdemServicoExecucao.dataHoraUpload ? dayjs(restAnexoOrdemServicoExecucao.dataHoraUpload) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAnexoOrdemServicoExecucao>): HttpResponse<IAnexoOrdemServicoExecucao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAnexoOrdemServicoExecucao[]>): HttpResponse<IAnexoOrdemServicoExecucao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
