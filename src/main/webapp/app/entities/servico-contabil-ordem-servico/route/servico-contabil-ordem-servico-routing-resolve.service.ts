import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';
import { ServicoContabilOrdemServicoService } from '../service/servico-contabil-ordem-servico.service';

const servicoContabilOrdemServicoResolve = (route: ActivatedRouteSnapshot): Observable<null | IServicoContabilOrdemServico> => {
  const id = route.params['id'];
  if (id) {
    return inject(ServicoContabilOrdemServicoService)
      .find(id)
      .pipe(
        mergeMap((servicoContabilOrdemServico: HttpResponse<IServicoContabilOrdemServico>) => {
          if (servicoContabilOrdemServico.body) {
            return of(servicoContabilOrdemServico.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default servicoContabilOrdemServicoResolve;
