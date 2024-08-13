import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from 'app/entities/tarefa-recorrente-execucao/service/tarefa-recorrente-execucao.service';
import { SubTarefaRecorrenteService } from '../service/sub-tarefa-recorrente.service';
import { ISubTarefaRecorrente } from '../sub-tarefa-recorrente.model';
import { SubTarefaRecorrenteFormService } from './sub-tarefa-recorrente-form.service';

import { SubTarefaRecorrenteUpdateComponent } from './sub-tarefa-recorrente-update.component';

describe('SubTarefaRecorrente Management Update Component', () => {
  let comp: SubTarefaRecorrenteUpdateComponent;
  let fixture: ComponentFixture<SubTarefaRecorrenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subTarefaRecorrenteFormService: SubTarefaRecorrenteFormService;
  let subTarefaRecorrenteService: SubTarefaRecorrenteService;
  let tarefaRecorrenteExecucaoService: TarefaRecorrenteExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SubTarefaRecorrenteUpdateComponent],
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
      .overrideTemplate(SubTarefaRecorrenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubTarefaRecorrenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subTarefaRecorrenteFormService = TestBed.inject(SubTarefaRecorrenteFormService);
    subTarefaRecorrenteService = TestBed.inject(SubTarefaRecorrenteService);
    tarefaRecorrenteExecucaoService = TestBed.inject(TarefaRecorrenteExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaRecorrenteExecucao query and add missing value', () => {
      const subTarefaRecorrente: ISubTarefaRecorrente = { id: 456 };
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 12535 };
      subTarefaRecorrente.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;

      const tarefaRecorrenteExecucaoCollection: ITarefaRecorrenteExecucao[] = [{ id: 7934 }];
      jest
        .spyOn(tarefaRecorrenteExecucaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: tarefaRecorrenteExecucaoCollection })));
      const additionalTarefaRecorrenteExecucaos = [tarefaRecorrenteExecucao];
      const expectedCollection: ITarefaRecorrenteExecucao[] = [
        ...additionalTarefaRecorrenteExecucaos,
        ...tarefaRecorrenteExecucaoCollection,
      ];
      jest.spyOn(tarefaRecorrenteExecucaoService, 'addTarefaRecorrenteExecucaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subTarefaRecorrente });
      comp.ngOnInit();

      expect(tarefaRecorrenteExecucaoService.query).toHaveBeenCalled();
      expect(tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaRecorrenteExecucaoCollection,
        ...additionalTarefaRecorrenteExecucaos.map(expect.objectContaining),
      );
      expect(comp.tarefaRecorrenteExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subTarefaRecorrente: ISubTarefaRecorrente = { id: 456 };
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 19118 };
      subTarefaRecorrente.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;

      activatedRoute.data = of({ subTarefaRecorrente });
      comp.ngOnInit();

      expect(comp.tarefaRecorrenteExecucaosSharedCollection).toContain(tarefaRecorrenteExecucao);
      expect(comp.subTarefaRecorrente).toEqual(subTarefaRecorrente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubTarefaRecorrente>>();
      const subTarefaRecorrente = { id: 123 };
      jest.spyOn(subTarefaRecorrenteFormService, 'getSubTarefaRecorrente').mockReturnValue(subTarefaRecorrente);
      jest.spyOn(subTarefaRecorrenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subTarefaRecorrente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subTarefaRecorrente }));
      saveSubject.complete();

      // THEN
      expect(subTarefaRecorrenteFormService.getSubTarefaRecorrente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(subTarefaRecorrenteService.update).toHaveBeenCalledWith(expect.objectContaining(subTarefaRecorrente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubTarefaRecorrente>>();
      const subTarefaRecorrente = { id: 123 };
      jest.spyOn(subTarefaRecorrenteFormService, 'getSubTarefaRecorrente').mockReturnValue({ id: null });
      jest.spyOn(subTarefaRecorrenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subTarefaRecorrente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subTarefaRecorrente }));
      saveSubject.complete();

      // THEN
      expect(subTarefaRecorrenteFormService.getSubTarefaRecorrente).toHaveBeenCalled();
      expect(subTarefaRecorrenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubTarefaRecorrente>>();
      const subTarefaRecorrente = { id: 123 };
      jest.spyOn(subTarefaRecorrenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subTarefaRecorrente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subTarefaRecorrenteService.update).toHaveBeenCalled();
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
