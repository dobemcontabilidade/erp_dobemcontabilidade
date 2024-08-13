import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from 'app/entities/tarefa-ordem-servico-execucao/service/tarefa-ordem-servico-execucao.service';
import { SubTarefaOrdemServicoService } from '../service/sub-tarefa-ordem-servico.service';
import { ISubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';
import { SubTarefaOrdemServicoFormService } from './sub-tarefa-ordem-servico-form.service';

import { SubTarefaOrdemServicoUpdateComponent } from './sub-tarefa-ordem-servico-update.component';

describe('SubTarefaOrdemServico Management Update Component', () => {
  let comp: SubTarefaOrdemServicoUpdateComponent;
  let fixture: ComponentFixture<SubTarefaOrdemServicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subTarefaOrdemServicoFormService: SubTarefaOrdemServicoFormService;
  let subTarefaOrdemServicoService: SubTarefaOrdemServicoService;
  let tarefaOrdemServicoExecucaoService: TarefaOrdemServicoExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SubTarefaOrdemServicoUpdateComponent],
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
      .overrideTemplate(SubTarefaOrdemServicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubTarefaOrdemServicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subTarefaOrdemServicoFormService = TestBed.inject(SubTarefaOrdemServicoFormService);
    subTarefaOrdemServicoService = TestBed.inject(SubTarefaOrdemServicoService);
    tarefaOrdemServicoExecucaoService = TestBed.inject(TarefaOrdemServicoExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaOrdemServicoExecucao query and add missing value', () => {
      const subTarefaOrdemServico: ISubTarefaOrdemServico = { id: 456 };
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 10832 };
      subTarefaOrdemServico.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;

      const tarefaOrdemServicoExecucaoCollection: ITarefaOrdemServicoExecucao[] = [{ id: 16247 }];
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

      activatedRoute.data = of({ subTarefaOrdemServico });
      comp.ngOnInit();

      expect(tarefaOrdemServicoExecucaoService.query).toHaveBeenCalled();
      expect(tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaOrdemServicoExecucaoCollection,
        ...additionalTarefaOrdemServicoExecucaos.map(expect.objectContaining),
      );
      expect(comp.tarefaOrdemServicoExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subTarefaOrdemServico: ISubTarefaOrdemServico = { id: 456 };
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 509 };
      subTarefaOrdemServico.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;

      activatedRoute.data = of({ subTarefaOrdemServico });
      comp.ngOnInit();

      expect(comp.tarefaOrdemServicoExecucaosSharedCollection).toContain(tarefaOrdemServicoExecucao);
      expect(comp.subTarefaOrdemServico).toEqual(subTarefaOrdemServico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubTarefaOrdemServico>>();
      const subTarefaOrdemServico = { id: 123 };
      jest.spyOn(subTarefaOrdemServicoFormService, 'getSubTarefaOrdemServico').mockReturnValue(subTarefaOrdemServico);
      jest.spyOn(subTarefaOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subTarefaOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subTarefaOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(subTarefaOrdemServicoFormService.getSubTarefaOrdemServico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(subTarefaOrdemServicoService.update).toHaveBeenCalledWith(expect.objectContaining(subTarefaOrdemServico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubTarefaOrdemServico>>();
      const subTarefaOrdemServico = { id: 123 };
      jest.spyOn(subTarefaOrdemServicoFormService, 'getSubTarefaOrdemServico').mockReturnValue({ id: null });
      jest.spyOn(subTarefaOrdemServicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subTarefaOrdemServico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subTarefaOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(subTarefaOrdemServicoFormService.getSubTarefaOrdemServico).toHaveBeenCalled();
      expect(subTarefaOrdemServicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISubTarefaOrdemServico>>();
      const subTarefaOrdemServico = { id: 123 };
      jest.spyOn(subTarefaOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subTarefaOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subTarefaOrdemServicoService.update).toHaveBeenCalled();
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
