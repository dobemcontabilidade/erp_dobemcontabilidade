import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgendaContadorConfig } from '../agenda-contador-config.model';
import { AgendaContadorConfigService } from '../service/agenda-contador-config.service';

const agendaContadorConfigResolve = (route: ActivatedRouteSnapshot): Observable<null | IAgendaContadorConfig> => {
  const id = route.params['id'];
  if (id) {
    return inject(AgendaContadorConfigService)
      .find(id)
      .pipe(
        mergeMap((agendaContadorConfig: HttpResponse<IAgendaContadorConfig>) => {
          if (agendaContadorConfig.body) {
            return of(agendaContadorConfig.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default agendaContadorConfigResolve;
