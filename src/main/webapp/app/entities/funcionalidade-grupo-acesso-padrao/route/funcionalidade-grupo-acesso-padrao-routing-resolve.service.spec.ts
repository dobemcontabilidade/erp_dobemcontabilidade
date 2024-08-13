import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';
import { FuncionalidadeGrupoAcessoPadraoService } from '../service/funcionalidade-grupo-acesso-padrao.service';

import funcionalidadeGrupoAcessoPadraoResolve from './funcionalidade-grupo-acesso-padrao-routing-resolve.service';

describe('FuncionalidadeGrupoAcessoPadrao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: FuncionalidadeGrupoAcessoPadraoService;
  let resultFuncionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao | null | undefined;

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
    service = TestBed.inject(FuncionalidadeGrupoAcessoPadraoService);
    resultFuncionalidadeGrupoAcessoPadrao = undefined;
  });

  describe('resolve', () => {
    it('should return IFuncionalidadeGrupoAcessoPadrao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        funcionalidadeGrupoAcessoPadraoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFuncionalidadeGrupoAcessoPadrao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFuncionalidadeGrupoAcessoPadrao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        funcionalidadeGrupoAcessoPadraoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFuncionalidadeGrupoAcessoPadrao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFuncionalidadeGrupoAcessoPadrao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFuncionalidadeGrupoAcessoPadrao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        funcionalidadeGrupoAcessoPadraoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFuncionalidadeGrupoAcessoPadrao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFuncionalidadeGrupoAcessoPadrao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
