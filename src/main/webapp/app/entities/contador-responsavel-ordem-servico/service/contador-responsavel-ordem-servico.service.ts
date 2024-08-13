import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContadorResponsavelOrdemServico, NewContadorResponsavelOrdemServico } from '../contador-responsavel-ordem-servico.model';

export type PartialUpdateContadorResponsavelOrdemServico = Partial<IContadorResponsavelOrdemServico> &
  Pick<IContadorResponsavelOrdemServico, 'id'>;

type RestOf<T extends IContadorResponsavelOrdemServico | NewContadorResponsavelOrdemServico> = Omit<
  T,
  'dataAtribuicao' | 'dataRevogacao'
> & {
  dataAtribuicao?: string | null;
  dataRevogacao?: string | null;
};

export type RestContadorResponsavelOrdemServico = RestOf<IContadorResponsavelOrdemServico>;

export type NewRestContadorResponsavelOrdemServico = RestOf<NewContadorResponsavelOrdemServico>;

export type PartialUpdateRestContadorResponsavelOrdemServico = RestOf<PartialUpdateContadorResponsavelOrdemServico>;

export type EntityResponseType = HttpResponse<IContadorResponsavelOrdemServico>;
export type EntityArrayResponseType = HttpResponse<IContadorResponsavelOrdemServico[]>;

@Injectable({ providedIn: 'root' })
export class ContadorResponsavelOrdemServicoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contador-responsavel-ordem-servicos');

  create(contadorResponsavelOrdemServico: NewContadorResponsavelOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contadorResponsavelOrdemServico);
    return this.http
      .post<RestContadorResponsavelOrdemServico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contadorResponsavelOrdemServico);
    return this.http
      .put<RestContadorResponsavelOrdemServico>(
        `${this.resourceUrl}/${this.getContadorResponsavelOrdemServicoIdentifier(contadorResponsavelOrdemServico)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(contadorResponsavelOrdemServico: PartialUpdateContadorResponsavelOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contadorResponsavelOrdemServico);
    return this.http
      .patch<RestContadorResponsavelOrdemServico>(
        `${this.resourceUrl}/${this.getContadorResponsavelOrdemServicoIdentifier(contadorResponsavelOrdemServico)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestContadorResponsavelOrdemServico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestContadorResponsavelOrdemServico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContadorResponsavelOrdemServicoIdentifier(contadorResponsavelOrdemServico: Pick<IContadorResponsavelOrdemServico, 'id'>): number {
    return contadorResponsavelOrdemServico.id;
  }

  compareContadorResponsavelOrdemServico(
    o1: Pick<IContadorResponsavelOrdemServico, 'id'> | null,
    o2: Pick<IContadorResponsavelOrdemServico, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getContadorResponsavelOrdemServicoIdentifier(o1) === this.getContadorResponsavelOrdemServicoIdentifier(o2)
      : o1 === o2;
  }

  addContadorResponsavelOrdemServicoToCollectionIfMissing<Type extends Pick<IContadorResponsavelOrdemServico, 'id'>>(
    contadorResponsavelOrdemServicoCollection: Type[],
    ...contadorResponsavelOrdemServicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contadorResponsavelOrdemServicos: Type[] = contadorResponsavelOrdemServicosToCheck.filter(isPresent);
    if (contadorResponsavelOrdemServicos.length > 0) {
      const contadorResponsavelOrdemServicoCollectionIdentifiers = contadorResponsavelOrdemServicoCollection.map(
        contadorResponsavelOrdemServicoItem => this.getContadorResponsavelOrdemServicoIdentifier(contadorResponsavelOrdemServicoItem),
      );
      const contadorResponsavelOrdemServicosToAdd = contadorResponsavelOrdemServicos.filter(contadorResponsavelOrdemServicoItem => {
        const contadorResponsavelOrdemServicoIdentifier =
          this.getContadorResponsavelOrdemServicoIdentifier(contadorResponsavelOrdemServicoItem);
        if (contadorResponsavelOrdemServicoCollectionIdentifiers.includes(contadorResponsavelOrdemServicoIdentifier)) {
          return false;
        }
        contadorResponsavelOrdemServicoCollectionIdentifiers.push(contadorResponsavelOrdemServicoIdentifier);
        return true;
      });
      return [...contadorResponsavelOrdemServicosToAdd, ...contadorResponsavelOrdemServicoCollection];
    }
    return contadorResponsavelOrdemServicoCollection;
  }

  protected convertDateFromClient<
    T extends IContadorResponsavelOrdemServico | NewContadorResponsavelOrdemServico | PartialUpdateContadorResponsavelOrdemServico,
  >(contadorResponsavelOrdemServico: T): RestOf<T> {
    return {
      ...contadorResponsavelOrdemServico,
      dataAtribuicao: contadorResponsavelOrdemServico.dataAtribuicao?.toJSON() ?? null,
      dataRevogacao: contadorResponsavelOrdemServico.dataRevogacao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restContadorResponsavelOrdemServico: RestContadorResponsavelOrdemServico,
  ): IContadorResponsavelOrdemServico {
    return {
      ...restContadorResponsavelOrdemServico,
      dataAtribuicao: restContadorResponsavelOrdemServico.dataAtribuicao
        ? dayjs(restContadorResponsavelOrdemServico.dataAtribuicao)
        : undefined,
      dataRevogacao: restContadorResponsavelOrdemServico.dataRevogacao
        ? dayjs(restContadorResponsavelOrdemServico.dataRevogacao)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestContadorResponsavelOrdemServico>,
  ): HttpResponse<IContadorResponsavelOrdemServico> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestContadorResponsavelOrdemServico[]>,
  ): HttpResponse<IContadorResponsavelOrdemServico[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
