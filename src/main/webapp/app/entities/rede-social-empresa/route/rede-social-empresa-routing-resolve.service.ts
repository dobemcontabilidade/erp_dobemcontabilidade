import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRedeSocialEmpresa } from '../rede-social-empresa.model';
import { RedeSocialEmpresaService } from '../service/rede-social-empresa.service';

const redeSocialEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IRedeSocialEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(RedeSocialEmpresaService)
      .find(id)
      .pipe(
        mergeMap((redeSocialEmpresa: HttpResponse<IRedeSocialEmpresa>) => {
          if (redeSocialEmpresa.body) {
            return of(redeSocialEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default redeSocialEmpresaResolve;
