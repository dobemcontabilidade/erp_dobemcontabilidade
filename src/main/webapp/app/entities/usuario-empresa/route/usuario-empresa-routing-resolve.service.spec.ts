import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IUsuarioEmpresa } from '../usuario-empresa.model';
import { UsuarioEmpresaService } from '../service/usuario-empresa.service';

import usuarioEmpresaResolve from './usuario-empresa-routing-resolve.service';

describe('UsuarioEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: UsuarioEmpresaService;
  let resultUsuarioEmpresa: IUsuarioEmpresa | null | undefined;

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
    service = TestBed.inject(UsuarioEmpresaService);
    resultUsuarioEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IUsuarioEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        usuarioEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultUsuarioEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultUsuarioEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        usuarioEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultUsuarioEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultUsuarioEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IUsuarioEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        usuarioEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultUsuarioEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultUsuarioEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
