import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEtapaFluxoModelo } from '../etapa-fluxo-modelo.model';
import { EtapaFluxoModeloService } from '../service/etapa-fluxo-modelo.service';

const etapaFluxoModeloResolve = (route: ActivatedRouteSnapshot): Observable<null | IEtapaFluxoModelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(EtapaFluxoModeloService)
      .find(id)
      .pipe(
        mergeMap((etapaFluxoModelo: HttpResponse<IEtapaFluxoModelo>) => {
          if (etapaFluxoModelo.body) {
            return of(etapaFluxoModelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default etapaFluxoModeloResolve;
