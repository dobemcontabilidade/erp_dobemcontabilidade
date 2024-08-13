import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IServicoContabilAssinaturaEmpresa } from '../servico-contabil-assinatura-empresa.model';
import { ServicoContabilAssinaturaEmpresaService } from '../service/servico-contabil-assinatura-empresa.service';

import servicoContabilAssinaturaEmpresaResolve from './servico-contabil-assinatura-empresa-routing-resolve.service';

describe('ServicoContabilAssinaturaEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ServicoContabilAssinaturaEmpresaService;
  let resultServicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa | null | undefined;

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
    service = TestBed.inject(ServicoContabilAssinaturaEmpresaService);
    resultServicoContabilAssinaturaEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IServicoContabilAssinaturaEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultServicoContabilAssinaturaEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultServicoContabilAssinaturaEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IServicoContabilAssinaturaEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultServicoContabilAssinaturaEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
