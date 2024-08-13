import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrazoAssinatura, NewPrazoAssinatura } from '../prazo-assinatura.model';

export type PartialUpdatePrazoAssinatura = Partial<IPrazoAssinatura> & Pick<IPrazoAssinatura, 'id'>;

export type EntityResponseType = HttpResponse<IPrazoAssinatura>;
export type EntityArrayResponseType = HttpResponse<IPrazoAssinatura[]>;

@Injectable({ providedIn: 'root' })
export class PrazoAssinaturaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prazo-assinaturas');

  create(prazoAssinatura: NewPrazoAssinatura): Observable<EntityResponseType> {
    return this.http.post<IPrazoAssinatura>(this.resourceUrl, prazoAssinatura, { observe: 'response' });
  }

  update(prazoAssinatura: IPrazoAssinatura): Observable<EntityResponseType> {
    return this.http.put<IPrazoAssinatura>(`${this.resourceUrl}/${this.getPrazoAssinaturaIdentifier(prazoAssinatura)}`, prazoAssinatura, {
      observe: 'response',
    });
  }

  partialUpdate(prazoAssinatura: PartialUpdatePrazoAssinatura): Observable<EntityResponseType> {
    return this.http.patch<IPrazoAssinatura>(`${this.resourceUrl}/${this.getPrazoAssinaturaIdentifier(prazoAssinatura)}`, prazoAssinatura, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrazoAssinatura>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrazoAssinatura[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrazoAssinaturaIdentifier(prazoAssinatura: Pick<IPrazoAssinatura, 'id'>): number {
    return prazoAssinatura.id;
  }

  comparePrazoAssinatura(o1: Pick<IPrazoAssinatura, 'id'> | null, o2: Pick<IPrazoAssinatura, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrazoAssinaturaIdentifier(o1) === this.getPrazoAssinaturaIdentifier(o2) : o1 === o2;
  }

  addPrazoAssinaturaToCollectionIfMissing<Type extends Pick<IPrazoAssinatura, 'id'>>(
    prazoAssinaturaCollection: Type[],
    ...prazoAssinaturasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const prazoAssinaturas: Type[] = prazoAssinaturasToCheck.filter(isPresent);
    if (prazoAssinaturas.length > 0) {
      const prazoAssinaturaCollectionIdentifiers = prazoAssinaturaCollection.map(prazoAssinaturaItem =>
        this.getPrazoAssinaturaIdentifier(prazoAssinaturaItem),
      );
      const prazoAssinaturasToAdd = prazoAssinaturas.filter(prazoAssinaturaItem => {
        const prazoAssinaturaIdentifier = this.getPrazoAssinaturaIdentifier(prazoAssinaturaItem);
        if (prazoAssinaturaCollectionIdentifiers.includes(prazoAssinaturaIdentifier)) {
          return false;
        }
        prazoAssinaturaCollectionIdentifiers.push(prazoAssinaturaIdentifier);
        return true;
      });
      return [...prazoAssinaturasToAdd, ...prazoAssinaturaCollection];
    }
    return prazoAssinaturaCollection;
  }
}
