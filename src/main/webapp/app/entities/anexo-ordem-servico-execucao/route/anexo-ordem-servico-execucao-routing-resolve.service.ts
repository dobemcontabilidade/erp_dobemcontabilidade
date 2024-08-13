import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';
import { AnexoOrdemServicoExecucaoService } from '../service/anexo-ordem-servico-execucao.service';

const anexoOrdemServicoExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoOrdemServicoExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoOrdemServicoExecucaoService)
      .find(id)
      .pipe(
        mergeMap((anexoOrdemServicoExecucao: HttpResponse<IAnexoOrdemServicoExecucao>) => {
          if (anexoOrdemServicoExecucao.body) {
            return of(anexoOrdemServicoExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoOrdemServicoExecucaoResolve;
