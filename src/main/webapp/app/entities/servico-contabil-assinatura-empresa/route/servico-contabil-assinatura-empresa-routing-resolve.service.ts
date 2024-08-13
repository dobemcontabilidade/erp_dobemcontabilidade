import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServicoContabilAssinaturaEmpresa } from '../servico-contabil-assinatura-empresa.model';
import { ServicoContabilAssinaturaEmpresaService } from '../service/servico-contabil-assinatura-empresa.service';

const servicoContabilAssinaturaEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IServicoContabilAssinaturaEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(ServicoContabilAssinaturaEmpresaService)
      .find(id)
      .pipe(
        mergeMap((servicoContabilAssinaturaEmpresa: HttpResponse<IServicoContabilAssinaturaEmpresa>) => {
          if (servicoContabilAssinaturaEmpresa.body) {
            return of(servicoContabilAssinaturaEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default servicoContabilAssinaturaEmpresaResolve;
