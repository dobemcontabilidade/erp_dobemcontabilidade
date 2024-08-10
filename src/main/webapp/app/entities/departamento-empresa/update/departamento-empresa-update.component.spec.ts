import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { DepartamentoService } from 'app/entities/departamento/service/departamento.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IDepartamentoEmpresa } from '../departamento-empresa.model';
import { DepartamentoEmpresaService } from '../service/departamento-empresa.service';
import { DepartamentoEmpresaFormService } from './departamento-empresa-form.service';

import { DepartamentoEmpresaUpdateComponent } from './departamento-empresa-update.component';

describe('DepartamentoEmpresa Management Update Component', () => {
  let comp: DepartamentoEmpresaUpdateComponent;
  let fixture: ComponentFixture<DepartamentoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let departamentoEmpresaFormService: DepartamentoEmpresaFormService;
  let departamentoEmpresaService: DepartamentoEmpresaService;
  let departamentoService: DepartamentoService;
  let empresaService: EmpresaService;
  let contadorService: ContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DepartamentoEmpresaUpdateComponent],
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
      .overrideTemplate(DepartamentoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DepartamentoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    departamentoEmpresaFormService = TestBed.inject(DepartamentoEmpresaFormService);
    departamentoEmpresaService = TestBed.inject(DepartamentoEmpresaService);
    departamentoService = TestBed.inject(DepartamentoService);
    empresaService = TestBed.inject(EmpresaService);
    contadorService = TestBed.inject(ContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Departamento query and add missing value', () => {
      const departamentoEmpresa: IDepartamentoEmpresa = { id: 456 };
      const departamento: IDepartamento = { id: 18314 };
      departamentoEmpresa.departamento = departamento;

      const departamentoCollection: IDepartamento[] = [{ id: 9650 }];
      jest.spyOn(departamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: departamentoCollection })));
      const additionalDepartamentos = [departamento];
      const expectedCollection: IDepartamento[] = [...additionalDepartamentos, ...departamentoCollection];
      jest.spyOn(departamentoService, 'addDepartamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ departamentoEmpresa });
      comp.ngOnInit();

      expect(departamentoService.query).toHaveBeenCalled();
      expect(departamentoService.addDepartamentoToCollectionIfMissing).toHaveBeenCalledWith(
        departamentoCollection,
        ...additionalDepartamentos.map(expect.objectContaining),
      );
      expect(comp.departamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const departamentoEmpresa: IDepartamentoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 31603 };
      departamentoEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 7786 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ departamentoEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Contador query and add missing value', () => {
      const departamentoEmpresa: IDepartamentoEmpresa = { id: 456 };
      const contador: IContador = { id: 8635 };
      departamentoEmpresa.contador = contador;

      const contadorCollection: IContador[] = [{ id: 20407 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ departamentoEmpresa });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const departamentoEmpresa: IDepartamentoEmpresa = { id: 456 };
      const departamento: IDepartamento = { id: 15493 };
      departamentoEmpresa.departamento = departamento;
      const empresa: IEmpresa = { id: 3668 };
      departamentoEmpresa.empresa = empresa;
      const contador: IContador = { id: 26084 };
      departamentoEmpresa.contador = contador;

      activatedRoute.data = of({ departamentoEmpresa });
      comp.ngOnInit();

      expect(comp.departamentosSharedCollection).toContain(departamento);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.departamentoEmpresa).toEqual(departamentoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoEmpresa>>();
      const departamentoEmpresa = { id: 123 };
      jest.spyOn(departamentoEmpresaFormService, 'getDepartamentoEmpresa').mockReturnValue(departamentoEmpresa);
      jest.spyOn(departamentoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: departamentoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(departamentoEmpresaFormService.getDepartamentoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(departamentoEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(departamentoEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoEmpresa>>();
      const departamentoEmpresa = { id: 123 };
      jest.spyOn(departamentoEmpresaFormService, 'getDepartamentoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(departamentoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: departamentoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(departamentoEmpresaFormService.getDepartamentoEmpresa).toHaveBeenCalled();
      expect(departamentoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDepartamentoEmpresa>>();
      const departamentoEmpresa = { id: 123 };
      jest.spyOn(departamentoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(departamentoEmpresaService.update).toHaveBeenCalled();
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
  });
});
