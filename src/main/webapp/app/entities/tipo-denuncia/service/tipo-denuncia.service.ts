import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoDenuncia, NewTipoDenuncia } from '../tipo-denuncia.model';

export type PartialUpdateTipoDenuncia = Partial<ITipoDenuncia> & Pick<ITipoDenuncia, 'id'>;

export type EntityResponseType = HttpResponse<ITipoDenuncia>;
export type EntityArrayResponseType = HttpResponse<ITipoDenuncia[]>;

@Injectable({ providedIn: 'root' })
export class TipoDenunciaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-denuncias');

  create(tipoDenuncia: NewTipoDenuncia): Observable<EntityResponseType> {
    return this.http.post<ITipoDenuncia>(this.resourceUrl, tipoDenuncia, { observe: 'response' });
  }

  update(tipoDenuncia: ITipoDenuncia): Observable<EntityResponseType> {
    return this.http.put<ITipoDenuncia>(`${this.resourceUrl}/${this.getTipoDenunciaIdentifier(tipoDenuncia)}`, tipoDenuncia, {
      observe: 'response',
    });
  }

  partialUpdate(tipoDenuncia: PartialUpdateTipoDenuncia): Observable<EntityResponseType> {
    return this.http.patch<ITipoDenuncia>(`${this.resourceUrl}/${this.getTipoDenunciaIdentifier(tipoDenuncia)}`, tipoDenuncia, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoDenuncia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoDenuncia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTipoDenunciaIdentifier(tipoDenuncia: Pick<ITipoDenuncia, 'id'>): number {
    return tipoDenuncia.id;
  }

  compareTipoDenuncia(o1: Pick<ITipoDenuncia, 'id'> | null, o2: Pick<ITipoDenuncia, 'id'> | null): boolean {
    return o1 && o2 ? this.getTipoDenunciaIdentifier(o1) === this.getTipoDenunciaIdentifier(o2) : o1 === o2;
  }

  addTipoDenunciaToCollectionIfMissing<Type extends Pick<ITipoDenuncia, 'id'>>(
    tipoDenunciaCollection: Type[],
    ...tipoDenunciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tipoDenuncias: Type[] = tipoDenunciasToCheck.filter(isPresent);
    if (tipoDenuncias.length > 0) {
      const tipoDenunciaCollectionIdentifiers = tipoDenunciaCollection.map(tipoDenunciaItem =>
        this.getTipoDenunciaIdentifier(tipoDenunciaItem),
      );
      const tipoDenunciasToAdd = tipoDenuncias.filter(tipoDenunciaItem => {
        const tipoDenunciaIdentifier = this.getTipoDenunciaIdentifier(tipoDenunciaItem);
        if (tipoDenunciaCollectionIdentifiers.includes(tipoDenunciaIdentifier)) {
          return false;
        }
        tipoDenunciaCollectionIdentifiers.push(tipoDenunciaIdentifier);
        return true;
      });
      return [...tipoDenunciasToAdd, ...tipoDenunciaCollection];
    }
    return tipoDenunciaCollection;
  }
}
