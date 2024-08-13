import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstrangeiro } from '../estrangeiro.model';
import { EstrangeiroService } from '../service/estrangeiro.service';

const estrangeiroResolve = (route: ActivatedRouteSnapshot): Observable<null | IEstrangeiro> => {
  const id = route.params['id'];
  if (id) {
    return inject(EstrangeiroService)
      .find(id)
      .pipe(
        mergeMap((estrangeiro: HttpResponse<IEstrangeiro>) => {
          if (estrangeiro.body) {
            return of(estrangeiro.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default estrangeiroResolve;
