import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IFornecedorCertificado } from '../fornecedor-certificado.model';
import { FornecedorCertificadoService } from '../service/fornecedor-certificado.service';

import fornecedorCertificadoResolve from './fornecedor-certificado-routing-resolve.service';

describe('FornecedorCertificado routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: FornecedorCertificadoService;
  let resultFornecedorCertificado: IFornecedorCertificado | null | undefined;

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
    service = TestBed.inject(FornecedorCertificadoService);
    resultFornecedorCertificado = undefined;
  });

  describe('resolve', () => {
    it('should return IFornecedorCertificado returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        fornecedorCertificadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFornecedorCertificado = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFornecedorCertificado).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        fornecedorCertificadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFornecedorCertificado = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFornecedorCertificado).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFornecedorCertificado>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        fornecedorCertificadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFornecedorCertificado = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFornecedorCertificado).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
