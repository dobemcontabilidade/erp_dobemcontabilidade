import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from 'app/entities/tarefa-ordem-servico-execucao/service/tarefa-ordem-servico-execucao.service';
import { AnexoOrdemServicoExecucaoService } from '../service/anexo-ordem-servico-execucao.service';
import { IAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';
import { AnexoOrdemServicoExecucaoFormService } from './anexo-ordem-servico-execucao-form.service';

import { AnexoOrdemServicoExecucaoUpdateComponent } from './anexo-ordem-servico-execucao-update.component';

describe('AnexoOrdemServicoExecucao Management Update Component', () => {
  let comp: AnexoOrdemServicoExecucaoUpdateComponent;
  let fixture: ComponentFixture<AnexoOrdemServicoExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoOrdemServicoExecucaoFormService: AnexoOrdemServicoExecucaoFormService;
  let anexoOrdemServicoExecucaoService: AnexoOrdemServicoExecucaoService;
  let tarefaOrdemServicoExecucaoService: TarefaOrdemServicoExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoOrdemServicoExecucaoUpdateComponent],
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
      .overrideTemplate(AnexoOrdemServicoExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoOrdemServicoExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoOrdemServicoExecucaoFormService = TestBed.inject(AnexoOrdemServicoExecucaoFormService);
    anexoOrdemServicoExecucaoService = TestBed.inject(AnexoOrdemServicoExecucaoService);
    tarefaOrdemServicoExecucaoService = TestBed.inject(TarefaOrdemServicoExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaOrdemServicoExecucao query and add missing value', () => {
      const anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao = { id: 456 };
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 4647 };
      anexoOrdemServicoExecucao.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;

      const tarefaOrdemServicoExecucaoCollection: ITarefaOrdemServicoExecucao[] = [{ id: 6245 }];
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

      activatedRoute.data = of({ anexoOrdemServicoExecucao });
      comp.ngOnInit();

      expect(tarefaOrdemServicoExecucaoService.query).toHaveBeenCalled();
      expect(tarefaOrdemServicoExecucaoService.addTarefaOrdemServicoExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaOrdemServicoExecucaoCollection,
        ...additionalTarefaOrdemServicoExecucaos.map(expect.objectContaining),
      );
      expect(comp.tarefaOrdemServicoExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao = { id: 456 };
      const tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao = { id: 2221 };
      anexoOrdemServicoExecucao.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;

      activatedRoute.data = of({ anexoOrdemServicoExecucao });
      comp.ngOnInit();

      expect(comp.tarefaOrdemServicoExecucaosSharedCollection).toContain(tarefaOrdemServicoExecucao);
      expect(comp.anexoOrdemServicoExecucao).toEqual(anexoOrdemServicoExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoOrdemServicoExecucao>>();
      const anexoOrdemServicoExecucao = { id: 123 };
      jest.spyOn(anexoOrdemServicoExecucaoFormService, 'getAnexoOrdemServicoExecucao').mockReturnValue(anexoOrdemServicoExecucao);
      jest.spyOn(anexoOrdemServicoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoOrdemServicoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoOrdemServicoExecucao }));
      saveSubject.complete();

      // THEN
      expect(anexoOrdemServicoExecucaoFormService.getAnexoOrdemServicoExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoOrdemServicoExecucaoService.update).toHaveBeenCalledWith(expect.objectContaining(anexoOrdemServicoExecucao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoOrdemServicoExecucao>>();
      const anexoOrdemServicoExecucao = { id: 123 };
      jest.spyOn(anexoOrdemServicoExecucaoFormService, 'getAnexoOrdemServicoExecucao').mockReturnValue({ id: null });
      jest.spyOn(anexoOrdemServicoExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoOrdemServicoExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoOrdemServicoExecucao }));
      saveSubject.complete();

      // THEN
      expect(anexoOrdemServicoExecucaoFormService.getAnexoOrdemServicoExecucao).toHaveBeenCalled();
      expect(anexoOrdemServicoExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoOrdemServicoExecucao>>();
      const anexoOrdemServicoExecucao = { id: 123 };
      jest.spyOn(anexoOrdemServicoExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoOrdemServicoExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoOrdemServicoExecucaoService.update).toHaveBeenCalled();
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
