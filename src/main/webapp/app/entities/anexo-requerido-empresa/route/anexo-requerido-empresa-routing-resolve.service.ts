import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoRequeridoEmpresa } from '../anexo-requerido-empresa.model';
import { AnexoRequeridoEmpresaService } from '../service/anexo-requerido-empresa.service';

const anexoRequeridoEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoRequeridoEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoRequeridoEmpresaService)
      .find(id)
      .pipe(
        mergeMap((anexoRequeridoEmpresa: HttpResponse<IAnexoRequeridoEmpresa>) => {
          if (anexoRequeridoEmpresa.body) {
            return of(anexoRequeridoEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoRequeridoEmpresaResolve;
