import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPermisao } from '../permisao.model';
import { PermisaoService } from '../service/permisao.service';

const permisaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IPermisao> => {
  const id = route.params['id'];
  if (id) {
    return inject(PermisaoService)
      .find(id)
      .pipe(
        mergeMap((permisao: HttpResponse<IPermisao>) => {
          if (permisao.body) {
            return of(permisao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default permisaoResolve;
