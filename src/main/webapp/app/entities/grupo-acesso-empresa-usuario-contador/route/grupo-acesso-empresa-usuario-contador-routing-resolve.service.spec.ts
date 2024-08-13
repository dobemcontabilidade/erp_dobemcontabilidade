import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';
import { GrupoAcessoEmpresaUsuarioContadorService } from '../service/grupo-acesso-empresa-usuario-contador.service';

import grupoAcessoEmpresaUsuarioContadorResolve from './grupo-acesso-empresa-usuario-contador-routing-resolve.service';

describe('GrupoAcessoEmpresaUsuarioContador routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: GrupoAcessoEmpresaUsuarioContadorService;
  let resultGrupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador | null | undefined;

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
    service = TestBed.inject(GrupoAcessoEmpresaUsuarioContadorService);
    resultGrupoAcessoEmpresaUsuarioContador = undefined;
  });

  describe('resolve', () => {
    it('should return IGrupoAcessoEmpresaUsuarioContador returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoEmpresaUsuarioContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoEmpresaUsuarioContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultGrupoAcessoEmpresaUsuarioContador).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoEmpresaUsuarioContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoEmpresaUsuarioContador = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultGrupoAcessoEmpresaUsuarioContador).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IGrupoAcessoEmpresaUsuarioContador>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoEmpresaUsuarioContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoEmpresaUsuarioContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultGrupoAcessoEmpresaUsuarioContador).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
