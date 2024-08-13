import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgenteIntegracaoEstagio } from '../agente-integracao-estagio.model';
import { AgenteIntegracaoEstagioService } from '../service/agente-integracao-estagio.service';

const agenteIntegracaoEstagioResolve = (route: ActivatedRouteSnapshot): Observable<null | IAgenteIntegracaoEstagio> => {
  const id = route.params['id'];
  if (id) {
    return inject(AgenteIntegracaoEstagioService)
      .find(id)
      .pipe(
        mergeMap((agenteIntegracaoEstagio: HttpResponse<IAgenteIntegracaoEstagio>) => {
          if (agenteIntegracaoEstagio.body) {
            return of(agenteIntegracaoEstagio.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default agenteIntegracaoEstagioResolve;
