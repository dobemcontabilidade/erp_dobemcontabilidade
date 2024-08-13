import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from 'app/entities/tarefa-ordem-servico-execucao/service/tarefa-ordem-servico-execucao.service';
import { AgendaTarefaOrdemServicoExecucaoService } from '../service/agenda-tarefa-ordem-servico-execucao.service';
import { IAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';
import { AgendaTarefaOrdemServicoExecucaoFormService } from './agenda-tarefa-ordem-servico-execucao-form.service';

import { AgendaTarefaOrdemServicoExecucaoUpdateComponent } from './agenda-tarefa-ordem-servico-execucao-update.component';

describe('AgendaTarefaOrdemServicoExecucao Management Update Component', () => {
  let comp: AgendaTarefaOrdemServicoExecucaoUpdateComponent;
  let fixture: ComponentFixture<AgendaTarefaOrdemServicoExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agendaTarefaOrdemServicoExecucaoFormService: AgendaTarefaOrdemServicoExecucaoFormService;
  let agendaTarefaOrdemServicoExecucaoService: AgendaTarefaOrdemServicoExecucaoService;
  let tarefaOrdemServicoExecucaoService: TarefaOrdemServicoExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendaTarefaOrdemServicoExecucaoUpdateComponent],
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
      .overrideTemplate(AgendaTarefaOrdemServicoExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgendaTarefaOrdemServicoExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agendaTarefaOrdemServicoExecucaoFormService = TestBed.inject(AgendaTarefaOrdemServicoExecucaoFormService);
    agendaTarefaOrdemServicoExecucaoService = TestBed.inject(AgendaTarefaOrdemServicoExecucaoService);
    tarefaOrdemServicoExecucaoService = TestBed.inject(TarefaOrdemServicoExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaOrdemServicoExecucao query and add missing value', () => {
      const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = { id: 456 };
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 3174 };
      agendaTarefaOrdemServicoExecucao.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;

      const tarefaOrdemServicoExecucaoCollection: ITarefaOrdemServicoExecucao[] = [{ id: 23461 }];
      jest
        .spyOn(tarefaOrdemServicoExecucaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: tarefaOrdemServicoExecucaoCollection })));
      const additionalTarefaOrdemServicoExecucaos = [tarefaOrdemServicoExecucao];
      const expectedCollection: ITarefaOrdemServicoExecucao[] = [
        ...additionalTarefaOrdemServicoExecucaos,
        ...tarefaOrdemServicoExecucaoCollection,
      ];
      jest
        .spyOn(tarefaOrdemServicoExecucaoService, 'addTarefaOrdemServicoExecucaoToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agendaTarefaOrdemServicoExecucao });
      comp.ngOnInit();

      expect(tarefaOrdemServicoExecucaoService.query).toHaveBeenCalled();
      expect(tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaOrdemServicoExecucaoCollection,
        ...additionalTarefaOrdemServicoExecucaos.map(expect.objectContaining),
      );
      expect(comp.tarefaOrdemServicoExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = { id: 456 };
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 2039 };
      agendaTarefaOrdemServicoExecucao.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;

      activatedRoute.data = of({ agendaTarefaOrdemServicoExecucao });
      comp.ngOnInit();

      expect(comp.tarefaOrdemServicoExecucaosSharedCollection).toContain(tarefaOrdemServicoExecucao);
      expect(comp.agendaTarefaOrdemServicoExecucao).toEqual(agendaTarefaOrdemServicoExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaTarefaOrdemServicoExecucao>>();
      const agendaTarefaOrdemServicoExecucao = { id: 123 };
      jest
        .spyOn(agendaTarefaOrdemServicoExecucaoFormService, 'getAgendaTarefaOrdemServicoExecucao')
        .mockReturnValue(agendaTarefaOrdemServicoExecucao);
      jest.spyOn(agendaTarefaOrdemServicoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaTarefaOrdemServicoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendaTarefaOrdemServicoExecucao }));
      saveSubject.complete();

      // THEN
      expect(agendaTarefaOrdemServicoExecucaoFormService.getAgendaTarefaOrdemServicoExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agendaTarefaOrdemServicoExecucaoService.update).toHaveBeenCalledWith(
        expect.objectContaining(agendaTarefaOrdemServicoExecucao),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaTarefaOrdemServicoExecucao>>();
      const agendaTarefaOrdemServicoExecucao = { id: 123 };
      jest.spyOn(agendaTarefaOrdemServicoExecucaoFormService, 'getAgendaTarefaOrdemServicoExecucao').mockReturnValue({ id: null });
      jest.spyOn(agendaTarefaOrdemServicoExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaTarefaOrdemServicoExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendaTarefaOrdemServicoExecucao }));
      saveSubject.complete();

      // THEN
      expect(agendaTarefaOrdemServicoExecucaoFormService.getAgendaTarefaOrdemServicoExecucao).toHaveBeenCalled();
      expect(agendaTarefaOrdemServicoExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaTarefaOrdemServicoExecucao>>();
      const agendaTarefaOrdemServicoExecucao = { id: 123 };
      jest.spyOn(agendaTarefaOrdemServicoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaTarefaOrdemServicoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agendaTarefaOrdemServicoExecucaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTarefaOrdemServicoExecucao', () => {
      it('Should forward to tarefaOrdemServicoExecucaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tarefaOrdemServicoExecucaoService, 'compareTarefaOrdemServicoExecucao');
        comp.compareTarefaOrdemServicoExecucao(entity, entity2);
        expect(tarefaOrdemServicoExecucaoService.compareTarefaOrdemServicoExecucao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
