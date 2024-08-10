import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPerfilRedeSocial } from '../perfil-rede-social.model';
import { PerfilRedeSocialService } from '../service/perfil-rede-social.service';

const perfilRedeSocialResolve = (route: ActivatedRouteSnapshot): Observable<null | IPerfilRedeSocial> => {
  const id = route.params['id'];
  if (id) {
    return inject(PerfilRedeSocialService)
      .find(id)
      .pipe(
        mergeMap((perfilRedeSocial: HttpResponse<IPerfilRedeSocial>) => {
          if (perfilRedeSocial.body) {
            return of(perfilRedeSocial.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default perfilRedeSocialResolve;
