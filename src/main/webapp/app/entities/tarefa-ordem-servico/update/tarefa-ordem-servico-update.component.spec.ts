import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IServicoContabilOrdemServico } from 'app/entities/servico-contabil-ordem-servico/servico-contabil-ordem-servico.model';
import { ServicoContabilOrdemServicoService } from 'app/entities/servico-contabil-ordem-servico/service/servico-contabil-ordem-servico.service';
import { TarefaOrdemServicoService } from '../service/tarefa-ordem-servico.service';
import { ITarefaOrdemServico } from '../tarefa-ordem-servico.model';
import { TarefaOrdemServicoFormService } from './tarefa-ordem-servico-form.service';

import { TarefaOrdemServicoUpdateComponent } from './tarefa-ordem-servico-update.component';

describe('TarefaOrdemServico Management Update Component', () => {
  let comp: TarefaOrdemServicoUpdateComponent;
  let fixture: ComponentFixture<TarefaOrdemServicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tarefaOrdemServicoFormService: TarefaOrdemServicoFormService;
  let tarefaOrdemServicoService: TarefaOrdemServicoService;
  let servicoContabilOrdemServicoService: ServicoContabilOrdemServicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaOrdemServicoUpdateComponent],
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
      .overrideTemplate(TarefaOrdemServicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TarefaOrdemServicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tarefaOrdemServicoFormService = TestBed.inject(TarefaOrdemServicoFormService);
    tarefaOrdemServicoService = TestBed.inject(TarefaOrdemServicoService);
    servicoContabilOrdemServicoService = TestBed.inject(ServicoContabilOrdemServicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ServicoContabilOrdemServico query and add missing value', () => {
      const tarefaOrdemServico: ITarefaOrdemServico = { id: 456 };
      const servicoContabilOrdemServico: IServicoContabilOrdemServico = { id: 13423 };
      tarefaOrdemServico.servicoContabilOrdemServico = servicoContabilOrdemServico;

      const servicoContabilOrdemServicoCollection: IServicoContabilOrdemServico[] = [{ id: 28963 }];
      jest
        .spyOn(servicoContabilOrdemServicoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: servicoContabilOrdemServicoCollection })));
      const additionalServicoContabilOrdemServicos = [servicoContabilOrdemServico];
      const expectedCollection: IServicoContabilOrdemServico[] = [
        ...additionalServicoContabilOrdemServicos,
        ...servicoContabilOrdemServicoCollection,
      ];
      jest
        .spyOn(servicoContabilOrdemServicoService, 'addServicoContabilOrdemServicoToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaOrdemServico });
      comp.ngOnInit();

      expect(servicoContabilOrdemServicoService.query).toHaveBeenCalled();
      expect(servicoContabilOrdemServicoService.addServicoContabilOrdemServicoToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilOrdemServicoCollection,
        ...additionalServicoContabilOrdemServicos.map(expect.objectContaining),
      );
      expect(comp.servicoContabilOrdemServicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarefaOrdemServico: ITarefaOrdemServico = { id: 456 };
      const servicoContabilOrdemServico: IServicoContabilOrdemServico = { id: 21757 };
      tarefaOrdemServico.servicoContabilOrdemServico = servicoContabilOrdemServico;

      activatedRoute.data = of({ tarefaOrdemServico });
      comp.ngOnInit();

      expect(comp.servicoContabilOrdemServicosSharedCollection).toContain(servicoContabilOrdemServico);
      expect(comp.tarefaOrdemServico).toEqual(tarefaOrdemServico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaOrdemServico>>();
      const tarefaOrdemServico = { id: 123 };
      jest.spyOn(tarefaOrdemServicoFormService, 'getTarefaOrdemServico').mockReturnValue(tarefaOrdemServico);
      jest.spyOn(tarefaOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(tarefaOrdemServicoFormService.getTarefaOrdemServico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tarefaOrdemServicoService.update).toHaveBeenCalledWith(expect.objectContaining(tarefaOrdemServico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaOrdemServico>>();
      const tarefaOrdemServico = { id: 123 };
      jest.spyOn(tarefaOrdemServicoFormService, 'getTarefaOrdemServico').mockReturnValue({ id: null });
      jest.spyOn(tarefaOrdemServicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaOrdemServico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(tarefaOrdemServicoFormService.getTarefaOrdemServico).toHaveBeenCalled();
      expect(tarefaOrdemServicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaOrdemServico>>();
      const tarefaOrdemServico = { id: 123 };
      jest.spyOn(tarefaOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tarefaOrdemServicoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareServicoContabilOrdemServico', () => {
      it('Should forward to servicoContabilOrdemServicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilOrdemServicoService, 'compareServicoContabilOrdemServico');
        comp.compareServicoContabilOrdemServico(entity, entity2);
        expect(servicoContabilOrdemServicoService.compareServicoContabilOrdemServico).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
