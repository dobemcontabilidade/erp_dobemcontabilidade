import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAdicionalEnquadramento } from '../adicional-enquadramento.model';
import { AdicionalEnquadramentoService } from '../service/adicional-enquadramento.service';

import adicionalEnquadramentoResolve from './adicional-enquadramento-routing-resolve.service';

describe('AdicionalEnquadramento routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AdicionalEnquadramentoService;
  let resultAdicionalEnquadramento: IAdicionalEnquadramento | null | undefined;

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
    service = TestBed.inject(AdicionalEnquadramentoService);
    resultAdicionalEnquadramento = undefined;
  });

  describe('resolve', () => {
    it('should return IAdicionalEnquadramento returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalEnquadramentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalEnquadramento = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAdicionalEnquadramento).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalEnquadramentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalEnquadramento = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdicionalEnquadramento).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAdicionalEnquadramento>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalEnquadramentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalEnquadramento = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAdicionalEnquadramento).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
