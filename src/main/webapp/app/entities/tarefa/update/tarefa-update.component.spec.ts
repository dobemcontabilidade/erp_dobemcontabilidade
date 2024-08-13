import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEsfera } from 'app/entities/esfera/esfera.model';
import { EsferaService } from 'app/entities/esfera/service/esfera.service';
import { IFrequencia } from 'app/entities/frequencia/frequencia.model';
import { FrequenciaService } from 'app/entities/frequencia/service/frequencia.service';
import { ICompetencia } from 'app/entities/competencia/competencia.model';
import { CompetenciaService } from 'app/entities/competencia/service/competencia.service';
import { ITarefa } from '../tarefa.model';
import { TarefaService } from '../service/tarefa.service';
import { TarefaFormService } from './tarefa-form.service';

import { TarefaUpdateComponent } from './tarefa-update.component';

describe('Tarefa Management Update Component', () => {
  let comp: TarefaUpdateComponent;
  let fixture: ComponentFixture<TarefaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tarefaFormService: TarefaFormService;
  let tarefaService: TarefaService;
  let esferaService: EsferaService;
  let frequenciaService: FrequenciaService;
  let competenciaService: CompetenciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaUpdateComponent],
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
      .overrideTemplate(TarefaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TarefaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tarefaFormService = TestBed.inject(TarefaFormService);
    tarefaService = TestBed.inject(TarefaService);
    esferaService = TestBed.inject(EsferaService);
    frequenciaService = TestBed.inject(FrequenciaService);
    competenciaService = TestBed.inject(CompetenciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Esfera query and add missing value', () => {
      const tarefa: ITarefa = { id: 456 };
      const esfera: IEsfera = { id: 5720 };
      tarefa.esfera = esfera;

      const esferaCollection: IEsfera[] = [{ id: 31694 }];
      jest.spyOn(esferaService, 'query').mockReturnValue(of(new HttpResponse({ body: esferaCollection })));
      const additionalEsferas = [esfera];
      const expectedCollection: IEsfera[] = [...additionalEsferas, ...esferaCollection];
      jest.spyOn(esferaService, 'addEsferaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefa });
      comp.ngOnInit();

      expect(esferaService.query).toHaveBeenCalled();
      expect(esferaService.addEsferaToCollectionIfMissing).toHaveBeenCalledWith(
        esferaCollection,
        ...additionalEsferas.map(expect.objectContaining),
      );
      expect(comp.esferasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Frequencia query and add missing value', () => {
      const tarefa: ITarefa = { id: 456 };
      const frequencia: IFrequencia = { id: 13989 };
      tarefa.frequencia = frequencia;

      const frequenciaCollection: IFrequencia[] = [{ id: 1339 }];
      jest.spyOn(frequenciaService, 'query').mockReturnValue(of(new HttpResponse({ body: frequenciaCollection })));
      const additionalFrequencias = [frequencia];
      const expectedCollection: IFrequencia[] = [...additionalFrequencias, ...frequenciaCollection];
      jest.spyOn(frequenciaService, 'addFrequenciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefa });
      comp.ngOnInit();

      expect(frequenciaService.query).toHaveBeenCalled();
      expect(frequenciaService.addFrequenciaToCollectionIfMissing).toHaveBeenCalledWith(
        frequenciaCollection,
        ...additionalFrequencias.map(expect.objectContaining),
      );
      expect(comp.frequenciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Competencia query and add missing value', () => {
      const tarefa: ITarefa = { id: 456 };
      const competencia: ICompetencia = { id: 3932 };
      tarefa.competencia = competencia;

      const competenciaCollection: ICompetencia[] = [{ id: 28735 }];
      jest.spyOn(competenciaService, 'query').mockReturnValue(of(new HttpResponse({ body: competenciaCollection })));
      const additionalCompetencias = [competencia];
      const expectedCollection: ICompetencia[] = [...additionalCompetencias, ...competenciaCollection];
      jest.spyOn(competenciaService, 'addCompetenciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefa });
      comp.ngOnInit();

      expect(competenciaService.query).toHaveBeenCalled();
      expect(competenciaService.addCompetenciaToCollectionIfMissing).toHaveBeenCalledWith(
        competenciaCollection,
        ...additionalCompetencias.map(expect.objectContaining),
      );
      expect(comp.competenciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarefa: ITarefa = { id: 456 };
      const esfera: IEsfera = { id: 7459 };
      tarefa.esfera = esfera;
      const frequencia: IFrequencia = { id: 32759 };
      tarefa.frequencia = frequencia;
      const competencia: ICompetencia = { id: 25335 };
      tarefa.competencia = competencia;

      activatedRoute.data = of({ tarefa });
      comp.ngOnInit();

      expect(comp.esferasSharedCollection).toContain(esfera);
      expect(comp.frequenciasSharedCollection).toContain(frequencia);
      expect(comp.competenciasSharedCollection).toContain(competencia);
      expect(comp.tarefa).toEqual(tarefa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefa>>();
      const tarefa = { id: 123 };
      jest.spyOn(tarefaFormService, 'getTarefa').mockReturnValue(tarefa);
      jest.spyOn(tarefaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefa }));
      saveSubject.complete();

      // THEN
      expect(tarefaFormService.getTarefa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tarefaService.update).toHaveBeenCalledWith(expect.objectContaining(tarefa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefa>>();
      const tarefa = { id: 123 };
      jest.spyOn(tarefaFormService, 'getTarefa').mockReturnValue({ id: null });
      jest.spyOn(tarefaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefa }));
      saveSubject.complete();

      // THEN
      expect(tarefaFormService.getTarefa).toHaveBeenCalled();
      expect(tarefaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefa>>();
      const tarefa = { id: 123 };
      jest.spyOn(tarefaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tarefaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEsfera', () => {
      it('Should forward to esferaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(esferaService, 'compareEsfera');
        comp.compareEsfera(entity, entity2);
        expect(esferaService.compareEsfera).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFrequencia', () => {
      it('Should forward to frequenciaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(frequenciaService, 'compareFrequencia');
        comp.compareFrequencia(entity, entity2);
        expect(frequenciaService.compareFrequencia).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCompetencia', () => {
      it('Should forward to competenciaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(competenciaService, 'compareCompetencia');
        comp.compareCompetencia(entity, entity2);
        expect(competenciaService.compareCompetencia).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
