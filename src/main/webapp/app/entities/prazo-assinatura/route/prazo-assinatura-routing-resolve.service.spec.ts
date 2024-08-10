import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IPrazoAssinatura } from '../prazo-assinatura.model';
import { PrazoAssinaturaService } from '../service/prazo-assinatura.service';

import prazoAssinaturaResolve from './prazo-assinatura-routing-resolve.service';

describe('PrazoAssinatura routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: PrazoAssinaturaService;
  let resultPrazoAssinatura: IPrazoAssinatura | null | undefined;

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
    service = TestBed.inject(PrazoAssinaturaService);
    resultPrazoAssinatura = undefined;
  });

  describe('resolve', () => {
    it('should return IPrazoAssinatura returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        prazoAssinaturaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPrazoAssinatura = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPrazoAssinatura).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        prazoAssinaturaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPrazoAssinatura = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPrazoAssinatura).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPrazoAssinatura>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        prazoAssinaturaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPrazoAssinatura = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPrazoAssinatura).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
