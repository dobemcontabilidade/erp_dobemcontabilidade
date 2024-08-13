import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from 'app/entities/tarefa-recorrente-execucao/service/tarefa-recorrente-execucao.service';
import { AnexoTarefaRecorrenteExecucaoService } from '../service/anexo-tarefa-recorrente-execucao.service';
import { IAnexoTarefaRecorrenteExecucao } from '../anexo-tarefa-recorrente-execucao.model';
import { AnexoTarefaRecorrenteExecucaoFormService } from './anexo-tarefa-recorrente-execucao-form.service';

import { AnexoTarefaRecorrenteExecucaoUpdateComponent } from './anexo-tarefa-recorrente-execucao-update.component';

describe('AnexoTarefaRecorrenteExecucao Management Update Component', () => {
  let comp: AnexoTarefaRecorrenteExecucaoUpdateComponent;
  let fixture: ComponentFixture<AnexoTarefaRecorrenteExecucaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoTarefaRecorrenteExecucaoFormService: AnexoTarefaRecorrenteExecucaoFormService;
  let anexoTarefaRecorrenteExecucaoService: AnexoTarefaRecorrenteExecucaoService;
  let tarefaRecorrenteExecucaoService: TarefaRecorrenteExecucaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoTarefaRecorrenteExecucaoUpdateComponent],
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
      .overrideTemplate(AnexoTarefaRecorrenteExecucaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoTarefaRecorrenteExecucaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoTarefaRecorrenteExecucaoFormService = TestBed.inject(AnexoTarefaRecorrenteExecucaoFormService);
    anexoTarefaRecorrenteExecucaoService = TestBed.inject(AnexoTarefaRecorrenteExecucaoService);
    tarefaRecorrenteExecucaoService = TestBed.inject(TarefaRecorrenteExecucaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TarefaRecorrenteExecucao query and add missing value', () => {
      const anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao = { id: 456 };
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 14392 };
      anexoTarefaRecorrenteExecucao.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;

      const tarefaRecorrenteExecucaoCollection: ITarefaRecorrenteExecucao[] = [{ id: 14974 }];
      jest
        .spyOn(tarefaRecorrenteExecucaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: tarefaRecorrenteExecucaoCollection })));
      const additionalTarefaRecorrenteExecucaos = [tarefaRecorrenteExecucao];
      const expectedCollection: ITarefaRecorrenteExecucao[] = [
        ...additionalTarefaRecorrenteExecucaos,
        ...tarefaRecorrenteExecucaoCollection,
      ];
      jest.spyOn(tarefaRecorrenteExecucaoService, 'addTarefaRecorrenteExecucaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoTarefaRecorrenteExecucao });
      comp.ngOnInit();

      expect(tarefaRecorrenteExecucaoService.query).toHaveBeenCalled();
      expect(tarefaRecorrenteExecucaoService.addTarefaRecorrenteExecucaoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaRecorrenteExecucaoCollection,
        ...additionalTarefaRecorrenteExecucaos.map(expect.objectContaining),
      );
      expect(comp.tarefaRecorrenteExecucaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao = { id: 456 };
      const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = { id: 25340 };
      anexoTarefaRecorrenteExecucao.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;

      activatedRoute.data = of({ anexoTarefaRecorrenteExecucao });
      comp.ngOnInit();

      expect(comp.tarefaRecorrenteExecucaosSharedCollection).toContain(tarefaRecorrenteExecucao);
      expect(comp.anexoTarefaRecorrenteExecucao).toEqual(anexoTarefaRecorrenteExecucao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoTarefaRecorrenteExecucao>>();
      const anexoTarefaRecorrenteExecucao = { id: 123 };
      jest
        .spyOn(anexoTarefaRecorrenteExecucaoFormService, 'getAnexoTarefaRecorrenteExecucao')
        .mockReturnValue(anexoTarefaRecorrenteExecucao);
      jest.spyOn(anexoTarefaRecorrenteExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoTarefaRecorrenteExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoTarefaRecorrenteExecucao }));
      saveSubject.complete();

      // THEN
      expect(anexoTarefaRecorrenteExecucaoFormService.getAnexoTarefaRecorrenteExecucao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoTarefaRecorrenteExecucaoService.update).toHaveBeenCalledWith(expect.objectContaining(anexoTarefaRecorrenteExecucao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoTarefaRecorrenteExecucao>>();
      const anexoTarefaRecorrenteExecucao = { id: 123 };
      jest.spyOn(anexoTarefaRecorrenteExecucaoFormService, 'getAnexoTarefaRecorrenteExecucao').mockReturnValue({ id: null });
      jest.spyOn(anexoTarefaRecorrenteExecucaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoTarefaRecorrenteExecucao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoTarefaRecorrenteExecucao }));
      saveSubject.complete();

      // THEN
      expect(anexoTarefaRecorrenteExecucaoFormService.getAnexoTarefaRecorrenteExecucao).toHaveBeenCalled();
      expect(anexoTarefaRecorrenteExecucaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoTarefaRecorrenteExecucao>>();
      const anexoTarefaRecorrenteExecucao = { id: 123 };
      jest.spyOn(anexoTarefaRecorrenteExecucaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoTarefaRecorrenteExecucao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoTarefaRecorrenteExecucaoService.update).toHaveBeenCalled();
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
