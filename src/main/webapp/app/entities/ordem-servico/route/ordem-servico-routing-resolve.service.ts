import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrdemServico } from '../ordem-servico.model';
import { OrdemServicoService } from '../service/ordem-servico.service';

const ordemServicoResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrdemServico> => {
  const id = route.params['id'];
  if (id) {
    return inject(OrdemServicoService)
      .find(id)
      .pipe(
        mergeMap((ordemServico: HttpResponse<IOrdemServico>) => {
          if (ordemServico.body) {
            return of(ordemServico.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default ordemServicoResolve;
