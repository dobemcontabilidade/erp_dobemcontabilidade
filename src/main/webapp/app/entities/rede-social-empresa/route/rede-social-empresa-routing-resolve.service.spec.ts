import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IRedeSocialEmpresa } from '../rede-social-empresa.model';
import { RedeSocialEmpresaService } from '../service/rede-social-empresa.service';

import redeSocialEmpresaResolve from './rede-social-empresa-routing-resolve.service';

describe('RedeSocialEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: RedeSocialEmpresaService;
  let resultRedeSocialEmpresa: IRedeSocialEmpresa | null | undefined;

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
    service = TestBed.inject(RedeSocialEmpresaService);
    resultRedeSocialEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IRedeSocialEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        redeSocialEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultRedeSocialEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultRedeSocialEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        redeSocialEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultRedeSocialEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRedeSocialEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IRedeSocialEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        redeSocialEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultRedeSocialEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultRedeSocialEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
