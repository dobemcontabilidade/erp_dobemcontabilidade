import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';
import { ServicoContabilEmpresaModeloService } from '../service/servico-contabil-empresa-modelo.service';

import servicoContabilEmpresaModeloResolve from './servico-contabil-empresa-modelo-routing-resolve.service';

describe('ServicoContabilEmpresaModelo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ServicoContabilEmpresaModeloService;
  let resultServicoContabilEmpresaModelo: IServicoContabilEmpresaModelo | null | undefined;

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
    service = TestBed.inject(ServicoContabilEmpresaModeloService);
    resultServicoContabilEmpresaModelo = undefined;
  });

  describe('resolve', () => {
    it('should return IServicoContabilEmpresaModelo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilEmpresaModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilEmpresaModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultServicoContabilEmpresaModelo).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilEmpresaModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilEmpresaModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultServicoContabilEmpresaModelo).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IServicoContabilEmpresaModelo>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilEmpresaModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilEmpresaModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultServicoContabilEmpresaModelo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
