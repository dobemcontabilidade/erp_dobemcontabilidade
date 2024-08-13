import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImposto } from '../imposto.model';
import { ImpostoService } from '../service/imposto.service';

const impostoResolve = (route: ActivatedRouteSnapshot): Observable<null | IImposto> => {
  const id = route.params['id'];
  if (id) {
    return inject(ImpostoService)
      .find(id)
      .pipe(
        mergeMap((imposto: HttpResponse<IImposto>) => {
          if (imposto.body) {
            return of(imposto.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default impostoResolve;
