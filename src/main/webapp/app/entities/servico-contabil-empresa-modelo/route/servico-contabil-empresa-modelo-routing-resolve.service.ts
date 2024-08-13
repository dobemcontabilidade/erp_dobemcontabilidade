import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';
import { ServicoContabilEmpresaModeloService } from '../service/servico-contabil-empresa-modelo.service';

const servicoContabilEmpresaModeloResolve = (route: ActivatedRouteSnapshot): Observable<null | IServicoContabilEmpresaModelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ServicoContabilEmpresaModeloService)
      .find(id)
      .pipe(
        mergeMap((servicoContabilEmpresaModelo: HttpResponse<IServicoContabilEmpresaModelo>) => {
          if (servicoContabilEmpresaModelo.body) {
            return of(servicoContabilEmpresaModelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default servicoContabilEmpresaModeloResolve;
