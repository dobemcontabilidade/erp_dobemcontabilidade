import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IDepartamentoContador } from '../departamento-contador.model';
import { DepartamentoContadorService } from '../service/departamento-contador.service';
import { DepartamentoContadorFormService } from './departamento-contador-form.service';

import { DepartamentoContadorUpdateComponent } from './departamento-contador-update.component';

describe('DepartamentoContador Management Update Component', () => {
  let comp: DepartamentoContadorUpdateComponent;
  let fixture: ComponentFixture<DepartamentoContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let departamentoContadorFormService: DepartamentoContadorFormService;
  let departamentoContadorService: DepartamentoContadorService;
  let departamentoService: DepartamentoService;
  let contadorService: ContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DepartamentoContadorUpdateComponent],
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
      .overrideTemplate(DepartamentoContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DepartamentoContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    departamentoContadorFormService = TestBed.inject(DepartamentoContadorFormService);
    departamentoContadorService = TestBed.inject(DepartamentoContadorService);
    departamentoService = TestBed.inject(DepartamentoService);
    contadorService = TestBed.inject(ContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Departamento query and add missing value', () => {
      const departamentoContador: IDepartamentoContador = { id: 456 };
      const departamento: IDepartamento = { id: 10398 };
      departamentoContador.departamento = departamento;

      const departamentoCollection: IDepartamento[] = [{ id: 19619 }];
      jest.spyOn(departamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: departamentoCollection })));
      const additionalDepartamentos = [departamento];
      const expectedCollection: IDepartamento[] = [...additionalDepartamentos, ...departamentoCollection];
      jest.spyOn(departamentoService, 'addDepartamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ departamentoContador });
      comp.ngOnInit();

      expect(departamentoService.query).toHaveBeenCalled();
      expect(departamentoService.addDepartamentoToCollectionIfMissing).toHaveBeenCalledWith(
        departamentoCollection,
        ...additionalDepartamentos.map(expect.objectContaining),
      );
      expect(comp.departamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Contador query and add missing value', () => {
      const departamentoContador: IDepartamentoContador = { id: 456 };
      const contador: IContador = { id: 772 };
      departamentoContador.contador = contador;

      const contadorCollection: IContador[] = [{ id: 27093 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ departamentoContador });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const departamentoContador: IDepartamentoContador = { id: 456 };
      const departamento: IDepartamento = { id: 7901 };
      departamentoContador.departamento = departamento;
      const contador: IContador = { id: 419 };
      departamentoContador.contador = contador;

      activatedRoute.data = of({ departamentoContador });
      comp.ngOnInit();

      expect(comp.departamentosSharedCollection).toContain(departamento);
      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.departamentoContador).toEqual(departamentoContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoContador>>();
      const departamentoContador = { id: 123 };
      jest.spyOn(departamentoContadorFormService, 'getDepartamentoContador').mockReturnValue(departamentoContador);
      jest.spyOn(departamentoContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: departamentoContador }));
      saveSubject.complete();

      // THEN
      expect(departamentoContadorFormService.getDepartamentoContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(departamentoContadorService.update).toHaveBeenCalledWith(expect.objectContaining(departamentoContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoContador>>();
      const departamentoContador = { id: 123 };
      jest.spyOn(departamentoContadorFormService, 'getDepartamentoContador').mockReturnValue({ id: null });
      jest.spyOn(departamentoContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: departamentoContador }));
      saveSubject.complete();

      // THEN
      expect(departamentoContadorFormService.getDepartamentoContador).toHaveBeenCalled();
      expect(departamentoContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoContador>>();
      const departamentoContador = { id: 123 };
      jest.spyOn(departamentoContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(departamentoContadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDepartamento', () => {
      it('Should forward to departamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(departamentoService, 'compareDepartamento');
        comp.compareDepartamento(entity, entity2);
        expect(departamentoService.compareDepartamento).toHaveBeenCalledWith(entity, entity2);
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
