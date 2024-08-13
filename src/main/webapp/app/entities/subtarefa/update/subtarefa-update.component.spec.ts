import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefa } from 'app/entities/tarefa/tarefa.model';
import { TarefaService } from 'app/entities/tarefa/service/tarefa.service';
import { SubtarefaService } from '../service/subtarefa.service';
import { ISubtarefa } from '../subtarefa.model';
import { SubtarefaFormService } from './subtarefa-form.service';

import { SubtarefaUpdateComponent } from './subtarefa-update.component';

describe('Subtarefa Management Update Component', () => {
  let comp: SubtarefaUpdateComponent;
  let fixture: ComponentFixture<SubtarefaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subtarefaFormService: SubtarefaFormService;
  let subtarefaService: SubtarefaService;
  let tarefaService: TarefaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SubtarefaUpdateComponent],
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
      .overrideTemplate(SubtarefaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubtarefaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subtarefaFormService = TestBed.inject(SubtarefaFormService);
    subtarefaService = TestBed.inject(SubtarefaService);
    tarefaService = TestBed.inject(TarefaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Tarefa query and add missing value', () => {
      const subtarefa: ISubtarefa = { id: 456 };
      const tarefa: ITarefa = { id: 10020 };
      subtarefa.tarefa = tarefa;

      const tarefaCollection: ITarefa[] = [{ id: 19142 }];
      jest.spyOn(tarefaService, 'query').mockReturnValue(of(new HttpResponse({ body: tarefaCollection })));
      const additionalTarefas = [tarefa];
      const expectedCollection: ITarefa[] = [...additionalTarefas, ...tarefaCollection];
      jest.spyOn(tarefaService, 'addTarefaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subtarefa });
      comp.ngOnInit();

      expect(tarefaService.query).toHaveBeenCalled();
      expect(tarefaService.addTarefaToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaCollection,
        ...additionalTarefas.map(expect.objectContaining),
      );
      expect(comp.tarefasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subtarefa: ISubtarefa = { id: 456 };
      const tarefa: ITarefa = { id: 25664 };
      subtarefa.tarefa = tarefa;

      activatedRoute.data = of({ subtarefa });
      comp.ngOnInit();

      expect(comp.tarefasSharedCollection).toContain(tarefa);
      expect(comp.subtarefa).toEqual(subtarefa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubtarefa>>();
      const subtarefa = { id: 123 };
      jest.spyOn(subtarefaFormService, 'getSubtarefa').mockReturnValue(subtarefa);
      jest.spyOn(subtarefaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subtarefa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subtarefa }));
      saveSubject.complete();

      // THEN
      expect(subtarefaFormService.getSubtarefa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(subtarefaService.update).toHaveBeenCalledWith(expect.objectContaining(subtarefa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubtarefa>>();
      const subtarefa = { id: 123 };
      jest.spyOn(subtarefaFormService, 'getSubtarefa').mockReturnValue({ id: null });
      jest.spyOn(subtarefaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subtarefa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subtarefa }));
      saveSubject.complete();

      // THEN
      expect(subtarefaFormService.getSubtarefa).toHaveBeenCalled();
      expect(subtarefaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubtarefa>>();
      const subtarefa = { id: 123 };
      jest.spyOn(subtarefaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subtarefa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subtarefaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTarefa', () => {
      it('Should forward to tarefaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tarefaService, 'compareTarefa');
        comp.compareTarefa(entity, entity2);
        expect(tarefaService.compareTarefa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
