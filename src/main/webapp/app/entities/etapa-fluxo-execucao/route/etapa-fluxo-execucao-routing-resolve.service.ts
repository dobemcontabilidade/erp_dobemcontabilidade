import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';
import { EtapaFluxoExecucaoService } from '../service/etapa-fluxo-execucao.service';

const etapaFluxoExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IEtapaFluxoExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(EtapaFluxoExecucaoService)
      .find(id)
      .pipe(
        mergeMap((etapaFluxoExecucao: HttpResponse<IEtapaFluxoExecucao>) => {
          if (etapaFluxoExecucao.body) {
            return of(etapaFluxoExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default etapaFluxoExecucaoResolve;
