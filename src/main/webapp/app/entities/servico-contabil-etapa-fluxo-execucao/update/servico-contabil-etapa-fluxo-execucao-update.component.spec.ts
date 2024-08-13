import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IEtapaFluxoExecucao } from 'app/entities/etapa-fluxo-execucao/etapa-fluxo-execucao.model';
import { EtapaFluxoExecucaoService } from 'app/entities/etapa-fluxo-execucao/service/etapa-fluxo-execucao.service';
import { IServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';
import { ServicoContabilEtapaFluxoExecucaoService } from '../service/servico-contabil-etapa-fluxo-execucao.service';
import { ServicoContabilEtapaFluxoExecucaoFormService } from './servico-contabil-etapa-fluxo-execucao-form.service';

import { ServicoContabilEtapaFluxoExecucaoUpdateComponent } from './servico-contabil-etapa-fluxo-execucao-update.component';

describe('ServicoContabilEtapaFluxoExecucao Management Update Component', () => {
  let comp: ServicoContabilEtapaFluxoExecucaoUpdateComponent;
  let fixture: ComponentFixture<ServicoContabilEtapaFluxoExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let servicoContabilEtapaFluxoExecucaoFormService: ServicoContabilEtapaFluxoExecucaoFormService;
  let servicoContabilEtapaFluxoExecucaoService: ServicoContabilEtapaFluxoExecucaoService;
  let servicoContabilService: ServicoContabilService;
  let etapaFluxoExecucaoService: EtapaFluxoExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServicoContabilEtapaFluxoExecucaoUpdateComponent],
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
      .overrideTemplate(ServicoContabilEtapaFluxoExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServicoContabilEtapaFluxoExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    servicoContabilEtapaFluxoExecucaoFormService = TestBed.inject(ServicoContabilEtapaFluxoExecucaoFormService);
    servicoContabilEtapaFluxoExecucaoService = TestBed.inject(ServicoContabilEtapaFluxoExecucaoService);
    servicoContabilService = TestBed.inject(ServicoContabilService);
    etapaFluxoExecucaoService = TestBed.inject(EtapaFluxoExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ServicoContabil query and add missing value', () => {
      const servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 28010 };
      servicoContabilEtapaFluxoExecucao.servicoContabil = servicoContabil;

      const servicoContabilCollection: IServicoContabil[] = [{ id: 19243 }];
      jest.spyOn(servicoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: servicoContabilCollection })));
      const additionalServicoContabils = [servicoContabil];
      const expectedCollection: IServicoContabil[] = [...additionalServicoContabils, ...servicoContabilCollection];
      jest.spyOn(servicoContabilService, 'addServicoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilEtapaFluxoExecucao });
      comp.ngOnInit();

      expect(servicoContabilService.query).toHaveBeenCalled();
      expect(servicoContabilService.addServicoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilCollection,
        ...additionalServicoContabils.map(expect.objectContaining),
      );
      expect(comp.servicoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EtapaFluxoExecucao query and add missing value', () => {
      const servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao = { id: 456 };
      const etapaFluxoExecucao: IEtapaFluxoExecucao = { id: 19248 };
      servicoContabilEtapaFluxoExecucao.etapaFluxoExecucao = etapaFluxoExecucao;

      const etapaFluxoExecucaoCollection: IEtapaFluxoExecucao[] = [{ id: 8558 }];
      jest.spyOn(etapaFluxoExecucaoService, 'query').mockReturnValue(of(new HttpResponse({ body: etapaFluxoExecucaoCollection })));
      const additionalEtapaFluxoExecucaos = [etapaFluxoExecucao];
      const expectedCollection: IEtapaFluxoExecucao[] = [...additionalEtapaFluxoExecucaos, ...etapaFluxoExecucaoCollection];
      jest.spyOn(etapaFluxoExecucaoService, 'addEtapaFluxoExecucaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilEtapaFluxoExecucao });
      comp.ngOnInit();

      expect(etapaFluxoExecucaoService.query).toHaveBeenCalled();
      expect(etapaFluxoExecucaoService.addEtapaFluxoExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        etapaFluxoExecucaoCollection,
        ...additionalEtapaFluxoExecucaos.map(expect.objectContaining),
      );
      expect(comp.etapaFluxoExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 24741 };
      servicoContabilEtapaFluxoExecucao.servicoContabil = servicoContabil;
      const etapaFluxoExecucao: IEtapaFluxoExecucao = { id: 16242 };
      servicoContabilEtapaFluxoExecucao.etapaFluxoExecucao = etapaFluxoExecucao;

      activatedRoute.data = of({ servicoContabilEtapaFluxoExecucao });
      comp.ngOnInit();

      expect(comp.servicoContabilsSharedCollection).toContain(servicoContabil);
      expect(comp.etapaFluxoExecucaosSharedCollection).toContain(etapaFluxoExecucao);
      expect(comp.servicoContabilEtapaFluxoExecucao).toEqual(servicoContabilEtapaFluxoExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEtapaFluxoExecucao>>();
      const servicoContabilEtapaFluxoExecucao = { id: 123 };
      jest
        .spyOn(servicoContabilEtapaFluxoExecucaoFormService, 'getServicoContabilEtapaFluxoExecucao')
        .mockReturnValue(servicoContabilEtapaFluxoExecucao);
      jest.spyOn(servicoContabilEtapaFluxoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEtapaFluxoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilEtapaFluxoExecucao }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilEtapaFluxoExecucaoFormService.getServicoContabilEtapaFluxoExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(servicoContabilEtapaFluxoExecucaoService.update).toHaveBeenCalledWith(
        expect.objectContaining(servicoContabilEtapaFluxoExecucao),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEtapaFluxoExecucao>>();
      const servicoContabilEtapaFluxoExecucao = { id: 123 };
      jest.spyOn(servicoContabilEtapaFluxoExecucaoFormService, 'getServicoContabilEtapaFluxoExecucao').mockReturnValue({ id: null });
      jest.spyOn(servicoContabilEtapaFluxoExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEtapaFluxoExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilEtapaFluxoExecucao }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilEtapaFluxoExecucaoFormService.getServicoContabilEtapaFluxoExecucao).toHaveBeenCalled();
      expect(servicoContabilEtapaFluxoExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEtapaFluxoExecucao>>();
      const servicoContabilEtapaFluxoExecucao = { id: 123 };
      jest.spyOn(servicoContabilEtapaFluxoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEtapaFluxoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(servicoContabilEtapaFluxoExecucaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareServicoContabil', () => {
      it('Should forward to servicoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilService, 'compareServicoContabil');
        comp.compareServicoContabil(entity, entity2);
        expect(servicoContabilService.compareServicoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEtapaFluxoExecucao', () => {
      it('Should forward to etapaFluxoExecucaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(etapaFluxoExecucaoService, 'compareEtapaFluxoExecucao');
        comp.compareEtapaFluxoExecucao(entity, entity2);
        expect(etapaFluxoExecucaoService.compareEtapaFluxoExecucao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
