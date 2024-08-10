import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAdicionalRamo } from '../adicional-ramo.model';
import { AdicionalRamoService } from '../service/adicional-ramo.service';

import adicionalRamoResolve from './adicional-ramo-routing-resolve.service';

describe('AdicionalRamo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AdicionalRamoService;
  let resultAdicionalRamo: IAdicionalRamo | null | undefined;

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
    service = TestBed.inject(AdicionalRamoService);
    resultAdicionalRamo = undefined;
  });

  describe('resolve', () => {
    it('should return IAdicionalRamo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalRamoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalRamo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAdicionalRamo).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalRamoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalRamo = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdicionalRamo).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAdicionalRamo>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalRamoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalRamo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAdicionalRamo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
