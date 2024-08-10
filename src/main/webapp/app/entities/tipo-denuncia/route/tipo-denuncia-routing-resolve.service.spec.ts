import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITipoDenuncia } from '../tipo-denuncia.model';
import { TipoDenunciaService } from '../service/tipo-denuncia.service';

import tipoDenunciaResolve from './tipo-denuncia-routing-resolve.service';

describe('TipoDenuncia routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TipoDenunciaService;
  let resultTipoDenuncia: ITipoDenuncia | null | undefined;

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
    service = TestBed.inject(TipoDenunciaService);
    resultTipoDenuncia = undefined;
  });

  describe('resolve', () => {
    it('should return ITipoDenuncia returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        tipoDenunciaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTipoDenuncia = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTipoDenuncia).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        tipoDenunciaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTipoDenuncia = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTipoDenuncia).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITipoDenuncia>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        tipoDenunciaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTipoDenuncia = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTipoDenuncia).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
