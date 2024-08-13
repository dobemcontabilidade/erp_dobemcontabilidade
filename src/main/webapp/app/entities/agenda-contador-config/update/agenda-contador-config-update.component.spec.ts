import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAgendaTarefaRecorrenteExecucao } from 'app/entities/agenda-tarefa-recorrente-execucao/agenda-tarefa-recorrente-execucao.model';
import { AgendaTarefaRecorrenteExecucaoService } from 'app/entities/agenda-tarefa-recorrente-execucao/service/agenda-tarefa-recorrente-execucao.service';
import { IAgendaTarefaOrdemServicoExecucao } from 'app/entities/agenda-tarefa-ordem-servico-execucao/agenda-tarefa-ordem-servico-execucao.model';
import { AgendaTarefaOrdemServicoExecucaoService } from 'app/entities/agenda-tarefa-ordem-servico-execucao/service/agenda-tarefa-ordem-servico-execucao.service';
import { IAgendaContadorConfig } from '../agenda-contador-config.model';
import { AgendaContadorConfigService } from '../service/agenda-contador-config.service';
import { AgendaContadorConfigFormService } from './agenda-contador-config-form.service';

import { AgendaContadorConfigUpdateComponent } from './agenda-contador-config-update.component';

describe('AgendaContadorConfig Management Update Component', () => {
  let comp: AgendaContadorConfigUpdateComponent;
  let fixture: ComponentFixture<AgendaContadorConfigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agendaContadorConfigFormService: AgendaContadorConfigFormService;
  let agendaContadorConfigService: AgendaContadorConfigService;
  let agendaTarefaRecorrenteExecucaoService: AgendaTarefaRecorrenteExecucaoService;
  let agendaTarefaOrdemServicoExecucaoService: AgendaTarefaOrdemServicoExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendaContadorConfigUpdateComponent],
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
      .overrideTemplate(AgendaContadorConfigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgendaContadorConfigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agendaContadorConfigFormService = TestBed.inject(AgendaContadorConfigFormService);
    agendaContadorConfigService = TestBed.inject(AgendaContadorConfigService);
    agendaTarefaRecorrenteExecucaoService = TestBed.inject(AgendaTarefaRecorrenteExecucaoService);
    agendaTarefaOrdemServicoExecucaoService = TestBed.inject(AgendaTarefaOrdemServicoExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AgendaTarefaRecorrenteExecucao query and add missing value', () => {
      const agendaContadorConfig: IAgendaContadorConfig = { id: 456 };
      const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = { id: 29182 };
      agendaContadorConfig.agendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucao;

      const agendaTarefaRecorrenteExecucaoCollection: IAgendaTarefaRecorrenteExecucao[] = [{ id: 27452 }];
      jest
        .spyOn(agendaTarefaRecorrenteExecucaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: agendaTarefaRecorrenteExecucaoCollection })));
      const additionalAgendaTarefaRecorrenteExecucaos = [agendaTarefaRecorrenteExecucao];
      const expectedCollection: IAgendaTarefaRecorrenteExecucao[] = [
        ...additionalAgendaTarefaRecorrenteExecucaos,
        ...agendaTarefaRecorrenteExecucaoCollection,
      ];
      jest
        .spyOn(agendaTarefaRecorrenteExecucaoService, 'addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agendaContadorConfig });
      comp.ngOnInit();

      expect(agendaTarefaRecorrenteExecucaoService.query).toHaveBeenCalled();
      expect(agendaTarefaRecorrenteExecucaoService.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        agendaTarefaRecorrenteExecucaoCollection,
        ...additionalAgendaTarefaRecorrenteExecucaos.map(expect.objectContaining),
      );
      expect(comp.agendaTarefaRecorrenteExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AgendaTarefaOrdemServicoExecucao query and add missing value', () => {
      const agendaContadorConfig: IAgendaContadorConfig = { id: 456 };
      const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = { id: 26786 };
      agendaContadorConfig.agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucao;

      const agendaTarefaOrdemServicoExecucaoCollection: IAgendaTarefaOrdemServicoExecucao[] = [{ id: 32148 }];
      jest
        .spyOn(agendaTarefaOrdemServicoExecucaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: agendaTarefaOrdemServicoExecucaoCollection })));
      const additionalAgendaTarefaOrdemServicoExecucaos = [agendaTarefaOrdemServicoExecucao];
      const expectedCollection: IAgendaTarefaOrdemServicoExecucao[] = [
        ...additionalAgendaTarefaOrdemServicoExecucaos,
        ...agendaTarefaOrdemServicoExecucaoCollection,
      ];
      jest
        .spyOn(agendaTarefaOrdemServicoExecucaoService, 'addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agendaContadorConfig });
      comp.ngOnInit();

      expect(agendaTarefaOrdemServicoExecucaoService.query).toHaveBeenCalled();
      expect(agendaTarefaOrdemServicoExecucaoService.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        agendaTarefaOrdemServicoExecucaoCollection,
        ...additionalAgendaTarefaOrdemServicoExecucaos.map(expect.objectContaining),
      );
      expect(comp.agendaTarefaOrdemServicoExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agendaContadorConfig: IAgendaContadorConfig = { id: 456 };
      const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = { id: 17687 };
      agendaContadorConfig.agendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucao;
      const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = { id: 23620 };
      agendaContadorConfig.agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucao;

      activatedRoute.data = of({ agendaContadorConfig });
      comp.ngOnInit();

      expect(comp.agendaTarefaRecorrenteExecucaosSharedCollection).toContain(agendaTarefaRecorrenteExecucao);
      expect(comp.agendaTarefaOrdemServicoExecucaosSharedCollection).toContain(agendaTarefaOrdemServicoExecucao);
      expect(comp.agendaContadorConfig).toEqual(agendaContadorConfig);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaContadorConfig>>();
      const agendaContadorConfig = { id: 123 };
      jest.spyOn(agendaContadorConfigFormService, 'getAgendaContadorConfig').mockReturnValue(agendaContadorConfig);
      jest.spyOn(agendaContadorConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaContadorConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendaContadorConfig }));
      saveSubject.complete();

      // THEN
      expect(agendaContadorConfigFormService.getAgendaContadorConfig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agendaContadorConfigService.update).toHaveBeenCalledWith(expect.objectContaining(agendaContadorConfig));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaContadorConfig>>();
      const agendaContadorConfig = { id: 123 };
      jest.spyOn(agendaContadorConfigFormService, 'getAgendaContadorConfig').mockReturnValue({ id: null });
      jest.spyOn(agendaContadorConfigService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaContadorConfig: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendaContadorConfig }));
      saveSubject.complete();

      // THEN
      expect(agendaContadorConfigFormService.getAgendaContadorConfig).toHaveBeenCalled();
      expect(agendaContadorConfigService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaContadorConfig>>();
      const agendaContadorConfig = { id: 123 };
      jest.spyOn(agendaContadorConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaContadorConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agendaContadorConfigService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAgendaTarefaRecorrenteExecucao', () => {
      it('Should forward to agendaTarefaRecorrenteExecucaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(agendaTarefaRecorrenteExecucaoService, 'compareAgendaTarefaRecorrenteExecucao');
        comp.compareAgendaTarefaRecorrenteExecucao(entity, entity2);
        expect(agendaTarefaRecorrenteExecucaoService.compareAgendaTarefaRecorrenteExecucao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAgendaTarefaOrdemServicoExecucao', () => {
      it('Should forward to agendaTarefaOrdemServicoExecucaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(agendaTarefaOrdemServicoExecucaoService, 'compareAgendaTarefaOrdemServicoExecucao');
        comp.compareAgendaTarefaOrdemServicoExecucao(entity, entity2);
        expect(agendaTarefaOrdemServicoExecucaoService.compareAgendaTarefaOrdemServicoExecucao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
