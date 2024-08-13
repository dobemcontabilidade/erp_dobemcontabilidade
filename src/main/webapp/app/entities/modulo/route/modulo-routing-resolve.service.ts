import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModulo } from '../modulo.model';
import { ModuloService } from '../service/modulo.service';

const moduloResolve = (route: ActivatedRouteSnapshot): Observable<null | IModulo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ModuloService)
      .find(id)
      .pipe(
        mergeMap((modulo: HttpResponse<IModulo>) => {
          if (modulo.body) {
            return of(modulo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default moduloResolve;
