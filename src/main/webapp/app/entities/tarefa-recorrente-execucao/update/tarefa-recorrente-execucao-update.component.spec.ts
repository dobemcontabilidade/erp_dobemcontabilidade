import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaRecorrente } from 'app/entities/tarefa-recorrente/tarefa-recorrente.model';
import { TarefaRecorrenteService } from 'app/entities/tarefa-recorrente/service/tarefa-recorrente.service';
import { TarefaRecorrenteExecucaoService } from '../service/tarefa-recorrente-execucao.service';
import { ITarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoFormService } from './tarefa-recorrente-execucao-form.service';

import { TarefaRecorrenteExecucaoUpdateComponent } from './tarefa-recorrente-execucao-update.component';

describe('TarefaRecorrenteExecucao Management Update Component', () => {
  let comp: TarefaRecorrenteExecucaoUpdateComponent;
  let fixture: ComponentFixture<TarefaRecorrenteExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tarefaRecorrenteExecucaoFormService: TarefaRecorrenteExecucaoFormService;
  let tarefaRecorrenteExecucaoService: TarefaRecorrenteExecucaoService;
  let tarefaRecorrenteService: TarefaRecorrenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaRecorrenteExecucaoUpdateComponent],
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
      .overrideTemplate(TarefaRecorrenteExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TarefaRecorrenteExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tarefaRecorrenteExecucaoFormService = TestBed.inject(TarefaRecorrenteExecucaoFormService);
    tarefaRecorrenteExecucaoService = TestBed.inject(TarefaRecorrenteExecucaoService);
    tarefaRecorrenteService = TestBed.inject(TarefaRecorrenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaRecorrente query and add missing value', () => {
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 456 };
      const tarefaRecorrente: ITarefaRecorrente = { id: 13584 };
      tarefaRecorrenteExecucao.tarefaRecorrente = tarefaRecorrente;

      const tarefaRecorrenteCollection: ITarefaRecorrente[] = [{ id: 13093 }];
      jest.spyOn(tarefaRecorrenteService, 'query').mockReturnValue(of(new HttpResponse({ body: tarefaRecorrenteCollection })));
      const additionalTarefaRecorrentes = [tarefaRecorrente];
      const expectedCollection: ITarefaRecorrente[] = [...additionalTarefaRecorrentes, ...tarefaRecorrenteCollection];
      jest.spyOn(tarefaRecorrenteService, 'addTarefaRecorrenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaRecorrenteExecucao });
      comp.ngOnInit();

      expect(tarefaRecorrenteService.query).toHaveBeenCalled();
      expect(tarefaRecorrenteService.addTarefaRecorrenteToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaRecorrenteCollection,
        ...additionalTarefaRecorrentes.map(expect.objectContaining),
      );
      expect(comp.tarefaRecorrentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 456 };
      const tarefaRecorrente: ITarefaRecorrente = { id: 15239 };
      tarefaRecorrenteExecucao.tarefaRecorrente = tarefaRecorrente;

      activatedRoute.data = of({ tarefaRecorrenteExecucao });
      comp.ngOnInit();

      expect(comp.tarefaRecorrentesSharedCollection).toContain(tarefaRecorrente);
      expect(comp.tarefaRecorrenteExecucao).toEqual(tarefaRecorrenteExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrenteExecucao>>();
      const tarefaRecorrenteExecucao = { id: 123 };
      jest.spyOn(tarefaRecorrenteExecucaoFormService, 'getTarefaRecorrenteExecucao').mockReturnValue(tarefaRecorrenteExecucao);
      jest.spyOn(tarefaRecorrenteExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrenteExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaRecorrenteExecucao }));
      saveSubject.complete();

      // THEN
      expect(tarefaRecorrenteExecucaoFormService.getTarefaRecorrenteExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tarefaRecorrenteExecucaoService.update).toHaveBeenCalledWith(expect.objectContaining(tarefaRecorrenteExecucao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrenteExecucao>>();
      const tarefaRecorrenteExecucao = { id: 123 };
      jest.spyOn(tarefaRecorrenteExecucaoFormService, 'getTarefaRecorrenteExecucao').mockReturnValue({ id: null });
      jest.spyOn(tarefaRecorrenteExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrenteExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaRecorrenteExecucao }));
      saveSubject.complete();

      // THEN
      expect(tarefaRecorrenteExecucaoFormService.getTarefaRecorrenteExecucao).toHaveBeenCalled();
      expect(tarefaRecorrenteExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrenteExecucao>>();
      const tarefaRecorrenteExecucao = { id: 123 };
      jest.spyOn(tarefaRecorrenteExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrenteExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tarefaRecorrenteExecucaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTarefaRecorrente', () => {
      it('Should forward to tarefaRecorrenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tarefaRecorrenteService, 'compareTarefaRecorrente');
        comp.compareTarefaRecorrente(entity, entity2);
        expect(tarefaRecorrenteService.compareTarefaRecorrente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
