import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';
import { ServicoContabilEtapaFluxoExecucaoService } from '../service/servico-contabil-etapa-fluxo-execucao.service';

const servicoContabilEtapaFluxoExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IServicoContabilEtapaFluxoExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(ServicoContabilEtapaFluxoExecucaoService)
      .find(id)
      .pipe(
        mergeMap((servicoContabilEtapaFluxoExecucao: HttpResponse<IServicoContabilEtapaFluxoExecucao>) => {
          if (servicoContabilEtapaFluxoExecucao.body) {
            return of(servicoContabilEtapaFluxoExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default servicoContabilEtapaFluxoExecucaoResolve;
