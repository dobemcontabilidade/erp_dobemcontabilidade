import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUsuarioErp } from '../usuario-erp.model';
import { UsuarioErpService } from '../service/usuario-erp.service';

const usuarioErpResolve = (route: ActivatedRouteSnapshot): Observable<null | IUsuarioErp> => {
  const id = route.params['id'];
  if (id) {
    return inject(UsuarioErpService)
      .find(id)
      .pipe(
        mergeMap((usuarioErp: HttpResponse<IUsuarioErp>) => {
          if (usuarioErp.body) {
            return of(usuarioErp.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default usuarioErpResolve;
