import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IOpcaoRazaoSocialEmpresa } from '../opcao-razao-social-empresa.model';
import { OpcaoRazaoSocialEmpresaService } from '../service/opcao-razao-social-empresa.service';

import opcaoRazaoSocialEmpresaResolve from './opcao-razao-social-empresa-routing-resolve.service';

describe('OpcaoRazaoSocialEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: OpcaoRazaoSocialEmpresaService;
  let resultOpcaoRazaoSocialEmpresa: IOpcaoRazaoSocialEmpresa | null | undefined;

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
    service = TestBed.inject(OpcaoRazaoSocialEmpresaService);
    resultOpcaoRazaoSocialEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IOpcaoRazaoSocialEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        opcaoRazaoSocialEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOpcaoRazaoSocialEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultOpcaoRazaoSocialEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        opcaoRazaoSocialEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOpcaoRazaoSocialEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOpcaoRazaoSocialEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOpcaoRazaoSocialEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        opcaoRazaoSocialEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOpcaoRazaoSocialEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultOpcaoRazaoSocialEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
