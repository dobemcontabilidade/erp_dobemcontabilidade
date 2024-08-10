import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOpcaoNomeFantasiaEmpresa } from '../opcao-nome-fantasia-empresa.model';
import { OpcaoNomeFantasiaEmpresaService } from '../service/opcao-nome-fantasia-empresa.service';

const opcaoNomeFantasiaEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IOpcaoNomeFantasiaEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(OpcaoNomeFantasiaEmpresaService)
      .find(id)
      .pipe(
        mergeMap((opcaoNomeFantasiaEmpresa: HttpResponse<IOpcaoNomeFantasiaEmpresa>) => {
          if (opcaoNomeFantasiaEmpresa.body) {
            return of(opcaoNomeFantasiaEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default opcaoNomeFantasiaEmpresaResolve;
