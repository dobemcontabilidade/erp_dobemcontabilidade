import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IObservacaoCnae } from '../observacao-cnae.model';
import { ObservacaoCnaeService } from '../service/observacao-cnae.service';

import observacaoCnaeResolve from './observacao-cnae-routing-resolve.service';

describe('ObservacaoCnae routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ObservacaoCnaeService;
  let resultObservacaoCnae: IObservacaoCnae | null | undefined;

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
    service = TestBed.inject(ObservacaoCnaeService);
    resultObservacaoCnae = undefined;
  });

  describe('resolve', () => {
    it('should return IObservacaoCnae returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        observacaoCnaeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultObservacaoCnae = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultObservacaoCnae).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        observacaoCnaeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultObservacaoCnae = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultObservacaoCnae).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IObservacaoCnae>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        observacaoCnaeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultObservacaoCnae = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultObservacaoCnae).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
