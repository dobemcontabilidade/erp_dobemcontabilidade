import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';
import { GrupoAcessoUsuarioEmpresaService } from '../service/grupo-acesso-usuario-empresa.service';

import grupoAcessoUsuarioEmpresaResolve from './grupo-acesso-usuario-empresa-routing-resolve.service';

describe('GrupoAcessoUsuarioEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: GrupoAcessoUsuarioEmpresaService;
  let resultGrupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa | null | undefined;

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
    service = TestBed.inject(GrupoAcessoUsuarioEmpresaService);
    resultGrupoAcessoUsuarioEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IGrupoAcessoUsuarioEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoUsuarioEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoUsuarioEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultGrupoAcessoUsuarioEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoUsuarioEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoUsuarioEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultGrupoAcessoUsuarioEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IGrupoAcessoUsuarioEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoAcessoUsuarioEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoAcessoUsuarioEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultGrupoAcessoUsuarioEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
