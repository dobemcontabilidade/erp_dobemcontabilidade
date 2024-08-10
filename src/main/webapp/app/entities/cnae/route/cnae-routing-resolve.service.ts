import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICnae } from '../cnae.model';
import { CnaeService } from '../service/cnae.service';

const cnaeResolve = (route: ActivatedRouteSnapshot): Observable<null | ICnae> => {
  const id = route.params['id'];
  if (id) {
    return inject(CnaeService)
      .find(id)
      .pipe(
        mergeMap((cnae: HttpResponse<ICnae>) => {
          if (cnae.body) {
            return of(cnae.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cnaeResolve;
