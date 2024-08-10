import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';
import { OpcaoRazaoSocialEmpresaService } from '../service/opcao-razao-social-empresa.service';

const opcaoRazaoSocialEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IOpcaoRazaoSocialEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(OpcaoRazaoSocialEmpresaService)
      .find(id)
      .pipe(
        mergeMap((opcaoRazaoSocialEmpresa: HttpResponse<IOpcaoRazaoSocialEmpresa>) => {
          if (opcaoRazaoSocialEmpresa.body) {
            return of(opcaoRazaoSocialEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default opcaoRazaoSocialEmpresaResolve;
