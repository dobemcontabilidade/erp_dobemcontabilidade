import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';
import { ImpostoEmpresaModeloService } from '../service/imposto-empresa-modelo.service';

import impostoEmpresaModeloResolve from './imposto-empresa-modelo-routing-resolve.service';

describe('ImpostoEmpresaModelo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ImpostoEmpresaModeloService;
  let resultImpostoEmpresaModelo: IImpostoEmpresaModelo | null | undefined;

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
    service = TestBed.inject(ImpostoEmpresaModeloService);
    resultImpostoEmpresaModelo = undefined;
  });

  describe('resolve', () => {
    it('should return IImpostoEmpresaModelo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        impostoEmpresaModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultImpostoEmpresaModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultImpostoEmpresaModelo).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        impostoEmpresaModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultImpostoEmpresaModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultImpostoEmpresaModelo).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IImpostoEmpresaModelo>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        impostoEmpresaModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultImpostoEmpresaModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultImpostoEmpresaModelo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
