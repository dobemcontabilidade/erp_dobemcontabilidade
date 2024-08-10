import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdicionalEnquadramento } from '../adicional-enquadramento.model';
import { AdicionalEnquadramentoService } from '../service/adicional-enquadramento.service';

const adicionalEnquadramentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAdicionalEnquadramento> => {
  const id = route.params['id'];
  if (id) {
    return inject(AdicionalEnquadramentoService)
      .find(id)
      .pipe(
        mergeMap((adicionalEnquadramento: HttpResponse<IAdicionalEnquadramento>) => {
          if (adicionalEnquadramento.body) {
            return of(adicionalEnquadramento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default adicionalEnquadramentoResolve;
