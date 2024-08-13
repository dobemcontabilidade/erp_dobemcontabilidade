import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuncionalidadeGrupoAcessoPadrao, NewFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';

export type PartialUpdateFuncionalidadeGrupoAcessoPadrao = Partial<IFuncionalidadeGrupoAcessoPadrao> &
  Pick<IFuncionalidadeGrupoAcessoPadrao, 'id'>;

type RestOf<T extends IFuncionalidadeGrupoAcessoPadrao | NewFuncionalidadeGrupoAcessoPadrao> = Omit<
  T,
  'dataExpiracao' | 'dataAtribuicao'
> & {
  dataExpiracao?: string | null;
  dataAtribuicao?: string | null;
};

export type RestFuncionalidadeGrupoAcessoPadrao = RestOf<IFuncionalidadeGrupoAcessoPadrao>;

export type NewRestFuncionalidadeGrupoAcessoPadrao = RestOf<NewFuncionalidadeGrupoAcessoPadrao>;

export type PartialUpdateRestFuncionalidadeGrupoAcessoPadrao = RestOf<PartialUpdateFuncionalidadeGrupoAcessoPadrao>;

export type EntityResponseType = HttpResponse<IFuncionalidadeGrupoAcessoPadrao>;
export type EntityArrayResponseType = HttpResponse<IFuncionalidadeGrupoAcessoPadrao[]>;

@Injectable({ providedIn: 'root' })
export class FuncionalidadeGrupoAcessoPadraoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/funcionalidade-grupo-acesso-padraos');

  create(funcionalidadeGrupoAcessoPadrao: NewFuncionalidadeGrupoAcessoPadrao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcionalidadeGrupoAcessoPadrao);
    return this.http
      .post<RestFuncionalidadeGrupoAcessoPadrao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcionalidadeGrupoAcessoPadrao);
    return this.http
      .put<RestFuncionalidadeGrupoAcessoPadrao>(
        `${this.resourceUrl}/${this.getFuncionalidadeGrupoAcessoPadraoIdentifier(funcionalidadeGrupoAcessoPadrao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(funcionalidadeGrupoAcessoPadrao: PartialUpdateFuncionalidadeGrupoAcessoPadrao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcionalidadeGrupoAcessoPadrao);
    return this.http
      .patch<RestFuncionalidadeGrupoAcessoPadrao>(
        `${this.resourceUrl}/${this.getFuncionalidadeGrupoAcessoPadraoIdentifier(funcionalidadeGrupoAcessoPadrao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFuncionalidadeGrupoAcessoPadrao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFuncionalidadeGrupoAcessoPadrao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuncionalidadeGrupoAcessoPadraoIdentifier(funcionalidadeGrupoAcessoPadrao: Pick<IFuncionalidadeGrupoAcessoPadrao, 'id'>): number {
    return funcionalidadeGrupoAcessoPadrao.id;
  }

  compareFuncionalidadeGrupoAcessoPadrao(
    o1: Pick<IFuncionalidadeGrupoAcessoPadrao, 'id'> | null,
    o2: Pick<IFuncionalidadeGrupoAcessoPadrao, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getFuncionalidadeGrupoAcessoPadraoIdentifier(o1) === this.getFuncionalidadeGrupoAcessoPadraoIdentifier(o2)
      : o1 === o2;
  }

  addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing<Type extends Pick<IFuncionalidadeGrupoAcessoPadrao, 'id'>>(
    funcionalidadeGrupoAcessoPadraoCollection: Type[],
    ...funcionalidadeGrupoAcessoPadraosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const funcionalidadeGrupoAcessoPadraos: Type[] = funcionalidadeGrupoAcessoPadraosToCheck.filter(isPresent);
    if (funcionalidadeGrupoAcessoPadraos.length > 0) {
      const funcionalidadeGrupoAcessoPadraoCollectionIdentifiers = funcionalidadeGrupoAcessoPadraoCollection.map(
        funcionalidadeGrupoAcessoPadraoItem => this.getFuncionalidadeGrupoAcessoPadraoIdentifier(funcionalidadeGrupoAcessoPadraoItem),
      );
      const funcionalidadeGrupoAcessoPadraosToAdd = funcionalidadeGrupoAcessoPadraos.filter(funcionalidadeGrupoAcessoPadraoItem => {
        const funcionalidadeGrupoAcessoPadraoIdentifier =
          this.getFuncionalidadeGrupoAcessoPadraoIdentifier(funcionalidadeGrupoAcessoPadraoItem);
        if (funcionalidadeGrupoAcessoPadraoCollectionIdentifiers.includes(funcionalidadeGrupoAcessoPadraoIdentifier)) {
          return false;
        }
        funcionalidadeGrupoAcessoPadraoCollectionIdentifiers.push(funcionalidadeGrupoAcessoPadraoIdentifier);
        return true;
      });
      return [...funcionalidadeGrupoAcessoPadraosToAdd, ...funcionalidadeGrupoAcessoPadraoCollection];
    }
    return funcionalidadeGrupoAcessoPadraoCollection;
  }

  protected convertDateFromClient<
    T extends IFuncionalidadeGrupoAcessoPadrao | NewFuncionalidadeGrupoAcessoPadrao | PartialUpdateFuncionalidadeGrupoAcessoPadrao,
  >(funcionalidadeGrupoAcessoPadrao: T): RestOf<T> {
    return {
      ...funcionalidadeGrupoAcessoPadrao,
      dataExpiracao: funcionalidadeGrupoAcessoPadrao.dataExpiracao?.toJSON() ?? null,
      dataAtribuicao: funcionalidadeGrupoAcessoPadrao.dataAtribuicao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restFuncionalidadeGrupoAcessoPadrao: RestFuncionalidadeGrupoAcessoPadrao,
  ): IFuncionalidadeGrupoAcessoPadrao {
    return {
      ...restFuncionalidadeGrupoAcessoPadrao,
      dataExpiracao: restFuncionalidadeGrupoAcessoPadrao.dataExpiracao
        ? dayjs(restFuncionalidadeGrupoAcessoPadrao.dataExpiracao)
        : undefined,
      dataAtribuicao: restFuncionalidadeGrupoAcessoPadrao.dataAtribuicao
        ? dayjs(restFuncionalidadeGrupoAcessoPadrao.dataAtribuicao)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestFuncionalidadeGrupoAcessoPadrao>,
  ): HttpResponse<IFuncionalidadeGrupoAcessoPadrao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestFuncionalidadeGrupoAcessoPadrao[]>,
  ): HttpResponse<IFuncionalidadeGrupoAcessoPadrao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
