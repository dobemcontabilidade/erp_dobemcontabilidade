import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrupoCnae } from '../grupo-cnae.model';
import { GrupoCnaeService } from '../service/grupo-cnae.service';

const grupoCnaeResolve = (route: ActivatedRouteSnapshot): Observable<null | IGrupoCnae> => {
  const id = route.params['id'];
  if (id) {
    return inject(GrupoCnaeService)
      .find(id)
      .pipe(
        mergeMap((grupoCnae: HttpResponse<IGrupoCnae>) => {
          if (grupoCnae.body) {
            return of(grupoCnae.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default grupoCnaeResolve;
