import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from 'app/entities/tarefa-recorrente-execucao/service/tarefa-recorrente-execucao.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IContadorResponsavelTarefaRecorrente } from '../contador-responsavel-tarefa-recorrente.model';
import { ContadorResponsavelTarefaRecorrenteService } from '../service/contador-responsavel-tarefa-recorrente.service';
import { ContadorResponsavelTarefaRecorrenteFormService } from './contador-responsavel-tarefa-recorrente-form.service';

import { ContadorResponsavelTarefaRecorrenteUpdateComponent } from './contador-responsavel-tarefa-recorrente-update.component';

describe('ContadorResponsavelTarefaRecorrente Management Update Component', () => {
  let comp: ContadorResponsavelTarefaRecorrenteUpdateComponent;
  let fixture: ComponentFixture<ContadorResponsavelTarefaRecorrenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contadorResponsavelTarefaRecorrenteFormService: ContadorResponsavelTarefaRecorrenteFormService;
  let contadorResponsavelTarefaRecorrenteService: ContadorResponsavelTarefaRecorrenteService;
  let tarefaRecorrenteExecucaoService: TarefaRecorrenteExecucaoService;
  let contadorService: ContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContadorResponsavelTarefaRecorrenteUpdateComponent],
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
      .overrideTemplate(ContadorResponsavelTarefaRecorrenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContadorResponsavelTarefaRecorrenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contadorResponsavelTarefaRecorrenteFormService = TestBed.inject(ContadorResponsavelTarefaRecorrenteFormService);
    contadorResponsavelTarefaRecorrenteService = TestBed.inject(ContadorResponsavelTarefaRecorrenteService);
    tarefaRecorrenteExecucaoService = TestBed.inject(TarefaRecorrenteExecucaoService);
    contadorService = TestBed.inject(ContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaRecorrenteExecucao query and add missing value', () => {
      const contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente = { id: 456 };
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 30246 };
      contadorResponsavelTarefaRecorrente.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;

      const tarefaRecorrenteExecucaoCollection: ITarefaRecorrenteExecucao[] = [{ id: 696 }];
      jest
        .spyOn(tarefaRecorrenteExecucaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: tarefaRecorrenteExecucaoCollection })));
      const additionalTarefaRecorrenteExecucaos = [tarefaRecorrenteExecucao];
      const expectedCollection: ITarefaRecorrenteExecucao[] = [
        ...additionalTarefaRecorrenteExecucaos,
        ...tarefaRecorrenteExecucaoCollection,
      ];
      jest.spyOn(tarefaRecorrenteExecucaoService, 'addTarefaRecorrenteExecucaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contadorResponsavelTarefaRecorrente });
      comp.ngOnInit();

      expect(tarefaRecorrenteExecucaoService.query).toHaveBeenCalled();
      expect(tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaRecorrenteExecucaoCollection,
        ...additionalTarefaRecorrenteExecucaos.map(expect.objectContaining),
      );
      expect(comp.tarefaRecorrenteExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Contador query and add missing value', () => {
      const contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente = { id: 456 };
      const contador: IContador = { id: 17879 };
      contadorResponsavelTarefaRecorrente.contador = contador;

      const contadorCollection: IContador[] = [{ id: 9816 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contadorResponsavelTarefaRecorrente });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente = { id: 456 };
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 25808 };
      contadorResponsavelTarefaRecorrente.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;
      const contador: IContador = { id: 78 };
      contadorResponsavelTarefaRecorrente.contador = contador;

      activatedRoute.data = of({ contadorResponsavelTarefaRecorrente });
      comp.ngOnInit();

      expect(comp.tarefaRecorrenteExecucaosSharedCollection).toContain(tarefaRecorrenteExecucao);
      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.contadorResponsavelTarefaRecorrente).toEqual(contadorResponsavelTarefaRecorrente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContadorResponsavelTarefaRecorrente>>();
      const contadorResponsavelTarefaRecorrente = { id: 123 };
      jest
        .spyOn(contadorResponsavelTarefaRecorrenteFormService, 'getContadorResponsavelTarefaRecorrente')
        .mockReturnValue(contadorResponsavelTarefaRecorrente);
      jest.spyOn(contadorResponsavelTarefaRecorrenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contadorResponsavelTarefaRecorrente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contadorResponsavelTarefaRecorrente }));
      saveSubject.complete();

      // THEN
      expect(contadorResponsavelTarefaRecorrenteFormService.getContadorResponsavelTarefaRecorrente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contadorResponsavelTarefaRecorrenteService.update).toHaveBeenCalledWith(
        expect.objectContaining(contadorResponsavelTarefaRecorrente),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContadorResponsavelTarefaRecorrente>>();
      const contadorResponsavelTarefaRecorrente = { id: 123 };
      jest.spyOn(contadorResponsavelTarefaRecorrenteFormService, 'getContadorResponsavelTarefaRecorrente').mockReturnValue({ id: null });
      jest.spyOn(contadorResponsavelTarefaRecorrenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contadorResponsavelTarefaRecorrente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contadorResponsavelTarefaRecorrente }));
      saveSubject.complete();

      // THEN
      expect(contadorResponsavelTarefaRecorrenteFormService.getContadorResponsavelTarefaRecorrente).toHaveBeenCalled();
      expect(contadorResponsavelTarefaRecorrenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContadorResponsavelTarefaRecorrente>>();
      const contadorResponsavelTarefaRecorrente = { id: 123 };
      jest.spyOn(contadorResponsavelTarefaRecorrenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contadorResponsavelTarefaRecorrente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contadorResponsavelTarefaRecorrenteService.update).toHaveBeenCalled();
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

    describe('compareContador', () => {
      it('Should forward to contadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(contadorService, 'compareContador');
        comp.compareContador(entity, entity2);
        expect(contadorService.compareContador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
