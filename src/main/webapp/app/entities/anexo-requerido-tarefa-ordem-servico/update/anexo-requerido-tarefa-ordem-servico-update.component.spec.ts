import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { ITarefaOrdemServico } from 'app/entities/tarefa-ordem-servico/tarefa-ordem-servico.model';
import { TarefaOrdemServicoService } from 'app/entities/tarefa-ordem-servico/service/tarefa-ordem-servico.service';
import { IAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';
import { AnexoRequeridoTarefaOrdemServicoService } from '../service/anexo-requerido-tarefa-ordem-servico.service';
import { AnexoRequeridoTarefaOrdemServicoFormService } from './anexo-requerido-tarefa-ordem-servico-form.service';

import { AnexoRequeridoTarefaOrdemServicoUpdateComponent } from './anexo-requerido-tarefa-ordem-servico-update.component';

describe('AnexoRequeridoTarefaOrdemServico Management Update Component', () => {
  let comp: AnexoRequeridoTarefaOrdemServicoUpdateComponent;
  let fixture: ComponentFixture<AnexoRequeridoTarefaOrdemServicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoRequeridoTarefaOrdemServicoFormService: AnexoRequeridoTarefaOrdemServicoFormService;
  let anexoRequeridoTarefaOrdemServicoService: AnexoRequeridoTarefaOrdemServicoService;
  let anexoRequeridoService: AnexoRequeridoService;
  let tarefaOrdemServicoService: TarefaOrdemServicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoTarefaOrdemServicoUpdateComponent],
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
      .overrideTemplate(AnexoRequeridoTarefaOrdemServicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoRequeridoTarefaOrdemServicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoRequeridoTarefaOrdemServicoFormService = TestBed.inject(AnexoRequeridoTarefaOrdemServicoFormService);
    anexoRequeridoTarefaOrdemServicoService = TestBed.inject(AnexoRequeridoTarefaOrdemServicoService);
    anexoRequeridoService = TestBed.inject(AnexoRequeridoService);
    tarefaOrdemServicoService = TestBed.inject(TarefaOrdemServicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AnexoRequerido query and add missing value', () => {
      const anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 13124 };
      anexoRequeridoTarefaOrdemServico.anexoRequerido = anexoRequerido;

      const anexoRequeridoCollection: IAnexoRequerido[] = [{ id: 4750 }];
      jest.spyOn(anexoRequeridoService, 'query').mockReturnValue(of(new HttpResponse({ body: anexoRequeridoCollection })));
      const additionalAnexoRequeridos = [anexoRequerido];
      const expectedCollection: IAnexoRequerido[] = [...additionalAnexoRequeridos, ...anexoRequeridoCollection];
      jest.spyOn(anexoRequeridoService, 'addAnexoRequeridoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoTarefaOrdemServico });
      comp.ngOnInit();

      expect(anexoRequeridoService.query).toHaveBeenCalled();
      expect(anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing).toHaveBeenCalledWith(
        anexoRequeridoCollection,
        ...additionalAnexoRequeridos.map(expect.objectContaining),
      );
      expect(comp.anexoRequeridosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TarefaOrdemServico query and add missing value', () => {
      const anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico = { id: 456 };
      const tarefaOrdemServico: ITarefaOrdemServico = { id: 26200 };
      anexoRequeridoTarefaOrdemServico.tarefaOrdemServico = tarefaOrdemServico;

      const tarefaOrdemServicoCollection: ITarefaOrdemServico[] = [{ id: 28538 }];
      jest.spyOn(tarefaOrdemServicoService, 'query').mockReturnValue(of(new HttpResponse({ body: tarefaOrdemServicoCollection })));
      const additionalTarefaOrdemServicos = [tarefaOrdemServico];
      const expectedCollection: ITarefaOrdemServico[] = [...additionalTarefaOrdemServicos, ...tarefaOrdemServicoCollection];
      jest.spyOn(tarefaOrdemServicoService, 'addTarefaOrdemServicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoTarefaOrdemServico });
      comp.ngOnInit();

      expect(tarefaOrdemServicoService.query).toHaveBeenCalled();
      expect(tarefaOrdemServicoService.addTarefaOrdemServicoToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaOrdemServicoCollection,
        ...additionalTarefaOrdemServicos.map(expect.objectContaining),
      );
      expect(comp.tarefaOrdemServicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 1837 };
      anexoRequeridoTarefaOrdemServico.anexoRequerido = anexoRequerido;
      const tarefaOrdemServico: ITarefaOrdemServico = { id: 21573 };
      anexoRequeridoTarefaOrdemServico.tarefaOrdemServico = tarefaOrdemServico;

      activatedRoute.data = of({ anexoRequeridoTarefaOrdemServico });
      comp.ngOnInit();

      expect(comp.anexoRequeridosSharedCollection).toContain(anexoRequerido);
      expect(comp.tarefaOrdemServicosSharedCollection).toContain(tarefaOrdemServico);
      expect(comp.anexoRequeridoTarefaOrdemServico).toEqual(anexoRequeridoTarefaOrdemServico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoTarefaOrdemServico>>();
      const anexoRequeridoTarefaOrdemServico = { id: 123 };
      jest
        .spyOn(anexoRequeridoTarefaOrdemServicoFormService, 'getAnexoRequeridoTarefaOrdemServico')
        .mockReturnValue(anexoRequeridoTarefaOrdemServico);
      jest.spyOn(anexoRequeridoTarefaOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoTarefaOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoTarefaOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoTarefaOrdemServicoFormService.getAnexoRequeridoTarefaOrdemServico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoRequeridoTarefaOrdemServicoService.update).toHaveBeenCalledWith(
        expect.objectContaining(anexoRequeridoTarefaOrdemServico),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoTarefaOrdemServico>>();
      const anexoRequeridoTarefaOrdemServico = { id: 123 };
      jest.spyOn(anexoRequeridoTarefaOrdemServicoFormService, 'getAnexoRequeridoTarefaOrdemServico').mockReturnValue({ id: null });
      jest.spyOn(anexoRequeridoTarefaOrdemServicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoTarefaOrdemServico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoTarefaOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoTarefaOrdemServicoFormService.getAnexoRequeridoTarefaOrdemServico).toHaveBeenCalled();
      expect(anexoRequeridoTarefaOrdemServicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoTarefaOrdemServico>>();
      const anexoRequeridoTarefaOrdemServico = { id: 123 };
      jest.spyOn(anexoRequeridoTarefaOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoTarefaOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoRequeridoTarefaOrdemServicoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAnexoRequerido', () => {
      it('Should forward to anexoRequeridoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(anexoRequeridoService, 'compareAnexoRequerido');
        comp.compareAnexoRequerido(entity, entity2);
        expect(anexoRequeridoService.compareAnexoRequerido).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
