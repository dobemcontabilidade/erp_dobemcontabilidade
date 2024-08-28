import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICertificadoDigitalEmpresa } from '../certificado-digital-empresa.model';
import { CertificadoDigitalEmpresaService } from '../service/certificado-digital-empresa.service';

import certificadoDigitalEmpresaResolve from './certificado-digital-empresa-routing-resolve.service';

describe('CertificadoDigitalEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CertificadoDigitalEmpresaService;
  let resultCertificadoDigitalEmpresa: ICertificadoDigitalEmpresa | null | undefined;

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
    service = TestBed.inject(CertificadoDigitalEmpresaService);
    resultCertificadoDigitalEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return ICertificadoDigitalEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        certificadoDigitalEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCertificadoDigitalEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCertificadoDigitalEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        certificadoDigitalEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCertificadoDigitalEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCertificadoDigitalEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICertificadoDigitalEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        certificadoDigitalEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCertificadoDigitalEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCertificadoDigitalEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
