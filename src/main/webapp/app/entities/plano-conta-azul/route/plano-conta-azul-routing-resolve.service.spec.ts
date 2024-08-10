import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IPlanoContaAzul } from '../plano-conta-azul.model';
import { PlanoContaAzulService } from '../service/plano-conta-azul.service';

import planoContaAzulResolve from './plano-conta-azul-routing-resolve.service';

describe('PlanoContaAzul routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: PlanoContaAzulService;
  let resultPlanoContaAzul: IPlanoContaAzul | null | undefined;

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
    service = TestBed.inject(PlanoContaAzulService);
    resultPlanoContaAzul = undefined;
  });

  describe('resolve', () => {
    it('should return IPlanoContaAzul returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        planoContaAzulResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPlanoContaAzul = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPlanoContaAzul).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        planoContaAzulResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPlanoContaAzul = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPlanoContaAzul).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPlanoContaAzul>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        planoContaAzulResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPlanoContaAzul = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPlanoContaAzul).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
