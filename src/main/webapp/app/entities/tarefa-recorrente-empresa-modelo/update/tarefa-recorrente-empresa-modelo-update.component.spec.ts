import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IServicoContabilEmpresaModelo } from 'app/entities/servico-contabil-empresa-modelo/servico-contabil-empresa-modelo.model';
import { ServicoContabilEmpresaModeloService } from 'app/entities/servico-contabil-empresa-modelo/service/servico-contabil-empresa-modelo.service';
import { TarefaRecorrenteEmpresaModeloService } from '../service/tarefa-recorrente-empresa-modelo.service';
import { ITarefaRecorrenteEmpresaModelo } from '../tarefa-recorrente-empresa-modelo.model';
import { TarefaRecorrenteEmpresaModeloFormService } from './tarefa-recorrente-empresa-modelo-form.service';

import { TarefaRecorrenteEmpresaModeloUpdateComponent } from './tarefa-recorrente-empresa-modelo-update.component';

describe('TarefaRecorrenteEmpresaModelo Management Update Component', () => {
  let comp: TarefaRecorrenteEmpresaModeloUpdateComponent;
  let fixture: ComponentFixture<TarefaRecorrenteEmpresaModeloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tarefaRecorrenteEmpresaModeloFormService: TarefaRecorrenteEmpresaModeloFormService;
  let tarefaRecorrenteEmpresaModeloService: TarefaRecorrenteEmpresaModeloService;
  let servicoContabilEmpresaModeloService: ServicoContabilEmpresaModeloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaRecorrenteEmpresaModeloUpdateComponent],
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
      .overrideTemplate(TarefaRecorrenteEmpresaModeloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TarefaRecorrenteEmpresaModeloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tarefaRecorrenteEmpresaModeloFormService = TestBed.inject(TarefaRecorrenteEmpresaModeloFormService);
    tarefaRecorrenteEmpresaModeloService = TestBed.inject(TarefaRecorrenteEmpresaModeloService);
    servicoContabilEmpresaModeloService = TestBed.inject(ServicoContabilEmpresaModeloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ServicoContabilEmpresaModelo query and add missing value', () => {
      const tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo = { id: 456 };
      const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = { id: 26324 };
      tarefaRecorrenteEmpresaModelo.servicoContabilEmpresaModelo = servicoContabilEmpresaModelo;

      const servicoContabilEmpresaModeloCollection: IServicoContabilEmpresaModelo[] = [{ id: 4071 }];
      jest
        .spyOn(servicoContabilEmpresaModeloService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: servicoContabilEmpresaModeloCollection })));
      const additionalServicoContabilEmpresaModelos = [servicoContabilEmpresaModelo];
      const expectedCollection: IServicoContabilEmpresaModelo[] = [
        ...additionalServicoContabilEmpresaModelos,
        ...servicoContabilEmpresaModeloCollection,
      ];
      jest
        .spyOn(servicoContabilEmpresaModeloService, 'addServicoContabilEmpresaModeloToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaRecorrenteEmpresaModelo });
      comp.ngOnInit();

      expect(servicoContabilEmpresaModeloService.query).toHaveBeenCalled();
      expect(servicoContabilEmpresaModeloService.addServicoContabilEmpresaModeloToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilEmpresaModeloCollection,
        ...additionalServicoContabilEmpresaModelos.map(expect.objectContaining),
      );
      expect(comp.servicoContabilEmpresaModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarefaRecorrenteEmpresaModelo: ITarefaRecorrenteEmpresaModelo = { id: 456 };
      const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = { id: 13190 };
      tarefaRecorrenteEmpresaModelo.servicoContabilEmpresaModelo = servicoContabilEmpresaModelo;

      activatedRoute.data = of({ tarefaRecorrenteEmpresaModelo });
      comp.ngOnInit();

      expect(comp.servicoContabilEmpresaModelosSharedCollection).toContain(servicoContabilEmpresaModelo);
      expect(comp.tarefaRecorrenteEmpresaModelo).toEqual(tarefaRecorrenteEmpresaModelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrenteEmpresaModelo>>();
      const tarefaRecorrenteEmpresaModelo = { id: 123 };
      jest
        .spyOn(tarefaRecorrenteEmpresaModeloFormService, 'getTarefaRecorrenteEmpresaModelo')
        .mockReturnValue(tarefaRecorrenteEmpresaModelo);
      jest.spyOn(tarefaRecorrenteEmpresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrenteEmpresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaRecorrenteEmpresaModelo }));
      saveSubject.complete();

      // THEN
      expect(tarefaRecorrenteEmpresaModeloFormService.getTarefaRecorrenteEmpresaModelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tarefaRecorrenteEmpresaModeloService.update).toHaveBeenCalledWith(expect.objectContaining(tarefaRecorrenteEmpresaModelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrenteEmpresaModelo>>();
      const tarefaRecorrenteEmpresaModelo = { id: 123 };
      jest.spyOn(tarefaRecorrenteEmpresaModeloFormService, 'getTarefaRecorrenteEmpresaModelo').mockReturnValue({ id: null });
      jest.spyOn(tarefaRecorrenteEmpresaModeloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrenteEmpresaModelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaRecorrenteEmpresaModelo }));
      saveSubject.complete();

      // THEN
      expect(tarefaRecorrenteEmpresaModeloFormService.getTarefaRecorrenteEmpresaModelo).toHaveBeenCalled();
      expect(tarefaRecorrenteEmpresaModeloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaRecorrenteEmpresaModelo>>();
      const tarefaRecorrenteEmpresaModelo = { id: 123 };
      jest.spyOn(tarefaRecorrenteEmpresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaRecorrenteEmpresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tarefaRecorrenteEmpresaModeloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareServicoContabilEmpresaModelo', () => {
      it('Should forward to servicoContabilEmpresaModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilEmpresaModeloService, 'compareServicoContabilEmpresaModelo');
        comp.compareServicoContabilEmpresaModelo(entity, entity2);
        expect(servicoContabilEmpresaModeloService.compareServicoContabilEmpresaModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
