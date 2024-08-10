import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPerfilContadorAreaContabil } from '../perfil-contador-area-contabil.model';
import { PerfilContadorAreaContabilService } from '../service/perfil-contador-area-contabil.service';

const perfilContadorAreaContabilResolve = (route: ActivatedRouteSnapshot): Observable<null | IPerfilContadorAreaContabil> => {
  const id = route.params['id'];
  if (id) {
    return inject(PerfilContadorAreaContabilService)
      .find(id)
      .pipe(
        mergeMap((perfilContadorAreaContabil: HttpResponse<IPerfilContadorAreaContabil>) => {
          if (perfilContadorAreaContabil.body) {
            return of(perfilContadorAreaContabil.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default perfilContadorAreaContabilResolve;
