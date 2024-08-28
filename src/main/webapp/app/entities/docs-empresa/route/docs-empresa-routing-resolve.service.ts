import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocsEmpresa } from '../docs-empresa.model';
import { DocsEmpresaService } from '../service/docs-empresa.service';

const docsEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IDocsEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(DocsEmpresaService)
      .find(id)
      .pipe(
        mergeMap((docsEmpresa: HttpResponse<IDocsEmpresa>) => {
          if (docsEmpresa.body) {
            return of(docsEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default docsEmpresaResolve;
