import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITermoAdesaoContador } from '../termo-adesao-contador.model';
import { TermoAdesaoContadorService } from '../service/termo-adesao-contador.service';

const termoAdesaoContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | ITermoAdesaoContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(TermoAdesaoContadorService)
      .find(id)
      .pipe(
        mergeMap((termoAdesaoContador: HttpResponse<ITermoAdesaoContador>) => {
          if (termoAdesaoContador.body) {
            return of(termoAdesaoContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default termoAdesaoContadorResolve;
