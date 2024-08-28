import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITermoContratoAssinaturaEmpresa } from '../termo-contrato-assinatura-empresa.model';
import { TermoContratoAssinaturaEmpresaService } from '../service/termo-contrato-assinatura-empresa.service';

const termoContratoAssinaturaEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | ITermoContratoAssinaturaEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(TermoContratoAssinaturaEmpresaService)
      .find(id)
      .pipe(
        mergeMap((termoContratoAssinaturaEmpresa: HttpResponse<ITermoContratoAssinaturaEmpresa>) => {
          if (termoContratoAssinaturaEmpresa.body) {
            return of(termoContratoAssinaturaEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default termoContratoAssinaturaEmpresaResolve;
