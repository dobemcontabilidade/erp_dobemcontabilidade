import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFluxoModelo } from '../fluxo-modelo.model';
import { FluxoModeloService } from '../service/fluxo-modelo.service';

const fluxoModeloResolve = (route: ActivatedRouteSnapshot): Observable<null | IFluxoModelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(FluxoModeloService)
      .find(id)
      .pipe(
        mergeMap((fluxoModelo: HttpResponse<IFluxoModelo>) => {
          if (fluxoModelo.body) {
            return of(fluxoModelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default fluxoModeloResolve;
