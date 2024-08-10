import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProfissao, NewProfissao } from '../profissao.model';

export type PartialUpdateProfissao = Partial<IProfissao> & Pick<IProfissao, 'id'>;

export type EntityResponseType = HttpResponse<IProfissao>;
export type EntityArrayResponseType = HttpResponse<IProfissao[]>;

@Injectable({ providedIn: 'root' })
export class ProfissaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/profissaos');

  create(profissao: NewProfissao): Observable<EntityResponseType> {
    return this.http.post<IProfissao>(this.resourceUrl, profissao, { observe: 'response' });
  }

  update(profissao: IProfissao): Observable<EntityResponseType> {
    return this.http.put<IProfissao>(`${this.resourceUrl}/${this.getProfissaoIdentifier(profissao)}`, profissao, { observe: 'response' });
  }

  partialUpdate(profissao: PartialUpdateProfissao): Observable<EntityResponseType> {
    return this.http.patch<IProfissao>(`${this.resourceUrl}/${this.getProfissaoIdentifier(profissao)}`, profissao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfissao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfissao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProfissaoIdentifier(profissao: Pick<IProfissao, 'id'>): number {
    return profissao.id;
  }

  compareProfissao(o1: Pick<IProfissao, 'id'> | null, o2: Pick<IProfissao, 'id'> | null): boolean {
    return o1 && o2 ? this.getProfissaoIdentifier(o1) === this.getProfissaoIdentifier(o2) : o1 === o2;
  }

  addProfissaoToCollectionIfMissing<Type extends Pick<IProfissao, 'id'>>(
    profissaoCollection: Type[],
    ...profissaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const profissaos: Type[] = profissaosToCheck.filter(isPresent);
    if (profissaos.length > 0) {
      const profissaoCollectionIdentifiers = profissaoCollection.map(profissaoItem => this.getProfissaoIdentifier(profissaoItem));
      const profissaosToAdd = profissaos.filter(profissaoItem => {
        const profissaoIdentifier = this.getProfissaoIdentifier(profissaoItem);
        if (profissaoCollectionIdentifiers.includes(profissaoIdentifier)) {
          return false;
        }
        profissaoCollectionIdentifiers.push(profissaoIdentifier);
        return true;
      });
      return [...profissaosToAdd, ...profissaoCollection];
    }
    return profissaoCollection;
  }
}
