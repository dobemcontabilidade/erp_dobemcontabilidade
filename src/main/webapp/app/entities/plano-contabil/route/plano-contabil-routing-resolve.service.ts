import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanoContabil } from '../plano-contabil.model';
import { PlanoContabilService } from '../service/plano-contabil.service';

const planoContabilResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlanoContabil> => {
  const id = route.params['id'];
  if (id) {
    return inject(PlanoContabilService)
      .find(id)
      .pipe(
        mergeMap((planoContabil: HttpResponse<IPlanoContabil>) => {
          if (planoContabil.body) {
            return of(planoContabil.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default planoContabilResolve;
