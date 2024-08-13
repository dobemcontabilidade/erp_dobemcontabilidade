import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFluxoExecucao } from '../fluxo-execucao.model';
import { FluxoExecucaoService } from '../service/fluxo-execucao.service';

const fluxoExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IFluxoExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(FluxoExecucaoService)
      .find(id)
      .pipe(
        mergeMap((fluxoExecucao: HttpResponse<IFluxoExecucao>) => {
          if (fluxoExecucao.body) {
            return of(fluxoExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default fluxoExecucaoResolve;
