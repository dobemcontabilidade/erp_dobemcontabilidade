import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IGrupoCnae } from '../grupo-cnae.model';
import { GrupoCnaeService } from '../service/grupo-cnae.service';

import grupoCnaeResolve from './grupo-cnae-routing-resolve.service';

describe('GrupoCnae routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: GrupoCnaeService;
  let resultGrupoCnae: IGrupoCnae | null | undefined;

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
    service = TestBed.inject(GrupoCnaeService);
    resultGrupoCnae = undefined;
  });

  describe('resolve', () => {
    it('should return IGrupoCnae returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoCnaeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoCnae = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultGrupoCnae).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoCnaeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoCnae = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultGrupoCnae).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IGrupoCnae>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        grupoCnaeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultGrupoCnae = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultGrupoCnae).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
