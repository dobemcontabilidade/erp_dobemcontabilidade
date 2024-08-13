import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEtapaFluxoModelo } from 'app/entities/etapa-fluxo-modelo/etapa-fluxo-modelo.model';
import { EtapaFluxoModeloService } from 'app/entities/etapa-fluxo-modelo/service/etapa-fluxo-modelo.service';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IServicoContabilEtapaFluxoModelo } from '../servico-contabil-etapa-fluxo-modelo.model';
import { ServicoContabilEtapaFluxoModeloService } from '../service/servico-contabil-etapa-fluxo-modelo.service';
import { ServicoContabilEtapaFluxoModeloFormService } from './servico-contabil-etapa-fluxo-modelo-form.service';

import { ServicoContabilEtapaFluxoModeloUpdateComponent } from './servico-contabil-etapa-fluxo-modelo-update.component';

describe('ServicoContabilEtapaFluxoModelo Management Update Component', () => {
  let comp: ServicoContabilEtapaFluxoModeloUpdateComponent;
  let fixture: ComponentFixture<ServicoContabilEtapaFluxoModeloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let servicoContabilEtapaFluxoModeloFormService: ServicoContabilEtapaFluxoModeloFormService;
  let servicoContabilEtapaFluxoModeloService: ServicoContabilEtapaFluxoModeloService;
  let etapaFluxoModeloService: EtapaFluxoModeloService;
  let servicoContabilService: ServicoContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServicoContabilEtapaFluxoModeloUpdateComponent],
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
      .overrideTemplate(ServicoContabilEtapaFluxoModeloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServicoContabilEtapaFluxoModeloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    servicoContabilEtapaFluxoModeloFormService = TestBed.inject(ServicoContabilEtapaFluxoModeloFormService);
    servicoContabilEtapaFluxoModeloService = TestBed.inject(ServicoContabilEtapaFluxoModeloService);
    etapaFluxoModeloService = TestBed.inject(EtapaFluxoModeloService);
    servicoContabilService = TestBed.inject(ServicoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EtapaFluxoModelo query and add missing value', () => {
      const servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo = { id: 456 };
      const etapaFluxoModelo: IEtapaFluxoModelo = { id: 25934 };
      servicoContabilEtapaFluxoModelo.etapaFluxoModelo = etapaFluxoModelo;

      const etapaFluxoModeloCollection: IEtapaFluxoModelo[] = [{ id: 6411 }];
      jest.spyOn(etapaFluxoModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: etapaFluxoModeloCollection })));
      const additionalEtapaFluxoModelos = [etapaFluxoModelo];
      const expectedCollection: IEtapaFluxoModelo[] = [...additionalEtapaFluxoModelos, ...etapaFluxoModeloCollection];
      jest.spyOn(etapaFluxoModeloService, 'addEtapaFluxoModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilEtapaFluxoModelo });
      comp.ngOnInit();

      expect(etapaFluxoModeloService.query).toHaveBeenCalled();
      expect(etapaFluxoModeloService.addEtapaFluxoModeloToCollectionIfMissing).toHaveBeenCalledWith(
        etapaFluxoModeloCollection,
        ...additionalEtapaFluxoModelos.map(expect.objectContaining),
      );
      expect(comp.etapaFluxoModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ServicoContabil query and add missing value', () => {
      const servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 9250 };
      servicoContabilEtapaFluxoModelo.servicoContabil = servicoContabil;

      const servicoContabilCollection: IServicoContabil[] = [{ id: 28046 }];
      jest.spyOn(servicoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: servicoContabilCollection })));
      const additionalServicoContabils = [servicoContabil];
      const expectedCollection: IServicoContabil[] = [...additionalServicoContabils, ...servicoContabilCollection];
      jest.spyOn(servicoContabilService, 'addServicoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilEtapaFluxoModelo });
      comp.ngOnInit();

      expect(servicoContabilService.query).toHaveBeenCalled();
      expect(servicoContabilService.addServicoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilCollection,
        ...additionalServicoContabils.map(expect.objectContaining),
      );
      expect(comp.servicoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo = { id: 456 };
      const etapaFluxoModelo: IEtapaFluxoModelo = { id: 29950 };
      servicoContabilEtapaFluxoModelo.etapaFluxoModelo = etapaFluxoModelo;
      const servicoContabil: IServicoContabil = { id: 12896 };
      servicoContabilEtapaFluxoModelo.servicoContabil = servicoContabil;

      activatedRoute.data = of({ servicoContabilEtapaFluxoModelo });
      comp.ngOnInit();

      expect(comp.etapaFluxoModelosSharedCollection).toContain(etapaFluxoModelo);
      expect(comp.servicoContabilsSharedCollection).toContain(servicoContabil);
      expect(comp.servicoContabilEtapaFluxoModelo).toEqual(servicoContabilEtapaFluxoModelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEtapaFluxoModelo>>();
      const servicoContabilEtapaFluxoModelo = { id: 123 };
      jest
        .spyOn(servicoContabilEtapaFluxoModeloFormService, 'getServicoContabilEtapaFluxoModelo')
        .mockReturnValue(servicoContabilEtapaFluxoModelo);
      jest.spyOn(servicoContabilEtapaFluxoModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEtapaFluxoModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilEtapaFluxoModelo }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilEtapaFluxoModeloFormService.getServicoContabilEtapaFluxoModelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(servicoContabilEtapaFluxoModeloService.update).toHaveBeenCalledWith(expect.objectContaining(servicoContabilEtapaFluxoModelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEtapaFluxoModelo>>();
      const servicoContabilEtapaFluxoModelo = { id: 123 };
      jest.spyOn(servicoContabilEtapaFluxoModeloFormService, 'getServicoContabilEtapaFluxoModelo').mockReturnValue({ id: null });
      jest.spyOn(servicoContabilEtapaFluxoModeloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEtapaFluxoModelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilEtapaFluxoModelo }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilEtapaFluxoModeloFormService.getServicoContabilEtapaFluxoModelo).toHaveBeenCalled();
      expect(servicoContabilEtapaFluxoModeloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEtapaFluxoModelo>>();
      const servicoContabilEtapaFluxoModelo = { id: 123 };
      jest.spyOn(servicoContabilEtapaFluxoModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEtapaFluxoModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(servicoContabilEtapaFluxoModeloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEtapaFluxoModelo', () => {
      it('Should forward to etapaFluxoModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(etapaFluxoModeloService, 'compareEtapaFluxoModelo');
        comp.compareEtapaFluxoModelo(entity, entity2);
        expect(etapaFluxoModeloService.compareEtapaFluxoModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareServicoContabil', () => {
      it('Should forward to servicoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilService, 'compareServicoContabil');
        comp.compareServicoContabil(entity, entity2);
        expect(servicoContabilService.compareServicoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
