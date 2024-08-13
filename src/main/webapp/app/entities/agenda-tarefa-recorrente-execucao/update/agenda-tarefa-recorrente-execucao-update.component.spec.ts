import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from 'app/entities/tarefa-recorrente-execucao/service/tarefa-recorrente-execucao.service';
import { AgendaTarefaRecorrenteExecucaoService } from '../service/agenda-tarefa-recorrente-execucao.service';
import { IAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';
import { AgendaTarefaRecorrenteExecucaoFormService } from './agenda-tarefa-recorrente-execucao-form.service';

import { AgendaTarefaRecorrenteExecucaoUpdateComponent } from './agenda-tarefa-recorrente-execucao-update.component';

describe('AgendaTarefaRecorrenteExecucao Management Update Component', () => {
  let comp: AgendaTarefaRecorrenteExecucaoUpdateComponent;
  let fixture: ComponentFixture<AgendaTarefaRecorrenteExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agendaTarefaRecorrenteExecucaoFormService: AgendaTarefaRecorrenteExecucaoFormService;
  let agendaTarefaRecorrenteExecucaoService: AgendaTarefaRecorrenteExecucaoService;
  let tarefaRecorrenteExecucaoService: TarefaRecorrenteExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendaTarefaRecorrenteExecucaoUpdateComponent],
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
      .overrideTemplate(AgendaTarefaRecorrenteExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgendaTarefaRecorrenteExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agendaTarefaRecorrenteExecucaoFormService = TestBed.inject(AgendaTarefaRecorrenteExecucaoFormService);
    agendaTarefaRecorrenteExecucaoService = TestBed.inject(AgendaTarefaRecorrenteExecucaoService);
    tarefaRecorrenteExecucaoService = TestBed.inject(TarefaRecorrenteExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaRecorrenteExecucao query and add missing value', () => {
      const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = { id: 456 };
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 9389 };
      agendaTarefaRecorrenteExecucao.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;

      const tarefaRecorrenteExecucaoCollection: ITarefaRecorrenteExecucao[] = [{ id: 3716 }];
      jest
        .spyOn(tarefaRecorrenteExecucaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: tarefaRecorrenteExecucaoCollection })));
      const additionalTarefaRecorrenteExecucaos = [tarefaRecorrenteExecucao];
      const expectedCollection: ITarefaRecorrenteExecucao[] = [
        ...additionalTarefaRecorrenteExecucaos,
        ...tarefaRecorrenteExecucaoCollection,
      ];
      jest.spyOn(tarefaRecorrenteExecucaoService, 'addTarefaRecorrenteExecucaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agendaTarefaRecorrenteExecucao });
      comp.ngOnInit();

      expect(tarefaRecorrenteExecucaoService.query).toHaveBeenCalled();
      expect(tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaRecorrenteExecucaoCollection,
        ...additionalTarefaRecorrenteExecucaos.map(expect.objectContaining),
      );
      expect(comp.tarefaRecorrenteExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = { id: 456 };
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 11528 };
      agendaTarefaRecorrenteExecucao.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;

      activatedRoute.data = of({ agendaTarefaRecorrenteExecucao });
      comp.ngOnInit();

      expect(comp.tarefaRecorrenteExecucaosSharedCollection).toContain(tarefaRecorrenteExecucao);
      expect(comp.agendaTarefaRecorrenteExecucao).toEqual(agendaTarefaRecorrenteExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaTarefaRecorrenteExecucao>>();
      const agendaTarefaRecorrenteExecucao = { id: 123 };
      jest
        .spyOn(agendaTarefaRecorrenteExecucaoFormService, 'getAgendaTarefaRecorrenteExecucao')
        .mockReturnValue(agendaTarefaRecorrenteExecucao);
      jest.spyOn(agendaTarefaRecorrenteExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaTarefaRecorrenteExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendaTarefaRecorrenteExecucao }));
      saveSubject.complete();

      // THEN
      expect(agendaTarefaRecorrenteExecucaoFormService.getAgendaTarefaRecorrenteExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agendaTarefaRecorrenteExecucaoService.update).toHaveBeenCalledWith(expect.objectContaining(agendaTarefaRecorrenteExecucao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaTarefaRecorrenteExecucao>>();
      const agendaTarefaRecorrenteExecucao = { id: 123 };
      jest.spyOn(agendaTarefaRecorrenteExecucaoFormService, 'getAgendaTarefaRecorrenteExecucao').mockReturnValue({ id: null });
      jest.spyOn(agendaTarefaRecorrenteExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaTarefaRecorrenteExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agendaTarefaRecorrenteExecucao }));
      saveSubject.complete();

      // THEN
      expect(agendaTarefaRecorrenteExecucaoFormService.getAgendaTarefaRecorrenteExecucao).toHaveBeenCalled();
      expect(agendaTarefaRecorrenteExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgendaTarefaRecorrenteExecucao>>();
      const agendaTarefaRecorrenteExecucao = { id: 123 };
      jest.spyOn(agendaTarefaRecorrenteExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agendaTarefaRecorrenteExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agendaTarefaRecorrenteExecucaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTarefaRecorrenteExecucao', () => {
      it('Should forward to tarefaRecorrenteExecucaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tarefaRecorrenteExecucaoService, 'compareTarefaRecorrenteExecucao');
        comp.compareTarefaRecorrenteExecucao(entity, entity2);
        expect(tarefaRecorrenteExecucaoService.compareTarefaRecorrenteExecucao).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
