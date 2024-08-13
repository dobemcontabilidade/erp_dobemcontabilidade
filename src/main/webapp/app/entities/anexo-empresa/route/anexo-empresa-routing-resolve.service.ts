import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoEmpresa } from '../anexo-empresa.model';
import { AnexoEmpresaService } from '../service/anexo-empresa.service';

const anexoEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoEmpresaService)
      .find(id)
      .pipe(
        mergeMap((anexoEmpresa: HttpResponse<IAnexoEmpresa>) => {
          if (anexoEmpresa.body) {
            return of(anexoEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoEmpresaResolve;
