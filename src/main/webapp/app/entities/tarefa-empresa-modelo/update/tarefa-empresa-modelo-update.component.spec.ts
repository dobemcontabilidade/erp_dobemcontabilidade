import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { ITarefaEmpresaModelo } from '../tarefa-empresa-modelo.model';
import { TarefaEmpresaModeloService } from '../service/tarefa-empresa-modelo.service';
import { TarefaEmpresaModeloFormService } from './tarefa-empresa-modelo-form.service';

import { TarefaEmpresaModeloUpdateComponent } from './tarefa-empresa-modelo-update.component';

describe('TarefaEmpresaModelo Management Update Component', () => {
  let comp: TarefaEmpresaModeloUpdateComponent;
  let fixture: ComponentFixture<TarefaEmpresaModeloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tarefaEmpresaModeloFormService: TarefaEmpresaModeloFormService;
  let tarefaEmpresaModeloService: TarefaEmpresaModeloService;
  let empresaModeloService: EmpresaModeloService;
  let servicoContabilService: ServicoContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaEmpresaModeloUpdateComponent],
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
      .overrideTemplate(TarefaEmpresaModeloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TarefaEmpresaModeloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tarefaEmpresaModeloFormService = TestBed.inject(TarefaEmpresaModeloFormService);
    tarefaEmpresaModeloService = TestBed.inject(TarefaEmpresaModeloService);
    empresaModeloService = TestBed.inject(EmpresaModeloService);
    servicoContabilService = TestBed.inject(ServicoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EmpresaModelo query and add missing value', () => {
      const tarefaEmpresaModelo: ITarefaEmpresaModelo = { id: 456 };
      const empresaModelo: IEmpresaModelo = { id: 14373 };
      tarefaEmpresaModelo.empresaModelo = empresaModelo;

      const empresaModeloCollection: IEmpresaModelo[] = [{ id: 16089 }];
      jest.spyOn(empresaModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaModeloCollection })));
      const additionalEmpresaModelos = [empresaModelo];
      const expectedCollection: IEmpresaModelo[] = [...additionalEmpresaModelos, ...empresaModeloCollection];
      jest.spyOn(empresaModeloService, 'addEmpresaModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaEmpresaModelo });
      comp.ngOnInit();

      expect(empresaModeloService.query).toHaveBeenCalled();
      expect(empresaModeloService.addEmpresaModeloToCollectionIfMissing).toHaveBeenCalledWith(
        empresaModeloCollection,
        ...additionalEmpresaModelos.map(expect.objectContaining),
      );
      expect(comp.empresaModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ServicoContabil query and add missing value', () => {
      const tarefaEmpresaModelo: ITarefaEmpresaModelo = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 15855 };
      tarefaEmpresaModelo.servicoContabil = servicoContabil;

      const servicoContabilCollection: IServicoContabil[] = [{ id: 16445 }];
      jest.spyOn(servicoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: servicoContabilCollection })));
      const additionalServicoContabils = [servicoContabil];
      const expectedCollection: IServicoContabil[] = [...additionalServicoContabils, ...servicoContabilCollection];
      jest.spyOn(servicoContabilService, 'addServicoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaEmpresaModelo });
      comp.ngOnInit();

      expect(servicoContabilService.query).toHaveBeenCalled();
      expect(servicoContabilService.addServicoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilCollection,
        ...additionalServicoContabils.map(expect.objectContaining),
      );
      expect(comp.servicoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarefaEmpresaModelo: ITarefaEmpresaModelo = { id: 456 };
      const empresaModelo: IEmpresaModelo = { id: 15184 };
      tarefaEmpresaModelo.empresaModelo = empresaModelo;
      const servicoContabil: IServicoContabil = { id: 28689 };
      tarefaEmpresaModelo.servicoContabil = servicoContabil;

      activatedRoute.data = of({ tarefaEmpresaModelo });
      comp.ngOnInit();

      expect(comp.empresaModelosSharedCollection).toContain(empresaModelo);
      expect(comp.servicoContabilsSharedCollection).toContain(servicoContabil);
      expect(comp.tarefaEmpresaModelo).toEqual(tarefaEmpresaModelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaEmpresaModelo>>();
      const tarefaEmpresaModelo = { id: 123 };
      jest.spyOn(tarefaEmpresaModeloFormService, 'getTarefaEmpresaModelo').mockReturnValue(tarefaEmpresaModelo);
      jest.spyOn(tarefaEmpresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaEmpresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaEmpresaModelo }));
      saveSubject.complete();

      // THEN
      expect(tarefaEmpresaModeloFormService.getTarefaEmpresaModelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tarefaEmpresaModeloService.update).toHaveBeenCalledWith(expect.objectContaining(tarefaEmpresaModelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaEmpresaModelo>>();
      const tarefaEmpresaModelo = { id: 123 };
      jest.spyOn(tarefaEmpresaModeloFormService, 'getTarefaEmpresaModelo').mockReturnValue({ id: null });
      jest.spyOn(tarefaEmpresaModeloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaEmpresaModelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaEmpresaModelo }));
      saveSubject.complete();

      // THEN
      expect(tarefaEmpresaModeloFormService.getTarefaEmpresaModelo).toHaveBeenCalled();
      expect(tarefaEmpresaModeloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaEmpresaModelo>>();
      const tarefaEmpresaModelo = { id: 123 };
      jest.spyOn(tarefaEmpresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaEmpresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tarefaEmpresaModeloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresaModelo', () => {
      it('Should forward to empresaModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaModeloService, 'compareEmpresaModelo');
        comp.compareEmpresaModelo(entity, entity2);
        expect(empresaModeloService.compareEmpresaModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareServicoContabil', () => {
      it('Should forward to servicoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilService, 'compareServicoContabil');
        comp.compareServicoContabil(entity, entity2);
        expect(servicoContabilService.compareServicoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
