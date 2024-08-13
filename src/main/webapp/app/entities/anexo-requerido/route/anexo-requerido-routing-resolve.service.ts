import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoRequerido } from '../anexo-requerido.model';
import { AnexoRequeridoService } from '../service/anexo-requerido.service';

const anexoRequeridoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoRequerido> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoRequeridoService)
      .find(id)
      .pipe(
        mergeMap((anexoRequerido: HttpResponse<IAnexoRequerido>) => {
          if (anexoRequerido.body) {
            return of(anexoRequerido.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoRequeridoResolve;
