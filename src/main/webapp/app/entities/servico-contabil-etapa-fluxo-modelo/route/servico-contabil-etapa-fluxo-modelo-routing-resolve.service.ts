import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServicoContabilEtapaFluxoModelo } from '../servico-contabil-etapa-fluxo-modelo.model';
import { ServicoContabilEtapaFluxoModeloService } from '../service/servico-contabil-etapa-fluxo-modelo.service';

const servicoContabilEtapaFluxoModeloResolve = (route: ActivatedRouteSnapshot): Observable<null | IServicoContabilEtapaFluxoModelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ServicoContabilEtapaFluxoModeloService)
      .find(id)
      .pipe(
        mergeMap((servicoContabilEtapaFluxoModelo: HttpResponse<IServicoContabilEtapaFluxoModelo>) => {
          if (servicoContabilEtapaFluxoModelo.body) {
            return of(servicoContabilEtapaFluxoModelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default servicoContabilEtapaFluxoModeloResolve;
