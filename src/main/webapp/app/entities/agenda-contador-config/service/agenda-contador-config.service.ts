import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgendaContadorConfig, NewAgendaContadorConfig } from '../agenda-contador-config.model';

export type PartialUpdateAgendaContadorConfig = Partial<IAgendaContadorConfig> & Pick<IAgendaContadorConfig, 'id'>;

export type EntityResponseType = HttpResponse<IAgendaContadorConfig>;
export type EntityArrayResponseType = HttpResponse<IAgendaContadorConfig[]>;

@Injectable({ providedIn: 'root' })
export class AgendaContadorConfigService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agenda-contador-configs');

  create(agendaContadorConfig: NewAgendaContadorConfig): Observable<EntityResponseType> {
    return this.http.post<IAgendaContadorConfig>(this.resourceUrl, agendaContadorConfig, { observe: 'response' });
  }

  update(agendaContadorConfig: IAgendaContadorConfig): Observable<EntityResponseType> {
    return this.http.put<IAgendaContadorConfig>(
      `${this.resourceUrl}/${this.getAgendaContadorConfigIdentifier(agendaContadorConfig)}`,
      agendaContadorConfig,
      { observe: 'response' },
    );
  }

  partialUpdate(agendaContadorConfig: PartialUpdateAgendaContadorConfig): Observable<EntityResponseType> {
    return this.http.patch<IAgendaContadorConfig>(
      `${this.resourceUrl}/${this.getAgendaContadorConfigIdentifier(agendaContadorConfig)}`,
      agendaContadorConfig,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgendaContadorConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgendaContadorConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAgendaContadorConfigIdentifier(agendaContadorConfig: Pick<IAgendaContadorConfig, 'id'>): number {
    return agendaContadorConfig.id;
  }

  compareAgendaContadorConfig(o1: Pick<IAgendaContadorConfig, 'id'> | null, o2: Pick<IAgendaContadorConfig, 'id'> | null): boolean {
    return o1 && o2 ? this.getAgendaContadorConfigIdentifier(o1) === this.getAgendaContadorConfigIdentifier(o2) : o1 === o2;
  }

  addAgendaContadorConfigToCollectionIfMissing<Type extends Pick<IAgendaContadorConfig, 'id'>>(
    agendaContadorConfigCollection: Type[],
    ...agendaContadorConfigsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const agendaContadorConfigs: Type[] = agendaContadorConfigsToCheck.filter(isPresent);
    if (agendaContadorConfigs.length > 0) {
      const agendaContadorConfigCollectionIdentifiers = agendaContadorConfigCollection.map(agendaContadorConfigItem =>
        this.getAgendaContadorConfigIdentifier(agendaContadorConfigItem),
      );
      const agendaContadorConfigsToAdd = agendaContadorConfigs.filter(agendaContadorConfigItem => {
        const agendaContadorConfigIdentifier = this.getAgendaContadorConfigIdentifier(agendaContadorConfigItem);
        if (agendaContadorConfigCollectionIdentifiers.includes(agendaContadorConfigIdentifier)) {
          return false;
        }
        agendaContadorConfigCollectionIdentifiers.push(agendaContadorConfigIdentifier);
        return true;
      });
      return [...agendaContadorConfigsToAdd, ...agendaContadorConfigCollection];
    }
    return agendaContadorConfigCollection;
  }
}
