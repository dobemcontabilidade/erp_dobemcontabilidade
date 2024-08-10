import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEsfera } from '../esfera.model';
import { EsferaService } from '../service/esfera.service';

const esferaResolve = (route: ActivatedRouteSnapshot): Observable<null | IEsfera> => {
  const id = route.params['id'];
  if (id) {
    return inject(EsferaService)
      .find(id)
      .pipe(
        mergeMap((esfera: HttpResponse<IEsfera>) => {
          if (esfera.body) {
            return of(esfera.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default esferaResolve;
