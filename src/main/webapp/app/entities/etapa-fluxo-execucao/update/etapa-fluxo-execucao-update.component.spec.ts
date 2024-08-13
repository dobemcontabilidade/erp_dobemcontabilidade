import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';
import { OrdemServicoService } from 'app/entities/ordem-servico/service/ordem-servico.service';
import { EtapaFluxoExecucaoService } from '../service/etapa-fluxo-execucao.service';
import { IEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';
import { EtapaFluxoExecucaoFormService } from './etapa-fluxo-execucao-form.service';

import { EtapaFluxoExecucaoUpdateComponent } from './etapa-fluxo-execucao-update.component';

describe('EtapaFluxoExecucao Management Update Component', () => {
  let comp: EtapaFluxoExecucaoUpdateComponent;
  let fixture: ComponentFixture<EtapaFluxoExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let etapaFluxoExecucaoFormService: EtapaFluxoExecucaoFormService;
  let etapaFluxoExecucaoService: EtapaFluxoExecucaoService;
  let ordemServicoService: OrdemServicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EtapaFluxoExecucaoUpdateComponent],
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
      .overrideTemplate(EtapaFluxoExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EtapaFluxoExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    etapaFluxoExecucaoFormService = TestBed.inject(EtapaFluxoExecucaoFormService);
    etapaFluxoExecucaoService = TestBed.inject(EtapaFluxoExecucaoService);
    ordemServicoService = TestBed.inject(OrdemServicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call OrdemServico query and add missing value', () => {
      const etapaFluxoExecucao: IEtapaFluxoExecucao = { id: 456 };
      const ordemServico: IOrdemServico = { id: 25706 };
      etapaFluxoExecucao.ordemServico = ordemServico;

      const ordemServicoCollection: IOrdemServico[] = [{ id: 7711 }];
      jest.spyOn(ordemServicoService, 'query').mockReturnValue(of(new HttpResponse({ body: ordemServicoCollection })));
      const additionalOrdemServicos = [ordemServico];
      const expectedCollection: IOrdemServico[] = [...additionalOrdemServicos, ...ordemServicoCollection];
      jest.spyOn(ordemServicoService, 'addOrdemServicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ etapaFluxoExecucao });
      comp.ngOnInit();

      expect(ordemServicoService.query).toHaveBeenCalled();
      expect(ordemServicoService.addOrdemServicoToCollectionIfMissing).toHaveBeenCalledWith(
        ordemServicoCollection,
        ...additionalOrdemServicos.map(expect.objectContaining),
      );
      expect(comp.ordemServicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const etapaFluxoExecucao: IEtapaFluxoExecucao = { id: 456 };
      const ordemServico: IOrdemServico = { id: 17495 };
      etapaFluxoExecucao.ordemServico = ordemServico;

      activatedRoute.data = of({ etapaFluxoExecucao });
      comp.ngOnInit();

      expect(comp.ordemServicosSharedCollection).toContain(ordemServico);
      expect(comp.etapaFluxoExecucao).toEqual(etapaFluxoExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtapaFluxoExecucao>>();
      const etapaFluxoExecucao = { id: 123 };
      jest.spyOn(etapaFluxoExecucaoFormService, 'getEtapaFluxoExecucao').mockReturnValue(etapaFluxoExecucao);
      jest.spyOn(etapaFluxoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etapaFluxoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etapaFluxoExecucao }));
      saveSubject.complete();

      // THEN
      expect(etapaFluxoExecucaoFormService.getEtapaFluxoExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(etapaFluxoExecucaoService.update).toHaveBeenCalledWith(expect.objectContaining(etapaFluxoExecucao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtapaFluxoExecucao>>();
      const etapaFluxoExecucao = { id: 123 };
      jest.spyOn(etapaFluxoExecucaoFormService, 'getEtapaFluxoExecucao').mockReturnValue({ id: null });
      jest.spyOn(etapaFluxoExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etapaFluxoExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etapaFluxoExecucao }));
      saveSubject.complete();

      // THEN
      expect(etapaFluxoExecucaoFormService.getEtapaFluxoExecucao).toHaveBeenCalled();
      expect(etapaFluxoExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtapaFluxoExecucao>>();
      const etapaFluxoExecucao = { id: 123 };
      jest.spyOn(etapaFluxoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etapaFluxoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(etapaFluxoExecucaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOrdemServico', () => {
      it('Should forward to ordemServicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ordemServicoService, 'compareOrdemServico');
        comp.compareOrdemServico(entity, entity2);
        expect(ordemServicoService.compareOrdemServico).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
