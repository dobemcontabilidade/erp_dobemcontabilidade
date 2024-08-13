import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from 'app/entities/tarefa-ordem-servico-execucao/service/tarefa-ordem-servico-execucao.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IContadorResponsavelOrdemServico } from '../contador-responsavel-ordem-servico.model';
import { ContadorResponsavelOrdemServicoService } from '../service/contador-responsavel-ordem-servico.service';
import { ContadorResponsavelOrdemServicoFormService } from './contador-responsavel-ordem-servico-form.service';

import { ContadorResponsavelOrdemServicoUpdateComponent } from './contador-responsavel-ordem-servico-update.component';

describe('ContadorResponsavelOrdemServico Management Update Component', () => {
  let comp: ContadorResponsavelOrdemServicoUpdateComponent;
  let fixture: ComponentFixture<ContadorResponsavelOrdemServicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contadorResponsavelOrdemServicoFormService: ContadorResponsavelOrdemServicoFormService;
  let contadorResponsavelOrdemServicoService: ContadorResponsavelOrdemServicoService;
  let tarefaOrdemServicoExecucaoService: TarefaOrdemServicoExecucaoService;
  let contadorService: ContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContadorResponsavelOrdemServicoUpdateComponent],
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
      .overrideTemplate(ContadorResponsavelOrdemServicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContadorResponsavelOrdemServicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contadorResponsavelOrdemServicoFormService = TestBed.inject(ContadorResponsavelOrdemServicoFormService);
    contadorResponsavelOrdemServicoService = TestBed.inject(ContadorResponsavelOrdemServicoService);
    tarefaOrdemServicoExecucaoService = TestBed.inject(TarefaOrdemServicoExecucaoService);
    contadorService = TestBed.inject(ContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaOrdemServicoExecucao query and add missing value', () => {
      const contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico = { id: 456 };
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 25393 };
      contadorResponsavelOrdemServico.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;

      const tarefaOrdemServicoExecucaoCollection: ITarefaOrdemServicoExecucao[] = [{ id: 9362 }];
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

      activatedRoute.data = of({ contadorResponsavelOrdemServico });
      comp.ngOnInit();

      expect(tarefaOrdemServicoExecucaoService.query).toHaveBeenCalled();
      expect(tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaOrdemServicoExecucaoCollection,
        ...additionalTarefaOrdemServicoExecucaos.map(expect.objectContaining),
      );
      expect(comp.tarefaOrdemServicoExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Contador query and add missing value', () => {
      const contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico = { id: 456 };
      const contador: IContador = { id: 11050 };
      contadorResponsavelOrdemServico.contador = contador;

      const contadorCollection: IContador[] = [{ id: 6155 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contadorResponsavelOrdemServico });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contadorResponsavelOrdemServico: IContadorResponsavelOrdemServico = { id: 456 };
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 20852 };
      contadorResponsavelOrdemServico.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;
      const contador: IContador = { id: 12699 };
      contadorResponsavelOrdemServico.contador = contador;

      activatedRoute.data = of({ contadorResponsavelOrdemServico });
      comp.ngOnInit();

      expect(comp.tarefaOrdemServicoExecucaosSharedCollection).toContain(tarefaOrdemServicoExecucao);
      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.contadorResponsavelOrdemServico).toEqual(contadorResponsavelOrdemServico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContadorResponsavelOrdemServico>>();
      const contadorResponsavelOrdemServico = { id: 123 };
      jest
        .spyOn(contadorResponsavelOrdemServicoFormService, 'getContadorResponsavelOrdemServico')
        .mockReturnValue(contadorResponsavelOrdemServico);
      jest.spyOn(contadorResponsavelOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contadorResponsavelOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contadorResponsavelOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(contadorResponsavelOrdemServicoFormService.getContadorResponsavelOrdemServico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contadorResponsavelOrdemServicoService.update).toHaveBeenCalledWith(expect.objectContaining(contadorResponsavelOrdemServico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContadorResponsavelOrdemServico>>();
      const contadorResponsavelOrdemServico = { id: 123 };
      jest.spyOn(contadorResponsavelOrdemServicoFormService, 'getContadorResponsavelOrdemServico').mockReturnValue({ id: null });
      jest.spyOn(contadorResponsavelOrdemServicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contadorResponsavelOrdemServico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contadorResponsavelOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(contadorResponsavelOrdemServicoFormService.getContadorResponsavelOrdemServico).toHaveBeenCalled();
      expect(contadorResponsavelOrdemServicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContadorResponsavelOrdemServico>>();
      const contadorResponsavelOrdemServico = { id: 123 };
      jest.spyOn(contadorResponsavelOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contadorResponsavelOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contadorResponsavelOrdemServicoService.update).toHaveBeenCalled();
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
