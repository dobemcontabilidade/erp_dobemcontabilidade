import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContador } from '../contador.model';
import { ContadorService } from '../service/contador.service';

const contadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContadorService)
      .find(id)
      .pipe(
        mergeMap((contador: HttpResponse<IContador>) => {
          if (contador.body) {
            return of(contador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default contadorResolve;
