import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITermoDeAdesao } from '../termo-de-adesao.model';
import { TermoDeAdesaoService } from '../service/termo-de-adesao.service';

const termoDeAdesaoResolve = (route: ActivatedRouteSnapshot): Observable<null | ITermoDeAdesao> => {
  const id = route.params['id'];
  if (id) {
    return inject(TermoDeAdesaoService)
      .find(id)
      .pipe(
        mergeMap((termoDeAdesao: HttpResponse<ITermoDeAdesao>) => {
          if (termoDeAdesao.body) {
            return of(termoDeAdesao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default termoDeAdesaoResolve;
