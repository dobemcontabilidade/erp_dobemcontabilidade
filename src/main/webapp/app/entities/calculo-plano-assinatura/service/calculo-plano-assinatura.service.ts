import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICalculoPlanoAssinatura, NewCalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';

export type PartialUpdateCalculoPlanoAssinatura = Partial<ICalculoPlanoAssinatura> & Pick<ICalculoPlanoAssinatura, 'id'>;

export type EntityResponseType = HttpResponse<ICalculoPlanoAssinatura>;
export type EntityArrayResponseType = HttpResponse<ICalculoPlanoAssinatura[]>;

@Injectable({ providedIn: 'root' })
export class CalculoPlanoAssinaturaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/calculo-plano-assinaturas');

  create(calculoPlanoAssinatura: NewCalculoPlanoAssinatura): Observable<EntityResponseType> {
    return this.http.post<ICalculoPlanoAssinatura>(this.resourceUrl, calculoPlanoAssinatura, { observe: 'response' });
  }

  update(calculoPlanoAssinatura: ICalculoPlanoAssinatura): Observable<EntityResponseType> {
    return this.http.put<ICalculoPlanoAssinatura>(
      `${this.resourceUrl}/${this.getCalculoPlanoAssinaturaIdentifier(calculoPlanoAssinatura)}`,
      calculoPlanoAssinatura,
      { observe: 'response' },
    );
  }

  partialUpdate(calculoPlanoAssinatura: PartialUpdateCalculoPlanoAssinatura): Observable<EntityResponseType> {
    return this.http.patch<ICalculoPlanoAssinatura>(
      `${this.resourceUrl}/${this.getCalculoPlanoAssinaturaIdentifier(calculoPlanoAssinatura)}`,
      calculoPlanoAssinatura,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICalculoPlanoAssinatura>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICalculoPlanoAssinatura[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCalculoPlanoAssinaturaIdentifier(calculoPlanoAssinatura: Pick<ICalculoPlanoAssinatura, 'id'>): number {
    return calculoPlanoAssinatura.id;
  }

  compareCalculoPlanoAssinatura(o1: Pick<ICalculoPlanoAssinatura, 'id'> | null, o2: Pick<ICalculoPlanoAssinatura, 'id'> | null): boolean {
    return o1 && o2 ? this.getCalculoPlanoAssinaturaIdentifier(o1) === this.getCalculoPlanoAssinaturaIdentifier(o2) : o1 === o2;
  }

  addCalculoPlanoAssinaturaToCollectionIfMissing<Type extends Pick<ICalculoPlanoAssinatura, 'id'>>(
    calculoPlanoAssinaturaCollection: Type[],
    ...calculoPlanoAssinaturasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const calculoPlanoAssinaturas: Type[] = calculoPlanoAssinaturasToCheck.filter(isPresent);
    if (calculoPlanoAssinaturas.length > 0) {
      const calculoPlanoAssinaturaCollectionIdentifiers = calculoPlanoAssinaturaCollection.map(calculoPlanoAssinaturaItem =>
        this.getCalculoPlanoAssinaturaIdentifier(calculoPlanoAssinaturaItem),
      );
      const calculoPlanoAssinaturasToAdd = calculoPlanoAssinaturas.filter(calculoPlanoAssinaturaItem => {
        const calculoPlanoAssinaturaIdentifier = this.getCalculoPlanoAssinaturaIdentifier(calculoPlanoAssinaturaItem);
        if (calculoPlanoAssinaturaCollectionIdentifiers.includes(calculoPlanoAssinaturaIdentifier)) {
          return false;
        }
        calculoPlanoAssinaturaCollectionIdentifiers.push(calculoPlanoAssinaturaIdentifier);
        return true;
      });
      return [...calculoPlanoAssinaturasToAdd, ...calculoPlanoAssinaturaCollection];
    }
    return calculoPlanoAssinaturaCollection;
  }
}
