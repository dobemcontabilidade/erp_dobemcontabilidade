import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAgendaContadorConfig } from '../agenda-contador-config.model';
import { AgendaContadorConfigService } from '../service/agenda-contador-config.service';

import agendaContadorConfigResolve from './agenda-contador-config-routing-resolve.service';

describe('AgendaContadorConfig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AgendaContadorConfigService;
  let resultAgendaContadorConfig: IAgendaContadorConfig | null | undefined;

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
    service = TestBed.inject(AgendaContadorConfigService);
    resultAgendaContadorConfig = undefined;
  });

  describe('resolve', () => {
    it('should return IAgendaContadorConfig returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        agendaContadorConfigResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgendaContadorConfig = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAgendaContadorConfig).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        agendaContadorConfigResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgendaContadorConfig = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAgendaContadorConfig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAgendaContadorConfig>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        agendaContadorConfigResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgendaContadorConfig = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAgendaContadorConfig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
