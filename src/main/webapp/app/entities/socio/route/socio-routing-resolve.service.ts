import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocio } from '../socio.model';
import { SocioService } from '../service/socio.service';

const socioResolve = (route: ActivatedRouteSnapshot): Observable<null | ISocio> => {
  const id = route.params['id'];
  if (id) {
    return inject(SocioService)
      .find(id)
      .pipe(
        mergeMap((socio: HttpResponse<ISocio>) => {
          if (socio.body) {
            return of(socio.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default socioResolve;
