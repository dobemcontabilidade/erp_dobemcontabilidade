import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDivisaoCnae } from '../divisao-cnae.model';
import { DivisaoCnaeService } from '../service/divisao-cnae.service';

const divisaoCnaeResolve = (route: ActivatedRouteSnapshot): Observable<null | IDivisaoCnae> => {
  const id = route.params['id'];
  if (id) {
    return inject(DivisaoCnaeService)
      .find(id)
      .pipe(
        mergeMap((divisaoCnae: HttpResponse<IDivisaoCnae>) => {
          if (divisaoCnae.body) {
            return of(divisaoCnae.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default divisaoCnaeResolve;
