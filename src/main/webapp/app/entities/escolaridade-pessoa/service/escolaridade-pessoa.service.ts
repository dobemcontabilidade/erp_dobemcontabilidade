import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEscolaridadePessoa, NewEscolaridadePessoa } from '../escolaridade-pessoa.model';

export type PartialUpdateEscolaridadePessoa = Partial<IEscolaridadePessoa> & Pick<IEscolaridadePessoa, 'id'>;

export type EntityResponseType = HttpResponse<IEscolaridadePessoa>;
export type EntityArrayResponseType = HttpResponse<IEscolaridadePessoa[]>;

@Injectable({ providedIn: 'root' })
export class EscolaridadePessoaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/escolaridade-pessoas');

  create(escolaridadePessoa: NewEscolaridadePessoa): Observable<EntityResponseType> {
    return this.http.post<IEscolaridadePessoa>(this.resourceUrl, escolaridadePessoa, { observe: 'response' });
  }

  update(escolaridadePessoa: IEscolaridadePessoa): Observable<EntityResponseType> {
    return this.http.put<IEscolaridadePessoa>(
      `${this.resourceUrl}/${this.getEscolaridadePessoaIdentifier(escolaridadePessoa)}`,
      escolaridadePessoa,
      { observe: 'response' },
    );
  }

  partialUpdate(escolaridadePessoa: PartialUpdateEscolaridadePessoa): Observable<EntityResponseType> {
    return this.http.patch<IEscolaridadePessoa>(
      `${this.resourceUrl}/${this.getEscolaridadePessoaIdentifier(escolaridadePessoa)}`,
      escolaridadePessoa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEscolaridadePessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscolaridadePessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEscolaridadePessoaIdentifier(escolaridadePessoa: Pick<IEscolaridadePessoa, 'id'>): number {
    return escolaridadePessoa.id;
  }

  compareEscolaridadePessoa(o1: Pick<IEscolaridadePessoa, 'id'> | null, o2: Pick<IEscolaridadePessoa, 'id'> | null): boolean {
    return o1 && o2 ? this.getEscolaridadePessoaIdentifier(o1) === this.getEscolaridadePessoaIdentifier(o2) : o1 === o2;
  }

  addEscolaridadePessoaToCollectionIfMissing<Type extends Pick<IEscolaridadePessoa, 'id'>>(
    escolaridadePessoaCollection: Type[],
    ...escolaridadePessoasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const escolaridadePessoas: Type[] = escolaridadePessoasToCheck.filter(isPresent);
    if (escolaridadePessoas.length > 0) {
      const escolaridadePessoaCollectionIdentifiers = escolaridadePessoaCollection.map(escolaridadePessoaItem =>
        this.getEscolaridadePessoaIdentifier(escolaridadePessoaItem),
      );
      const escolaridadePessoasToAdd = escolaridadePessoas.filter(escolaridadePessoaItem => {
        const escolaridadePessoaIdentifier = this.getEscolaridadePessoaIdentifier(escolaridadePessoaItem);
        if (escolaridadePessoaCollectionIdentifiers.includes(escolaridadePessoaIdentifier)) {
          return false;
        }
        escolaridadePessoaCollectionIdentifiers.push(escolaridadePessoaIdentifier);
        return true;
      });
      return [...escolaridadePessoasToAdd, ...escolaridadePessoaCollection];
    }
    return escolaridadePessoaCollection;
  }
}
