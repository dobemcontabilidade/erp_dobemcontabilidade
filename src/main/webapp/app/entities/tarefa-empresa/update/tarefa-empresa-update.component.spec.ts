import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { ITarefa } from 'app/entities/tarefa/tarefa.model';
import { TarefaService } from 'app/entities/tarefa/service/tarefa.service';
import { ITarefaEmpresa } from '../tarefa-empresa.model';
import { TarefaEmpresaService } from '../service/tarefa-empresa.service';
import { TarefaEmpresaFormService } from './tarefa-empresa-form.service';

import { TarefaEmpresaUpdateComponent } from './tarefa-empresa-update.component';

describe('TarefaEmpresa Management Update Component', () => {
  let comp: TarefaEmpresaUpdateComponent;
  let fixture: ComponentFixture<TarefaEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tarefaEmpresaFormService: TarefaEmpresaFormService;
  let tarefaEmpresaService: TarefaEmpresaService;
  let empresaService: EmpresaService;
  let contadorService: ContadorService;
  let tarefaService: TarefaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaEmpresaUpdateComponent],
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
      .overrideTemplate(TarefaEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TarefaEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tarefaEmpresaFormService = TestBed.inject(TarefaEmpresaFormService);
    tarefaEmpresaService = TestBed.inject(TarefaEmpresaService);
    empresaService = TestBed.inject(EmpresaService);
    contadorService = TestBed.inject(ContadorService);
    tarefaService = TestBed.inject(TarefaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const tarefaEmpresa: ITarefaEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 22577 };
      tarefaEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 22633 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Contador query and add missing value', () => {
      const tarefaEmpresa: ITarefaEmpresa = { id: 456 };
      const contador: IContador = { id: 15748 };
      tarefaEmpresa.contador = contador;

      const contadorCollection: IContador[] = [{ id: 22910 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaEmpresa });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tarefa query and add missing value', () => {
      const tarefaEmpresa: ITarefaEmpresa = { id: 456 };
      const tarefa: ITarefa = { id: 22621 };
      tarefaEmpresa.tarefa = tarefa;

      const tarefaCollection: ITarefa[] = [{ id: 3388 }];
      jest.spyOn(tarefaService, 'query').mockReturnValue(of(new HttpResponse({ body: tarefaCollection })));
      const additionalTarefas = [tarefa];
      const expectedCollection: ITarefa[] = [...additionalTarefas, ...tarefaCollection];
      jest.spyOn(tarefaService, 'addTarefaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tarefaEmpresa });
      comp.ngOnInit();

      expect(tarefaService.query).toHaveBeenCalled();
      expect(tarefaService.addTarefaToCollectionIfMissing).toHaveBeenCalledWith(
        tarefaCollection,
        ...additionalTarefas.map(expect.objectContaining),
      );
      expect(comp.tarefasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tarefaEmpresa: ITarefaEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 13863 };
      tarefaEmpresa.empresa = empresa;
      const contador: IContador = { id: 22339 };
      tarefaEmpresa.contador = contador;
      const tarefa: ITarefa = { id: 24494 };
      tarefaEmpresa.tarefa = tarefa;

      activatedRoute.data = of({ tarefaEmpresa });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.tarefasSharedCollection).toContain(tarefa);
      expect(comp.tarefaEmpresa).toEqual(tarefaEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaEmpresa>>();
      const tarefaEmpresa = { id: 123 };
      jest.spyOn(tarefaEmpresaFormService, 'getTarefaEmpresa').mockReturnValue(tarefaEmpresa);
      jest.spyOn(tarefaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(tarefaEmpresaFormService.getTarefaEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tarefaEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(tarefaEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaEmpresa>>();
      const tarefaEmpresa = { id: 123 };
      jest.spyOn(tarefaEmpresaFormService, 'getTarefaEmpresa').mockReturnValue({ id: null });
      jest.spyOn(tarefaEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tarefaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(tarefaEmpresaFormService.getTarefaEmpresa).toHaveBeenCalled();
      expect(tarefaEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITarefaEmpresa>>();
      const tarefaEmpresa = { id: 123 };
      jest.spyOn(tarefaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tarefaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tarefaEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareTarefa', () => {
      it('Should forward to tarefaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tarefaService, 'compareTarefa');
        comp.compareTarefa(entity, entity2);
        expect(tarefaService.compareTarefa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
