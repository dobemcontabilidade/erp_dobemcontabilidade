import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAtorAvaliado } from '../ator-avaliado.model';
import { AtorAvaliadoService } from '../service/ator-avaliado.service';

const atorAvaliadoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAtorAvaliado> => {
  const id = route.params['id'];
  if (id) {
    return inject(AtorAvaliadoService)
      .find(id)
      .pipe(
        mergeMap((atorAvaliado: HttpResponse<IAtorAvaliado>) => {
          if (atorAvaliado.body) {
            return of(atorAvaliado.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default atorAvaliadoResolve;
