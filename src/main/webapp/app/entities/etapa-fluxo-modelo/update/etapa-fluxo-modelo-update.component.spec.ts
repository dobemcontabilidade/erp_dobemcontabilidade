import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IFluxoModelo } from 'app/entities/fluxo-modelo/fluxo-modelo.model';
import { FluxoModeloService } from 'app/entities/fluxo-modelo/service/fluxo-modelo.service';
import { EtapaFluxoModeloService } from '../service/etapa-fluxo-modelo.service';
import { IEtapaFluxoModelo } from '../etapa-fluxo-modelo.model';
import { EtapaFluxoModeloFormService } from './etapa-fluxo-modelo-form.service';

import { EtapaFluxoModeloUpdateComponent } from './etapa-fluxo-modelo-update.component';

describe('EtapaFluxoModelo Management Update Component', () => {
  let comp: EtapaFluxoModeloUpdateComponent;
  let fixture: ComponentFixture<EtapaFluxoModeloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let etapaFluxoModeloFormService: EtapaFluxoModeloFormService;
  let etapaFluxoModeloService: EtapaFluxoModeloService;
  let fluxoModeloService: FluxoModeloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EtapaFluxoModeloUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EtapaFluxoModeloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EtapaFluxoModeloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    etapaFluxoModeloFormService = TestBed.inject(EtapaFluxoModeloFormService);
    etapaFluxoModeloService = TestBed.inject(EtapaFluxoModeloService);
    fluxoModeloService = TestBed.inject(FluxoModeloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call FluxoModelo query and add missing value', () => {
      const etapaFluxoModelo: IEtapaFluxoModelo = { id: 456 };
      const fluxoModelo: IFluxoModelo = { id: 24116 };
      etapaFluxoModelo.fluxoModelo = fluxoModelo;

      const fluxoModeloCollection: IFluxoModelo[] = [{ id: 1013 }];
      jest.spyOn(fluxoModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: fluxoModeloCollection })));
      const additionalFluxoModelos = [fluxoModelo];
      const expectedCollection: IFluxoModelo[] = [...additionalFluxoModelos, ...fluxoModeloCollection];
      jest.spyOn(fluxoModeloService, 'addFluxoModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ etapaFluxoModelo });
      comp.ngOnInit();

      expect(fluxoModeloService.query).toHaveBeenCalled();
      expect(fluxoModeloService.addFluxoModeloToCollectionIfMissing).toHaveBeenCalledWith(
        fluxoModeloCollection,
        ...additionalFluxoModelos.map(expect.objectContaining),
      );
      expect(comp.fluxoModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const etapaFluxoModelo: IEtapaFluxoModelo = { id: 456 };
      const fluxoModelo: IFluxoModelo = { id: 30445 };
      etapaFluxoModelo.fluxoModelo = fluxoModelo;

      activatedRoute.data = of({ etapaFluxoModelo });
      comp.ngOnInit();

      expect(comp.fluxoModelosSharedCollection).toContain(fluxoModelo);
      expect(comp.etapaFluxoModelo).toEqual(etapaFluxoModelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtapaFluxoModelo>>();
      const etapaFluxoModelo = { id: 123 };
      jest.spyOn(etapaFluxoModeloFormService, 'getEtapaFluxoModelo').mockReturnValue(etapaFluxoModelo);
      jest.spyOn(etapaFluxoModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etapaFluxoModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etapaFluxoModelo }));
      saveSubject.complete();

      // THEN
      expect(etapaFluxoModeloFormService.getEtapaFluxoModelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(etapaFluxoModeloService.update).toHaveBeenCalledWith(expect.objectContaining(etapaFluxoModelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtapaFluxoModelo>>();
      const etapaFluxoModelo = { id: 123 };
      jest.spyOn(etapaFluxoModeloFormService, 'getEtapaFluxoModelo').mockReturnValue({ id: null });
      jest.spyOn(etapaFluxoModeloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etapaFluxoModelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etapaFluxoModelo }));
      saveSubject.complete();

      // THEN
      expect(etapaFluxoModeloFormService.getEtapaFluxoModelo).toHaveBeenCalled();
      expect(etapaFluxoModeloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtapaFluxoModelo>>();
      const etapaFluxoModelo = { id: 123 };
      jest.spyOn(etapaFluxoModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etapaFluxoModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(etapaFluxoModeloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFluxoModelo', () => {
      it('Should forward to fluxoModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fluxoModeloService, 'compareFluxoModelo');
        comp.compareFluxoModelo(entity, entity2);
        expect(fluxoModeloService.compareFluxoModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
