import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoServicoContabilEmpresa } from '../anexo-servico-contabil-empresa.model';
import { AnexoServicoContabilEmpresaService } from '../service/anexo-servico-contabil-empresa.service';

const anexoServicoContabilEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoServicoContabilEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoServicoContabilEmpresaService)
      .find(id)
      .pipe(
        mergeMap((anexoServicoContabilEmpresa: HttpResponse<IAnexoServicoContabilEmpresa>) => {
          if (anexoServicoContabilEmpresa.body) {
            return of(anexoServicoContabilEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoServicoContabilEmpresaResolve;
