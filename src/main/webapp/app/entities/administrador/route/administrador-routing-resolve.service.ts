import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdministrador } from '../administrador.model';
import { AdministradorService } from '../service/administrador.service';

const administradorResolve = (route: ActivatedRouteSnapshot): Observable<null | IAdministrador> => {
  const id = route.params['id'];
  if (id) {
    return inject(AdministradorService)
      .find(id)
      .pipe(
        mergeMap((administrador: HttpResponse<IAdministrador>) => {
          if (administrador.body) {
            return of(administrador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default administradorResolve;
