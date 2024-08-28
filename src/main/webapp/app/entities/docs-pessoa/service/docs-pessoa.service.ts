import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocsPessoa, NewDocsPessoa } from '../docs-pessoa.model';

export type PartialUpdateDocsPessoa = Partial<IDocsPessoa> & Pick<IDocsPessoa, 'id'>;

export type EntityResponseType = HttpResponse<IDocsPessoa>;
export type EntityArrayResponseType = HttpResponse<IDocsPessoa[]>;

@Injectable({ providedIn: 'root' })
export class DocsPessoaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/docs-pessoas');

  create(docsPessoa: NewDocsPessoa): Observable<EntityResponseType> {
    return this.http.post<IDocsPessoa>(this.resourceUrl, docsPessoa, { observe: 'response' });
  }

  update(docsPessoa: IDocsPessoa): Observable<EntityResponseType> {
    return this.http.put<IDocsPessoa>(`${this.resourceUrl}/${this.getDocsPessoaIdentifier(docsPessoa)}`, docsPessoa, {
      observe: 'response',
    });
  }

  partialUpdate(docsPessoa: PartialUpdateDocsPessoa): Observable<EntityResponseType> {
    return this.http.patch<IDocsPessoa>(`${this.resourceUrl}/${this.getDocsPessoaIdentifier(docsPessoa)}`, docsPessoa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocsPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocsPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocsPessoaIdentifier(docsPessoa: Pick<IDocsPessoa, 'id'>): number {
    return docsPessoa.id;
  }

  compareDocsPessoa(o1: Pick<IDocsPessoa, 'id'> | null, o2: Pick<IDocsPessoa, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocsPessoaIdentifier(o1) === this.getDocsPessoaIdentifier(o2) : o1 === o2;
  }

  addDocsPessoaToCollectionIfMissing<Type extends Pick<IDocsPessoa, 'id'>>(
    docsPessoaCollection: Type[],
    ...docsPessoasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const docsPessoas: Type[] = docsPessoasToCheck.filter(isPresent);
    if (docsPessoas.length > 0) {
      const docsPessoaCollectionIdentifiers = docsPessoaCollection.map(docsPessoaItem => this.getDocsPessoaIdentifier(docsPessoaItem));
      const docsPessoasToAdd = docsPessoas.filter(docsPessoaItem => {
        const docsPessoaIdentifier = this.getDocsPessoaIdentifier(docsPessoaItem);
        if (docsPessoaCollectionIdentifiers.includes(docsPessoaIdentifier)) {
          return false;
        }
        docsPessoaCollectionIdentifiers.push(docsPessoaIdentifier);
        return true;
      });
      return [...docsPessoasToAdd, ...docsPessoaCollection];
    }
    return docsPessoaCollection;
  }
}
