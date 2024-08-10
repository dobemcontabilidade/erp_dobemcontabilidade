import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanoContaAzul } from '../plano-conta-azul.model';
import { PlanoContaAzulService } from '../service/plano-conta-azul.service';

const planoContaAzulResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlanoContaAzul> => {
  const id = route.params['id'];
  if (id) {
    return inject(PlanoContaAzulService)
      .find(id)
      .pipe(
        mergeMap((planoContaAzul: HttpResponse<IPlanoContaAzul>) => {
          if (planoContaAzul.body) {
            return of(planoContaAzul.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default planoContaAzulResolve;
