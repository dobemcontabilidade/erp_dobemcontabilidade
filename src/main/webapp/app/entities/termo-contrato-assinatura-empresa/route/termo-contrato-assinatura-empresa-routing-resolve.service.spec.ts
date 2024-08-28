import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITermoContratoAssinaturaEmpresa } from '../termo-contrato-assinatura-empresa.model';
import { TermoContratoAssinaturaEmpresaService } from '../service/termo-contrato-assinatura-empresa.service';

import termoContratoAssinaturaEmpresaResolve from './termo-contrato-assinatura-empresa-routing-resolve.service';

describe('TermoContratoAssinaturaEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TermoContratoAssinaturaEmpresaService;
  let resultTermoContratoAssinaturaEmpresa: ITermoContratoAssinaturaEmpresa | null | undefined;

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
    service = TestBed.inject(TermoContratoAssinaturaEmpresaService);
    resultTermoContratoAssinaturaEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return ITermoContratoAssinaturaEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoContratoAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoContratoAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTermoContratoAssinaturaEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoContratoAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoContratoAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTermoContratoAssinaturaEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITermoContratoAssinaturaEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoContratoAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoContratoAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTermoContratoAssinaturaEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
