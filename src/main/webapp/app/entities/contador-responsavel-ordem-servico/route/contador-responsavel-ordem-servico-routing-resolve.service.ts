import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContadorResponsavelOrdemServico } from '../contador-responsavel-ordem-servico.model';
import { ContadorResponsavelOrdemServicoService } from '../service/contador-responsavel-ordem-servico.service';

const contadorResponsavelOrdemServicoResolve = (route: ActivatedRouteSnapshot): Observable<null | IContadorResponsavelOrdemServico> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContadorResponsavelOrdemServicoService)
      .find(id)
      .pipe(
        mergeMap((contadorResponsavelOrdemServico: HttpResponse<IContadorResponsavelOrdemServico>) => {
          if (contadorResponsavelOrdemServico.body) {
            return of(contadorResponsavelOrdemServico.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default contadorResponsavelOrdemServicoResolve;
