import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorService } from '../service/criterio-avaliacao-ator.service';

import criterioAvaliacaoAtorResolve from './criterio-avaliacao-ator-routing-resolve.service';

describe('CriterioAvaliacaoAtor routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CriterioAvaliacaoAtorService;
  let resultCriterioAvaliacaoAtor: ICriterioAvaliacaoAtor | null | undefined;

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
    service = TestBed.inject(CriterioAvaliacaoAtorService);
    resultCriterioAvaliacaoAtor = undefined;
  });

  describe('resolve', () => {
    it('should return ICriterioAvaliacaoAtor returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        criterioAvaliacaoAtorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCriterioAvaliacaoAtor = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCriterioAvaliacaoAtor).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        criterioAvaliacaoAtorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCriterioAvaliacaoAtor = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCriterioAvaliacaoAtor).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICriterioAvaliacaoAtor>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        criterioAvaliacaoAtorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCriterioAvaliacaoAtor = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCriterioAvaliacaoAtor).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
