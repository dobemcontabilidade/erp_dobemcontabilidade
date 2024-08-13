import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';
import { GrupoAcessoPadraoService } from '../service/grupo-acesso-padrao.service';

import grupoAcessoPadraoResolve from './grupo-acesso-padrao-routing-resolve.service';

describe('GrupoAcessoPadrao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: GrupoAcessoPadraoService;
  let resultGrupoAcessoPadrao: IGrupoAcessoPadrao | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(GrupoAcessoPadraoService);
    resultGrupoAcessoPadrao = undefined;
  });

  describe('resolve', () => {
    it('should return IGrupoAcessoPadrao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoPadraoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoPadrao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultGrupoAcessoPadrao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoPadraoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoPadrao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultGrupoAcessoPadrao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IGrupoAcessoPadrao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoPadraoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoPadrao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultGrupoAcessoPadrao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
