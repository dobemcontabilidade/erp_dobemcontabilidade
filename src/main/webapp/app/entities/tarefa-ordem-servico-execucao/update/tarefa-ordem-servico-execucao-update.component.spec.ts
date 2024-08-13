import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaOrdemServico } from 'app/entities/tarefa-ordem-servico/tarefa-ordem-servico.model';
import { TarefaOrdemServicoService } from 'app/entities/tarefa-ordem-servico/service/tarefa-ordem-servico.service';
import { TarefaOrdemServicoExecucaoService } from '../service/tarefa-ordem-servico-execucao.service';
import { ITarefaOrdemServicoExecucao } from '../tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoFormService } from './tarefa-ordem-servico-execucao-form.service';

import { TarefaOrdemServicoExecucaoUpdateComponent } from './tarefa-ordem-servico-execucao-update.component';

describe('TarefaOrdemServicoExecucao Management Update Component', () => {
  let comp: TarefaOrdemServicoExecucaoUpdateComponent;
  let fixture: ComponentFixture<TarefaOrdemServicoExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tarefaOrdemServicoExecucaoFormService: TarefaOrdemServicoExecucaoFormService;
  let tarefaOrdemServicoExecucaoService: TarefaOrdemServicoExecucaoService;
  let tarefaOrdemServicoService: TarefaOrdemServicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaOrdemServicoExecucaoUpdateComponent],
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
      .overrideTemplate(TarefaOrdemServicoExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TarefaOrdemServicoExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tarefaOrdemServicoExecucaoFormService = TestBed.inject(TarefaOrdemServicoExecucaoFormService);
    tarefaOrdemServicoExecucaoService = TestBed.inject(TarefaOrdemServicoExecucaoService);
    tarefaOrdemServicoService = TestBed.inject(TarefaOrdemServicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaOrdemServico query and add missing value', () => {
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 456 };
      const tarefaOrdemServico: ITarefaOrdemServico = { id: 12886 };
      tarefaOrdemServicoExecucao.tarefaOrdemServico = tarefaOrdemServico;

      const tarefaOrdemServicoCollection: ITarefaOrdemServico[] = [{ id: 18831 }];
      jest.spyOn(tarefaOrdemServicoService, 'query').mockReturnValue(of(new HttpResponse({ body: tarefaOrdemServicoCollection })));
      const additionalTarefaOrdemServicos = [tarefaOrdemServico];
      const expectedCollection: ITarefaOrdemServico[] = [...additionalTarefaOrdemServicos, ...tarefaOrdemServicoCollection];
      jest.spyOn(tarefaOrdemServicoService, 'addTarefaOrdemServicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaOrdemServicoExecucao });
      comp.ngOnInit();

      expect(tarefaOrdemServicoService.query).toHaveBeenCalled();
      expect(tarefaOrdemServicoService.addTarefaOrdemServicoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaOrdemServicoCollection,
        ...additionalTarefaOrdemServicos.map(expect.objectContaining),
      );
      expect(comp.tarefaOrdemServicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 456 };
      const tarefaOrdemServico: ITarefaOrdemServico = { id: 23077 };
      tarefaOrdemServicoExecucao.tarefaOrdemServico = tarefaOrdemServico;

      activatedRoute.data = of({ tarefaOrdemServicoExecucao });
      comp.ngOnInit();

      expect(comp.tarefaOrdemServicosSharedCollection).toContain(tarefaOrdemServico);
      expect(comp.tarefaOrdemServicoExecucao).toEqual(tarefaOrdemServicoExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaOrdemServicoExecucao>>();
      const tarefaOrdemServicoExecucao = { id: 123 };
      jest.spyOn(tarefaOrdemServicoExecucaoFormService, 'getTarefaOrdemServicoExecucao').mockReturnValue(tarefaOrdemServicoExecucao);
      jest.spyOn(tarefaOrdemServicoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaOrdemServicoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaOrdemServicoExecucao }));
      saveSubject.complete();

      // THEN
      expect(tarefaOrdemServicoExecucaoFormService.getTarefaOrdemServicoExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tarefaOrdemServicoExecucaoService.update).toHaveBeenCalledWith(expect.objectContaining(tarefaOrdemServicoExecucao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaOrdemServicoExecucao>>();
      const tarefaOrdemServicoExecucao = { id: 123 };
      jest.spyOn(tarefaOrdemServicoExecucaoFormService, 'getTarefaOrdemServicoExecucao').mockReturnValue({ id: null });
      jest.spyOn(tarefaOrdemServicoExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaOrdemServicoExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaOrdemServicoExecucao }));
      saveSubject.complete();

      // THEN
      expect(tarefaOrdemServicoExecucaoFormService.getTarefaOrdemServicoExecucao).toHaveBeenCalled();
      expect(tarefaOrdemServicoExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaOrdemServicoExecucao>>();
      const tarefaOrdemServicoExecucao = { id: 123 };
      jest.spyOn(tarefaOrdemServicoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaOrdemServicoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tarefaOrdemServicoExecucaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTarefaOrdemServico', () => {
      it('Should forward to tarefaOrdemServicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tarefaOrdemServicoService, 'compareTarefaOrdemServico');
        comp.compareTarefaOrdemServico(entity, entity2);
        expect(tarefaOrdemServicoService.compareTarefaOrdemServico).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
