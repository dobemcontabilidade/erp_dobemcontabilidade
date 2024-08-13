import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IValorBaseRamo } from '../valor-base-ramo.model';
import { ValorBaseRamoService } from '../service/valor-base-ramo.service';

import valorBaseRamoResolve from './valor-base-ramo-routing-resolve.service';

describe('ValorBaseRamo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ValorBaseRamoService;
  let resultValorBaseRamo: IValorBaseRamo | null | undefined;

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
    service = TestBed.inject(ValorBaseRamoService);
    resultValorBaseRamo = undefined;
  });

  describe('resolve', () => {
    it('should return IValorBaseRamo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        valorBaseRamoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultValorBaseRamo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultValorBaseRamo).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        valorBaseRamoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultValorBaseRamo = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultValorBaseRamo).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IValorBaseRamo>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        valorBaseRamoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultValorBaseRamo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultValorBaseRamo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
