import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUsuarioEmpresa } from '../usuario-empresa.model';
import { UsuarioEmpresaService } from '../service/usuario-empresa.service';

const usuarioEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IUsuarioEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(UsuarioEmpresaService)
      .find(id)
      .pipe(
        mergeMap((usuarioEmpresa: HttpResponse<IUsuarioEmpresa>) => {
          if (usuarioEmpresa.body) {
            return of(usuarioEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default usuarioEmpresaResolve;
